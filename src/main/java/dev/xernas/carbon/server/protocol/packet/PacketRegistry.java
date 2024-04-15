package dev.xernas.carbon.server.protocol.packet;

import dev.xernas.carbon.client.ClientSession;
import dev.xernas.carbon.server.protocol.packet.config.FinishConfigPacket;
import dev.xernas.carbon.server.protocol.enums.Bound;
import dev.xernas.carbon.server.protocol.enums.State;
import dev.xernas.carbon.server.protocol.packet.handshake.HandshakePacket;
import dev.xernas.carbon.server.protocol.packet.login.LoginAcknowledgedPacket;
import dev.xernas.carbon.server.protocol.packet.login.LoginDisconnectPacket;
import dev.xernas.carbon.server.protocol.packet.login.LoginStartPacket;
import dev.xernas.carbon.server.protocol.packet.login.LoginSuccessPacket;
import dev.xernas.carbon.server.protocol.packet.play.JoinGamePacket;
import dev.xernas.carbon.server.protocol.packet.status.PingPongPacket;
import dev.xernas.carbon.server.protocol.packet.status.StatusPacket;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class PacketRegistry {

    private static final Map<Bound, Map<State, Map<Integer, Class<? extends IPacket>>>> packets = new HashMap<>();

    public static void registerPackets() {
        register(Bound.SERVER, State.HANDSHAKE, 0x00, HandshakePacket.class);
        register(Bound.BOTH, State.STATUS, 0x00, StatusPacket.class);
        register(Bound.BOTH, State.STATUS, 0x01, PingPongPacket.class);
        register(Bound.SERVER, State.LOGIN, 0x00, LoginStartPacket.class);
        register(Bound.CLIENT, State.LOGIN, 0x02, LoginSuccessPacket.class);
        register(Bound.SERVER, State.LOGIN, 0x03, LoginAcknowledgedPacket.class);
        register(Bound.CLIENT, State.LOGIN, 0x00, LoginDisconnectPacket.class);
        register(Bound.BOTH, State.CONFIG, 0x02, FinishConfigPacket.class);
        register(Bound.CLIENT, State.PLAY, 0x29, JoinGamePacket.class);
    }

    public static void register(Bound bound, State state, Integer id, Class<? extends IPacket> packet) {
        Map<State, Map<Integer, Class<? extends IPacket>>> boundPackets = packets.get(bound);
        if (boundPackets == null) boundPackets = new HashMap<>();
        Map<Integer, Class<? extends IPacket>> statePackets = boundPackets.get(state);
        if (statePackets == null) statePackets = new HashMap<>();
        statePackets.put(id, packet);
        boundPackets.put(state, statePackets);
        packets.put(bound, boundPackets);
    }

    public static boolean isPacket(ClientSession session, Integer id, Class<? extends IPacket> packet, Bound bound) {
        Class<? extends IPacket> packetClass = getPacketClass(session, id, bound);
        if (packetClass == null) return false;
        return packetClass.equals(packet);
    }

    public static boolean isValid(IPacket packet) {
        return packets
                .values()
                .stream()
                .anyMatch(boundPackets -> boundPackets
                        .values()
                        .stream()
                        .anyMatch(statePackets -> statePackets.containsValue(packet.getClass())));
    }

    public static int getId(ClientSession session, IPacket packet, Bound bound) {
        Map<Integer, Class<? extends IPacket>> packetMap = getPacketMap(session, bound);
        if (packetMap == null) return -1;
        int id = packetMap
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue().equals(packet.getClass()))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(-1);
        if (id == -1) packetMap = getPacketMap(session, Bound.BOTH);
        if (packetMap == null) return -1;
        id = packetMap
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue().equals(packet.getClass()))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(-1);
        return id;
    }

    public static IPacket getPacket(ClientSession session, Integer id, Bound bound) {
        Class<? extends IPacket> packetClass = getPacketClass(session, id, bound);
        if (packetClass == null) return null;
        try {
            return packetClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            //TODO Handle exception
            throw new RuntimeException(e);
        }
    }

    public static Class<? extends IPacket> getPacketClass(ClientSession session, Integer id, Bound bound) {
        Map<Integer, Class<? extends IPacket>> packetMap = getPacketMap(session, bound);
        if (packetMap == null) return null;
        Class<? extends IPacket> packetClass = packetMap.get(id);
        if (packetClass == null) packetMap = getPacketMap(session, Bound.BOTH);
        if (packetMap == null) return null;
        return packetMap.get(id);
    }

    private static Map<Integer, Class<? extends IPacket>> getPacketMap(ClientSession session, Bound bound) {
        Map<State, Map<Integer, Class<? extends IPacket>>> boundPackets = packets.get(bound);
        if (boundPackets == null) boundPackets = packets.get(Bound.BOTH);
        if (boundPackets == null) return null;
        Map<Integer, Class<? extends IPacket>> statePackets = boundPackets.get(session.getCurrentState());
        if (statePackets == null) boundPackets = packets.get(Bound.BOTH);;
        statePackets = boundPackets.get(session.getCurrentState());
        return statePackets;
    }

}
