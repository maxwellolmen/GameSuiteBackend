package fr.cermak.gamesuite.controller;

import fr.cermak.gamesuite.GameSuite;
import fr.cermak.gamesuite.socket.ClientHandler;
import fr.cermak.gamesuite.socket.GameCommand;
import fr.cermak.gamesuite.socket.GameResponse;
import fr.cermak.gamesuite.util.MessageMapping;

import java.io.IOException;

public class GameSuiteController {
    @MessageMapping(path = GameCommand.PING)
    public void pong(GameResponse gameResponse) throws IOException {
        for (ClientHandler otherClient : GameSuite.handler.getClients()) {
            gameResponse.send(otherClient.getOut());
        }
    }
}
