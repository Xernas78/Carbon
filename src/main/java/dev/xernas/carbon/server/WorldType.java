package dev.xernas.carbon.server;

public class WorldType {

    public static final WorldType OVERWORLD = new WorldType(Identifier.mc("overworld"));

    private final Identifier identifier;

    public WorldType(Identifier identifier) {
        this.identifier = identifier;
    }

    public Identifier getIdentifier() {
        return identifier;
    }
}
