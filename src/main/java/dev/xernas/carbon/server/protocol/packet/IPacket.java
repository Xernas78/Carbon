package dev.xernas.carbon.server.protocol.packet;

import dev.xernas.carbon.server.io.MCByteBuf;

import java.io.IOException;

public interface IPacket {

    void write(MCByteBuf byteBuf) throws IOException;

    void read(PacketData<String> data, MCByteBuf byteBuf) throws IOException;

}
