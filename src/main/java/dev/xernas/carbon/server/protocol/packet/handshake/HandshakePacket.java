package dev.xernas.carbon.server.protocol.packet.handshake;

import dev.xernas.carbon.server.io.MCByteBuf;
import dev.xernas.carbon.server.protocol.packet.IPacket;
import dev.xernas.carbon.server.protocol.packet.PacketData;
import dev.xernas.carbon.server.Server;

import java.io.IOException;

public class HandshakePacket implements IPacket {

    public HandshakePacket() {

    }

    @Override
    public void write(MCByteBuf byteBuf) {

    }

    @Override
    public void read(PacketData<String> data, MCByteBuf byteBuf) throws IOException {
        data.set("protocol", byteBuf.readVarInt());
        data.set("host", byteBuf.readString(255));
        data.set("port", byteBuf.getIn().readUnsignedShort());
        data.set("nextState", byteBuf.readVarInt());
        int nextState = data.get("nextState");
        if (nextState != 1 && nextState != 2) data.setValid(false);
    }
}
