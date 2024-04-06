package fr.cermak.gamesuite.socket;

import fr.cermak.gamesuite.util.ByteUtil;
import fr.cermak.gamesuite.util.MessageHandler;
import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;

@Getter
@Setter
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
                in.read(buffer, 0, 2);

                System.out.println("INCOMING DATA!");

                int command = ByteUtil.toUnsignedInt(buffer[0]);
                int length = ByteUtil.toUnsignedInt(buffer[1]);

                if (length == 0) {
                    processCommand(command, null);
                } else {
                    byte[] data = new byte[length];
                    in.read(data, 0, length);

                    processCommand(command, data);
                }
            }

            client.close();
        } catch (Exception e) {
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

    private void processCommand(int command, byte[] data) throws Exception {
        System.out.println("PROCESSING COMMAND " + command);

        GameResponse response = MessageHandler.getInstance().handleMessage(command, data);

        if (response != null) {
            response.send(out);
        }
    }
}