package dev.xernas.carbon.server.protocol.packet.config;

import dev.xernas.carbon.server.io.MCByteBuf;
import dev.xernas.carbon.server.protocol.packet.IPacket;
import dev.xernas.carbon.server.protocol.packet.PacketData;

import java.io.IOException;

public class FinishConfigPacket implements IPacket {

    @Override
    public void write(MCByteBuf byteBuf) throws IOException {

    }

    @Override
    public void read(PacketData<String> data, MCByteBuf byteBuf) throws IOException {

    }
}
