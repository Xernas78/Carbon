package dev.xernas.carbon.protocol.packet;

import dev.xernas.carbon.client.ClientSession;

import java.io.IOException;

public interface IPacketHandler {

    void handlePacket(ClientSession session, PacketReader reader) throws IOException;

    void onStateChange(ClientSession session) throws IOException;

}
