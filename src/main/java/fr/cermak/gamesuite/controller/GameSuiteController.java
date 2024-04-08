package fr.cermak.gamesuite.controller;

import fr.cermak.gamesuite.GameSuite;
import fr.cermak.gamesuite.socket.ClientHandler;
import fr.cermak.gamesuite.socket.GameResponse;
import fr.cermak.gamesuite.util.MessageMapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameSuiteController {
    @MessageMapping(path = 0)
    public GameResponse pong(byte[] data) {
        System.out.println("RECEIVED PING!");
        GameResponse response = new GameResponse(GameResponse.PONG, null);

        List<ClientHandler> toRemove = new ArrayList<>();
        for (ClientHandler otherClient : GameSuite.handler.getClients()) {
            System.out.println("SENDING PONG TO " + otherClient.getClient().getInetAddress().getHostAddress());

            try {
                response.send(otherClient.getOut());
            } catch (IOException e) {
                toRemove.add(otherClient);
            }
        }

        toRemove.forEach(GameSuite.handler.getClients()::remove);

        return null;
    }
}
