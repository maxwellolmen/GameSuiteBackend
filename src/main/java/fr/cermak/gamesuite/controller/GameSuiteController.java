package fr.cermak.gamesuite.controller;

import fr.cermak.gamesuite.GameSuite;
import fr.cermak.gamesuite.socket.ClientHandler;
import fr.cermak.gamesuite.socket.GameResponse;
import fr.cermak.gamesuite.util.MessageMapping;

import java.io.IOException;

public class GameSuiteController {
    @MessageMapping(path = 0)
    public GameResponse pong(byte[] data) throws IOException {
        System.out.println("RECEIVED PING!");
        GameResponse response = new GameResponse(GameResponse.PONG, null);
        for (ClientHandler otherClient : GameSuite.handler.getClients()) {
            System.out.println("SENDING PONG TO " + otherClient.getClient().getInputStream());
            response.send(otherClient.getOut());
        }

        return null;
    }
}
