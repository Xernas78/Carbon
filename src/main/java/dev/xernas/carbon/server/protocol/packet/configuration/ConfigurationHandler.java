package dev.xernas.carbon.server.protocol.packet.configuration;

import dev.xernas.carbon.client.ClientSession;
import dev.xernas.carbon.server.protocol.packet.IPacketHandler;
import dev.xernas.carbon.server.protocol.packet.PacketReader;
import dev.xernas.carbon.server.protocol.enums.State;

import java.io.IOException;

public class ConfigurationHandler implements IPacketHandler {
    @Override
    public void handlePacket(ClientSession session, PacketReader reader) throws IOException {

    }

    @Override
    public void onStateChange(ClientSession session) throws IOException {
        session.sendPacket(new FinishConfigurationPacket());
        session.nextState(State.PLAY);
    }
}
