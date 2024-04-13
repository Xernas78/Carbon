package dev.xernas.carbon;

import dev.xernas.carbon.server.Server;

import java.io.IOException;

public class Carbon {

    public static final String VERSION = "Carbon 1.20.4";
    public static final int PROTOCOL = 765;

    public static void main(String[] args) {
        try {
            Server server = new Server()
                    .withPort(25565);
            server.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
