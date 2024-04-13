package dev.xernas.carbon.server;

import java.util.*;

public class GameProfile {

    private final String username;
    private final UUID uuid;
    private final Map<String, Property> properties;

    public static class Property {
        private final String value;
        private String signature;

        public Property(String value) {
            this.value = value;
        }

        public Property(String value, String signature) {
            this.value = value;
            this.signature = signature;
        }


        public String getValue() {
            return value;
        }

        public String getSignature() {
            return signature;
        }

    }

    public GameProfile(String username, UUID uuid) {
        this.username = username;
        this.uuid = uuid;
        this.properties = new HashMap<>();
    }

    public GameProfile(String username, UUID uuid, Map<String, Property> properties) {
        this.username = username;
        this.uuid = uuid;
        this.properties = properties;
    }

    public void setProperty(String key, String value) {
        properties.put(key, new Property(value));
    }
    public void setProperty(String key, String value, String signature) {
        properties.put(key, new Property(value, signature));
    }

    public Property getProperty(String key) {
        return properties.get(key);
    }

    public String getUsername() {
        return username;
    }

    public UUID getUUID() {
        return uuid;
    }

    public Map<String, Property> getProperties() {
        return properties;
    }
}
