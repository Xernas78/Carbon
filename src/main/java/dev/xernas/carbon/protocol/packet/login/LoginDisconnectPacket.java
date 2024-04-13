package dev.xernas.carbon.protocol.packet.login;

import dev.xernas.carbon.io.MCByteBuf;
import dev.xernas.carbon.protocol.packet.IPacket;
import dev.xernas.carbon.protocol.packet.PacketData;

import java.io.IOException;

public class LoginDisconnectPacket implements IPacket {

    private String reason;

    public LoginDisconnectPacket() {

    }

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
