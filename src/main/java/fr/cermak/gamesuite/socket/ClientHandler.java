package fr.cermak.gamesuite.socket;

import fr.cermak.gamesuite.GameSuite;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;

public class ClientHandler extends Thread {

    private boolean active;
    private Socket client;

    private OutputStream out;
    private InputStream in;

    private byte[] buffer;

    public ClientHandler(Socket client) throws IOException {
        this.client = client;
        this.out = client.getOutputStream();
        this.in = client.getInputStream();

        buffer = new byte[1024];
    }

    @Override
    public void run() {
        try {
            while (active) {
                in.read(buffer, 0, 3);

                byte command = buffer[0];
                byte length = buffer[1];

                if (length == 0) {
                    processCommand(command, null);
                } else {
                    byte[] data = new byte[length];
                    in.read(data, 0, length);

                    processCommand(command, data);
                }
            }

            client.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        active = false;
    }

    private short parseShort(int byteIndex) {
        return wrap(byteIndex, 2).getShort();
    }

    private int parseInt(int byteIndex) {
        return wrap(byteIndex, 4).getInt();
    }

    private long parseLong(int byteIndex) {
        return wrap(byteIndex, 8).getLong();
    }

    private float parseFloat(int byteIndex) {
        return wrap(byteIndex, 4).getFloat();
    }

    private double parseDouble(int byteIndex) {
        return wrap(byteIndex, 8).getDouble();
    }

    private ByteBuffer wrap(int byteIndex, int length) {
        byte[] arr = new byte[length];
        System.arraycopy(buffer, byteIndex, arr, 0, length);

        return ByteBuffer.wrap(arr);
    }

    private void processCommand(byte command, byte[] data) throws IOException {
        GameResponse response = null;

        switch (command) {
            case GameCommand.PING:
                GameResponse pong = new GameResponse(GameResponse.PONG, null);
                for (ClientHandler otherClient : GameSuite.handler.getClients()) {
                    pong.send(otherClient.out);
                }
        }

        if (response != null) {
            response.send(out);
        }
    }
}