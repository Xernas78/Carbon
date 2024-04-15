package dev.xernas.carbon.client;

import dev.xernas.carbon.server.Server;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientManager {

    private int sessionIdCounter;

    private final Server server;

    private final Map<Integer, ClientSession> sessions;

    public ClientManager(Server server) {
        this.server = server;
        this.sessions = new HashMap<>();
        this.sessionIdCounter = 0;
    }

    public Integer newSession(ClientSession session) {
        Integer sessionId = sessionIdCounter;
        sessions.put(sessionId, session);
        sessionIdCounter++;
        return sessionId;
    }

    public void removeSession(Integer session) {
        sessions.remove(session);
    }

    public void removeSession(ClientSession session) {
        sessions.values().remove(session);
    }

    public ClientSession getSession(Integer session) {
        return sessions.get(session);
    }

    public Server getServer() {
        return server;
    }

    public Map<Integer, ClientSession> getSessions() {
        return Map.copyOf(sessions);
    }
}
