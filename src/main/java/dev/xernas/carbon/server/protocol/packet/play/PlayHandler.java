package dev.xernas.carbon.server.protocol.packet.play;

import dev.xernas.carbon.client.ClientSession;
import dev.xernas.carbon.server.GameMode;
import dev.xernas.carbon.server.Identifier;
import dev.xernas.carbon.server.WorldType;
import dev.xernas.carbon.server.protocol.packet.IPacketHandler;
import dev.xernas.carbon.server.protocol.packet.PacketReader;
import dev.xernas.carbon.server.protocol.packet.play.common.CommonSpawnInfo;

import java.io.IOException;
import java.util.List;

public class PlayHandler implements IPacketHandler {
    @Override
    public void handlePacket(ClientSession session, PacketReader reader) throws IOException {

    }

    @Override
    public void onStateChange(ClientSession session) throws IOException {
        Identifier worldName = Identifier.of("xernas", "carbon");
        CommonSpawnInfo spawnInfo = new CommonSpawnInfo(
                WorldType.OVERWORLD, worldName, 0,
                GameMode.SURVIVAL, GameMode.UNDEFINED, false,
                false, null, null
        );
        JoinGamePacket packet = new JoinGamePacket(0, false, List.of(worldName), 20,
                12, 12, false, false,
                false, spawnInfo, 0);
        session.sendPacket(packet);
    }
}
