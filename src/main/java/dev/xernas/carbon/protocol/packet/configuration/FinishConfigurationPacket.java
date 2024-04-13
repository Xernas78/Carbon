package dev.xernas.carbon.protocol.packet.configuration;

import dev.xernas.carbon.io.MCByteBuf;
import dev.xernas.carbon.protocol.packet.IPacket;
import dev.xernas.carbon.protocol.packet.PacketData;

import java.io.IOException;

public class FinishConfigurationPacket implements IPacket {

    public FinishConfigurationPacket() {}

    @Override
    public void write(MCByteBuf byteBuf) throws IOException {

    }

    @Override
    public void read(PacketData<String> data, MCByteBuf byteBuf) throws IOException {

    }
}
