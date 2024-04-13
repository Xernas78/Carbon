package dev.xernas.carbon.protocol.packet.status;

import dev.xernas.carbon.io.MCByteBuf;
import dev.xernas.carbon.protocol.packet.IPacket;
import dev.xernas.carbon.protocol.packet.PacketData;
import dev.xernas.carbon.server.ServerInfo;

import java.io.IOException;

public class StatusPacket implements IPacket {

    private ServerInfo serverInfo;

    public StatusPacket() {

    }

    public StatusPacket(ServerInfo serverInfo) {
        this.serverInfo = serverInfo;
    }

    @Override
    public void write(MCByteBuf byteBuf) throws IOException {
        byteBuf.writeString(serverInfo.toJson(), 32767);
    }

    @Override
    public void read(PacketData<String> data, MCByteBuf byteBuf) throws IOException {

    }

}
