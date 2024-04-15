package dev.xernas.carbon.server.protocol.packet.login;

import dev.xernas.carbon.server.io.MCByteBuf;
import dev.xernas.carbon.server.protocol.packet.IPacket;
import dev.xernas.carbon.server.protocol.packet.PacketData;
import dev.xernas.carbon.server.GameProfile;

import java.io.IOException;

public class LoginSuccessPacket implements IPacket {

    private final GameProfile profile;

    public LoginSuccessPacket(GameProfile profile) {
        this.profile = profile;
    }

    @Override
    public void write(MCByteBuf byteBuf) throws IOException {
        byteBuf.writeGameProfile(profile);
    }

    @Override
    public void read(PacketData<String> data, MCByteBuf byteBuf) throws IOException {

    }
}
