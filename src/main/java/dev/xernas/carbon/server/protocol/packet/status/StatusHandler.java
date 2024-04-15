package dev.xernas.carbon.server.protocol.packet.status;

import dev.xernas.carbon.Carbon;
import dev.xernas.carbon.client.ClientSession;
import dev.xernas.carbon.server.protocol.packet.IPacketHandler;
import dev.xernas.carbon.server.protocol.packet.PacketReader;
import dev.xernas.carbon.server.ServerInfo;

import java.io.IOException;

public class StatusHandler implements IPacketHandler {


    @Override
    public void handlePacket(ClientSession session, PacketReader reader) throws IOException {
        if (reader.is(StatusPacket.class)) {
            session.sendPacket(new StatusPacket(new ServerInfo(Carbon.VERSION, Carbon.PROTOCOL, 20, 10, "Salut :)")));
        } else if (reader.is(PingPongPacket.class)) {
            long timestamp = reader.read("timestamp");
            session.sendPacket(new PingPongPacket(timestamp));
        }
    }

    @Override
    public void onStateChange(ClientSession session) {

    }
}
