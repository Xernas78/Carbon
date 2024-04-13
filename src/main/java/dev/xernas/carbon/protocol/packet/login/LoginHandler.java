package dev.xernas.carbon.protocol.packet.login;

import dev.xernas.carbon.client.ClientSession;
import dev.xernas.carbon.protocol.packet.IPacketHandler;
import dev.xernas.carbon.protocol.packet.PacketReader;
import dev.xernas.carbon.protocol.packet.enums.State;
import dev.xernas.carbon.server.GameProfile;

import java.io.IOException;
import java.util.UUID;

public class LoginHandler implements IPacketHandler {

    @Override
    public void handlePacket(ClientSession session, PacketReader reader) throws IOException {
        if (reader.is(LoginStartPacket.class)) {
            String username = reader.read("username");
            UUID uuid = reader.read("uuid");
            GameProfile profile = new GameProfile(username, uuid);
            session.sendPacket(new LoginSuccessPacket(profile));
            session.setProfile(profile);
        } else if (reader.is(LoginAcknowledgedPacket.class)) {
            session.nextState(State.CONFIG);
        }
    }

    @Override
    public void onStateChange(ClientSession session) {

    }

}
