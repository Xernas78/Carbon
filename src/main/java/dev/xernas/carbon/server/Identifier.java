package dev.xernas.carbon.server;

public class Identifier {

    private final String key;
    private final String value;

    public Identifier(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static Identifier of(String key, String value) {
        return new Identifier(key, value);
    }

    public static Identifier mc(String value) {
        return new Identifier("minecraft", value);
    }

    public String toString() {
        return key + ":" + value;
    }

}
