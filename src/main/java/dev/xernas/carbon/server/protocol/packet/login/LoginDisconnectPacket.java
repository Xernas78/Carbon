package dev.xernas.carbon.server.protocol.packet.login;

import dev.xernas.carbon.server.io.MCByteBuf;
import dev.xernas.carbon.server.protocol.packet.IPacket;
import dev.xernas.carbon.server.protocol.packet.PacketData;

import java.io.IOException;

public class LoginDisconnectPacket implements IPacket {

    private final String reason;

    public LoginDisconnectPacket(String reason) {
        this.reason = reason;
    }

    @Override
    public void write(MCByteBuf byteBuf) throws IOException {
        byteBuf.writeString("{\"text\":\"" + reason + "\"}", 262144);
    }

    @Override
    public void read(PacketData<String> data, MCByteBuf byteBuf) throws IOException {

    }
}
