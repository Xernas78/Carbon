package dev.xernas.carbon.protocol.packet.handshake;

import dev.xernas.carbon.client.ClientSession;
import dev.xernas.carbon.protocol.packet.IPacketHandler;
import dev.xernas.carbon.protocol.packet.PacketData;
import dev.xernas.carbon.protocol.packet.PacketReader;
import dev.xernas.carbon.protocol.packet.PacketRegistry;
import dev.xernas.carbon.protocol.packet.enums.Bound;
import dev.xernas.carbon.protocol.packet.enums.State;

import java.io.IOException;

public class HandshakeHandler implements IPacketHandler {
    @Override
    public void handlePacket(ClientSession session, PacketReader reader) throws IOException {
        if (!reader.is(HandshakePacket.class)) return;
        int protocol = reader.read("protocol");
        String host = reader.read("host");
        int port = reader.read("port");
        int state = reader.read("nextState");
        switch (state) {
            case 1 -> session.nextState(State.STATUS);
            case 2 -> session.nextState(State.LOGIN);
        }
    }

    @Override
    public void onStateChange(ClientSession session) {

    }
}
