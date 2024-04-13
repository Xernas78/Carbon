package dev.xernas.carbon.protocol.packet.login;

import dev.xernas.carbon.io.MCByteBuf;
import dev.xernas.carbon.protocol.packet.IPacket;
import dev.xernas.carbon.protocol.packet.PacketData;

import java.io.IOException;

public class LoginAcknowledgedPacket implements IPacket {

    public LoginAcknowledgedPacket() {
    }

    @Override
    public void write(MCByteBuf byteBuf) throws IOException {

    }

    @Override
    public void read(PacketData<String> data, MCByteBuf byteBuf) throws IOException {

    }
}
