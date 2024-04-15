package dev.xernas.carbon.server;

public enum GameMode {

    UNDEFINED(-1),
    SURVIVAL(0),
    CREATIVE(1),
    ADVENTURE(2),
    SPECTATOR(3);

    private final int number;

    GameMode(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}
