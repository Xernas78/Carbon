package dev.xernas.carbon.server.protocol.packet.play.common;

import dev.xernas.carbon.server.GameMode;
import dev.xernas.carbon.server.Identifier;
import dev.xernas.carbon.server.Position;
import dev.xernas.carbon.server.WorldType;
import dev.xernas.carbon.server.io.MCByteBuf;

import java.io.IOException;

public class CommonSpawnInfo {

    private final WorldType worldType;
    private final Identifier spawnWorldName;
    private final long hashedSeed;
    private final GameMode gameMode;
    private final GameMode lastGameMode;
    private final boolean isDebug;
    private final boolean isFlat;
    private final Identifier deathDimensionName;
    private final Position deathPosition;

    public CommonSpawnInfo(WorldType worldType, Identifier spawnWorldName, long hashedSeed,
                           GameMode gameMode, GameMode lastGameMode, boolean isDebug, boolean isFlat,
                           Identifier deathDimensionName, Position deathPosition) {
        this.worldType = worldType;
        this.spawnWorldName = spawnWorldName;
        this.hashedSeed = hashedSeed;
        this.gameMode = gameMode;
        this.lastGameMode = lastGameMode;
        this.isDebug = isDebug;
        this.isFlat = isFlat;
        this.deathDimensionName = deathDimensionName;
        this.deathPosition = deathPosition;
    }

    public void write(MCByteBuf byteBuf) throws IOException {
        byteBuf.writeIdentifier(worldType.getIdentifier());
        byteBuf.writeIdentifier(spawnWorldName);
        byteBuf.getOut().writeLong(hashedSeed);
        byteBuf.getOut().writeByte(gameMode.getNumber());
        byteBuf.getOut().writeByte(lastGameMode.getNumber());
        byteBuf.getOut().writeBoolean(isDebug);
        byteBuf.getOut().writeBoolean(isFlat);
        boolean hasDeathLoc = deathDimensionName != null && deathPosition != null;
        byteBuf.getOut().writeBoolean(hasDeathLoc);
        if (hasDeathLoc) {
            System.out.println("PAS BON DU TOUT CA");
        }
    }


}
