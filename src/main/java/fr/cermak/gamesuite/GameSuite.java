package fr.cermak.gamesuite;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class GameSuite {
    public static void main(String[] args) {
        while (true) {
            try {
                ServerSocket sock = new ServerSocket(1337);

                Socket client = sock.accept();
                PrintWriter out =
                        new PrintWriter(client.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(client.getInputStream()));

                while (client.isConnected()) {
                    String input = in.readLine();

                    if (input == null) {
                        break;
                    }

                    if (input.equals("Hey!")) {
                        out.println("Hey!");
                    } else if (input.equals("Bye!")) {
                        client.close();
                    }
                }

                sock.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}