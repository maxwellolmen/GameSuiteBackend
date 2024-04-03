package fr.cermak.gamesuite.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class SocketHandler {

    public static boolean active = true;

    private final ServerSocket server;

    private Set<ClientHandler> clients;

    public SocketHandler(int port) throws IOException {
        server = new ServerSocket(port);
    }

    public void start() throws IOException {
        clients = new HashSet<>();

        while (active) {
            Socket client = server.accept();
            ClientHandler handler = new ClientHandler(client);
            clients.add(handler);
            handler.start();
        }
    }

    public Set<ClientHandler> getClients() {
        return clients;
    }
}