package dev.xernas.carbon.client;

import dev.xernas.carbon.io.MCByteBuf;
import dev.xernas.carbon.protocol.packet.*;
import dev.xernas.carbon.protocol.packet.configuration.ConfigurationHandler;
import dev.xernas.carbon.protocol.packet.enums.Bound;
import dev.xernas.carbon.protocol.packet.enums.State;
import dev.xernas.carbon.protocol.packet.handshake.HandshakeHandler;
import dev.xernas.carbon.protocol.packet.login.LoginDisconnectPacket;
import dev.xernas.carbon.protocol.packet.login.LoginHandler;
import dev.xernas.carbon.protocol.packet.status.StatusHandler;
import dev.xernas.carbon.server.GameProfile;
import dev.xernas.carbon.server.Server;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;

public class ClientSession implements Runnable {

    private final ClientManager manager;
    private final Integer sessionId;
    private final Socket client;
    private final MCByteBuf byteBuf;

    private final HandshakeHandler handshakeHandler;
    private final StatusHandler statusHandler;
    private final LoginHandler loginHandler;
    private final ConfigurationHandler configurationHandler;

    private State currentState;
    private GameProfile profile;

    public ClientSession(ClientManager manager, Socket client) throws IOException {
        this.manager = manager;
        this.client = client;
        this.byteBuf = new MCByteBuf(new DataInputStream(client.getInputStream()), new DataOutputStream(client.getOutputStream()));
        this.currentState = State.HANDSHAKE;
        this.handshakeHandler = new HandshakeHandler();
        this.statusHandler = new StatusHandler();
        this.loginHandler = new LoginHandler();
        this.configurationHandler = new ConfigurationHandler();
        this.sessionId = manager.newSession(this);
    }

    @Override
    public void run() {
        while (true) {
            try {
                PacketData<String> data = receivePacket();
                if (data != null) {
                    System.out.println(data);
                    IPacketHandler handler = getHandler();
                    if (handler != null) {
                        PacketReader reader = new PacketReader(PacketRegistry.getPacketClass(this, data.get("packetId"), Bound.SERVER), data);
                        if (!reader.isNull()) {
                            handler.handlePacket(this, reader);
                        }
                    }
                }
            } catch (IOException e) {
                //TODO Handle exception
                break;
            }
        }
        try {
            disconnect();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public PacketData<String> receivePacket() throws IOException {
        int length = byteBuf.readVarInt();
        int packetId = byteBuf.readVarInt();
        IPacket packet = PacketRegistry.getPacket(this, packetId, Bound.SERVER);
        if (packet == null || !PacketRegistry.isValid(packet)) return null;
        PacketData<String> data = new PacketData<>();
        packet.read(data, byteBuf);
        if (!data.isValid()) return null;
        data.set("packetName", packet.getClass().getSimpleName());
        data.set("packetLength", length);
        data.set("packetId", packetId);
        return data;
    }

    public void sendPacket(IPacket packet) throws IOException {
        ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
        DataOutputStream dataOut = new DataOutputStream(byteArrayOut);
        MCByteBuf tempByteBuf = new MCByteBuf(byteBuf.getIn(), dataOut);
        int packetId = PacketRegistry.getId(this, packet, Bound.CLIENT);
        if (packetId == -1) return;
        tempByteBuf.writeVarInt(packetId);
        packet.write(tempByteBuf);
        byteBuf.writeVarInt(byteArrayOut.size());
        byteBuf.getOut().write(byteArrayOut.toByteArray());
        byteBuf.getOut().flush();
        tempByteBuf.getOut().close();
    }

    public void disconnect() throws IOException {
        if (currentState == State.LOGIN) {
            sendPacket(new LoginDisconnectPacket("Disconnected"));
        } else if (currentState == State.PLAY) {
            //sendPacket(new DisconnectPacket());
        }
        manager.removeSession(sessionId);
        System.out.println("Session with id " + sessionId + " disconnected");
        client.close();
    }

    public void nextState(State state) throws IOException {
        currentState = state;
        System.out.println("State changed to " + state);
        IPacketHandler handler = getHandler();
        if (handler != null) handler.onStateChange(this);
    }

    public void setProfile(GameProfile profile) {
        this.profile = profile;
    }

    public IPacketHandler getHandler() {
        return switch (currentState) {
            case HANDSHAKE -> handshakeHandler;
            case STATUS -> statusHandler;
            case LOGIN -> loginHandler;
            case CONFIG -> configurationHandler;
            default -> null;
        };
    }

    public Socket getClient() {
        return client;
    }

    public MCByteBuf getByteBuf() {
        return byteBuf;
    }

    public State getCurrentState() {
        return currentState;
    }

    public GameProfile getProfile() {
        return profile;
    }
}
