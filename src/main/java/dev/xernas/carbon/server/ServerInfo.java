package dev.xernas.carbon.server;

import dev.xernas.carbon.json.JsonSerializable;
import dev.xernas.carbon.server.enums.Version;

public class ServerInfo implements JsonSerializable<ServerInfo> {

    private final String version;
    private final int protocol;
    private final int maxPlayers;
    private final int onlinePlayers;
    private final String motd;

    public ServerInfo(String version, int protocol, int maxPlayers, int onlinePlayers, String motd) {
        this.version = version;
        this.protocol = protocol;
        this.maxPlayers = maxPlayers;
        this.onlinePlayers = onlinePlayers;
        this.motd = motd;
    }

    @Override
    public String toJson() {
        return "{" +
                "\"version\":" + "{" + "\"name\":\"" + version + "\"," +
                "\"protocol\":" + protocol +
                "}," +
                "\"players\":" + "{" + "\"max\":" + maxPlayers + "," +
                "\"online\":" + onlinePlayers +
                "}," +
                "\"description\":" + "{" + "\"text\":\"" + motd + "\"" +
                "}" +
                "}";
    }

    @Override
    public ServerInfo fromJson(String json) {
        return null;
    }
}
