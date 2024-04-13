package dev.xernas.carbon.protocol.packet.status;

import dev.xernas.carbon.io.MCByteBuf;
import dev.xernas.carbon.protocol.packet.IPacket;
import dev.xernas.carbon.protocol.packet.PacketData;

import java.io.IOException;

public class PingPongPacket implements IPacket {

    private long timestamp;

    public PingPongPacket() {

    }

    public PingPongPacket(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public void write(MCByteBuf byteBuf) throws IOException {
        byteBuf.getOut().writeLong(this.timestamp);
    }

    @Override
    public void read(PacketData<String> data, MCByteBuf byteBuf) throws IOException {
        data.set("timestamp", byteBuf.getIn().readLong());
    }
}
