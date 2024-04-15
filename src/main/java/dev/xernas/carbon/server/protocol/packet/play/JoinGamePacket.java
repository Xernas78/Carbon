package dev.xernas.carbon.server.protocol.packet.play;

import dev.xernas.carbon.server.WorldType;
import dev.xernas.carbon.server.GameMode;
import dev.xernas.carbon.server.Identifier;
import dev.xernas.carbon.server.Position;
import dev.xernas.carbon.server.io.MCByteBuf;
import dev.xernas.carbon.server.protocol.packet.IPacket;
import dev.xernas.carbon.server.protocol.packet.PacketData;
import dev.xernas.carbon.server.protocol.packet.play.common.CommonSpawnInfo;

import java.io.IOException;
import java.util.List;

public class JoinGamePacket implements IPacket {

    private final int entityId;
    private final boolean hardcore;
    private final List<Identifier> worldNames;
    private final int maxPlayers;
    private final int renderDistance;
    private final int simulationDistance;
    private final boolean doReducedDebugInfo;
    private final boolean doImmediateRespawn;
    private final boolean doLimitedCrafting;
    private final CommonSpawnInfo spawnInfo;
    private final int portalCooldown;

    public JoinGamePacket(int entityId, boolean hardcore, List<Identifier> worldNames, int maxPlayers,
                          int renderDistance, int simulationDistance, boolean doReducedDebugInfo, boolean doImmediateRespawn,
                          boolean doLimitedCrafting, CommonSpawnInfo spawnInfo, int portalCooldown) {
        this.entityId = entityId;
        this.hardcore = hardcore;
        this.worldNames = worldNames;
        this.maxPlayers = maxPlayers;
        this.renderDistance = renderDistance;
        this.simulationDistance = simulationDistance;
        this.doReducedDebugInfo = doReducedDebugInfo;
        this.doImmediateRespawn = doImmediateRespawn;
        this.doLimitedCrafting = doLimitedCrafting;
        this.spawnInfo = spawnInfo;
        this.portalCooldown = portalCooldown;
    }

    @Override
    public void write(MCByteBuf byteBuf) throws IOException {
        byteBuf.getOut().writeInt(entityId);
        byteBuf.getOut().writeBoolean(hardcore);
        byteBuf.writeIdentifierList(worldNames);
        byteBuf.writeVarInt(maxPlayers);
        byteBuf.writeVarInt(renderDistance);
        byteBuf.writeVarInt(simulationDistance);
        byteBuf.getOut().writeBoolean(doReducedDebugInfo);
        byteBuf.getOut().writeBoolean(!doImmediateRespawn);
        byteBuf.getOut().writeBoolean(doLimitedCrafting);
        spawnInfo.write(byteBuf);
        byteBuf.writeVarInt(portalCooldown);
    }

    @Override
    public void read(PacketData<String> data, MCByteBuf byteBuf) throws IOException {

    }
}
