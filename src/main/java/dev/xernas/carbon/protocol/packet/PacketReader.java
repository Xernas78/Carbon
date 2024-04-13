package dev.xernas.carbon.protocol.packet;

import dev.xernas.carbon.client.ClientSession;
import dev.xernas.carbon.protocol.packet.enums.Bound;

public class PacketReader {

    private final PacketData<String> data;
    private final Class<? extends IPacket> packetClass;

    public PacketReader(Class<? extends IPacket> packetClass, PacketData<String> data) {
        this.data = data;
        this.packetClass = packetClass;
    }

    public boolean is(Class<? extends IPacket> packetClass) {
        return this.packetClass.equals(packetClass);
    }

    public <T> T read(String key) {
        return data.get(key);
    }

    public boolean isNull() {
        return packetClass == null;
    }

}
