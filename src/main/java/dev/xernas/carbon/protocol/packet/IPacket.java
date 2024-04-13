package dev.xernas.carbon.protocol.packet;

import dev.xernas.carbon.io.MCByteBuf;

import java.io.IOException;

public interface IPacket {

    void write(MCByteBuf byteBuf) throws IOException;

    void read(PacketData<String> data, MCByteBuf byteBuf) throws IOException;

}
