package fr.cermak.gamesuite.controller;

import fr.cermak.gamesuite.GameSuite;
import fr.cermak.gamesuite.socket.ClientHandler;
import fr.cermak.gamesuite.socket.GameCommand;
import fr.cermak.gamesuite.socket.GameResponse;
import fr.cermak.gamesuite.util.MessageMapping;

import java.io.IOException;

public class GameSuiteController {
    @MessageMapping(path = GameCommand.PING)
    public GameResponse pong(byte[] data) throws IOException {
        GameResponse response = new GameResponse(GameResponse.PONG, null);
        for (ClientHandler otherClient : GameSuite.handler.getClients()) {
            response.send(otherClient.getOut());
        }

        return null;
    }
}
