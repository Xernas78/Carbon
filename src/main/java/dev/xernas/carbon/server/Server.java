package dev.xernas.carbon.server;

import dev.xernas.carbon.client.ClientManager;
import dev.xernas.carbon.client.ClientSession;
import dev.xernas.carbon.server.protocol.packet.PacketRegistry;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private int port;

    private static Server instance;
    private boolean running = false;
    public Server() {
        this.port = 4242;
    }

    public Server withPort(int port) {
        this.port = port;
        return this;
    }

    public void start() throws IOException {
        System.out.println("Server started on port " + port);
        instance = this;
        try (ServerSocket server = new ServerSocket(port)) {
            PacketRegistry.registerPackets();
            ClientManager manager = new ClientManager(this);
            running = true;
            while (running) {
                Socket client = server.accept();
                ClientSession session = new ClientSession(manager, client);
                new Thread(session).start();
            }
        }
    }

    public void stop() {
        System.out.println("Server stopped");
        running = false;
    }

    public static Server getInstance() {
        return instance;
    }

    public int getPort() {
        return port;
    }
}
