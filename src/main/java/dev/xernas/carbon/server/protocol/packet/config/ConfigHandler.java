package dev.xernas.carbon.server.protocol.packet.config;

import dev.xernas.carbon.client.ClientSession;
import dev.xernas.carbon.server.protocol.packet.IPacketHandler;
import dev.xernas.carbon.server.protocol.packet.PacketReader;
import dev.xernas.carbon.server.protocol.enums.State;

import java.io.IOException;

public class ConfigHandler implements IPacketHandler {
    @Override
    public void handlePacket(ClientSession session, PacketReader reader) throws IOException {
        if (reader.is(FinishConfigPacket.class)) {
            session.nextState(State.PLAY);
        }
    }

    @Override
    public void onStateChange(ClientSession session) throws IOException {
        session.sendPacket(new FinishConfigPacket());
    }
}
