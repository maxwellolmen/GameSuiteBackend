package fr.cermak.gamesuite;

import fr.cermak.gamesuite.socket.SocketHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class GameSuite {

    public static SocketHandler handler;

    public static void main(String[] args) throws IOException {
        handler = new SocketHandler(1337);
        handler.start();
    }
}