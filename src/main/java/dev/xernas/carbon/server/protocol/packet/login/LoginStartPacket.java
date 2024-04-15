package dev.xernas.carbon.server.protocol.packet.login;

import dev.xernas.carbon.server.io.MCByteBuf;
import dev.xernas.carbon.server.protocol.packet.IPacket;
import dev.xernas.carbon.server.protocol.packet.PacketData;

import java.io.IOException;

public class LoginStartPacket implements IPacket {

    public LoginStartPacket() {
    }

    @Override
    public void write(MCByteBuf byteBuf) throws IOException {

    }

    @Override
    public void read(PacketData<String> data, MCByteBuf byteBuf) throws IOException {
        data.set("username", byteBuf.readString(16));
        data.set("uuid", byteBuf.readUUID());
    }
}
