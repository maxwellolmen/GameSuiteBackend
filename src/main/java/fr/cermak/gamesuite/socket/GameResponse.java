package fr.cermak.gamesuite.socket;

import java.io.IOException;
import java.io.OutputStream;

public class GameResponse {

    public static final byte PONG = 0;

    private final byte code;
    private final byte[] data;

    public GameResponse(byte code, byte[] data) {
        this.code = code;
        this.data = data;
    }

    public void send(OutputStream out) throws IOException {
        out.write(code);

        if (data != null) {
            out.write(data.length);
            out.write(data);
        } else {
            // Length is 0; client will not await data
            out.write(0);
        }
    }
}