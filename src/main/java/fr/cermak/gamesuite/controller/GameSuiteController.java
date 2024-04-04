package fr.cermak.gamesuite.controller;

import fr.cermak.gamesuite.GameSuite;
import fr.cermak.gamesuite.socket.ClientHandler;
import fr.cermak.gamesuite.socket.GameResponse;
import fr.cermak.gamesuite.utils.MessageMapping;

import java.io.IOException;

public class GameSuiteController {
    @MessageMapping(path = "pong")
    public void pong(GameResponse gameResponse) throws IOException {
        for (ClientHandler otherClient : GameSuite.handler.getClients()) {
            gameResponse.send(otherClient.getOut());
        }
    }
}
