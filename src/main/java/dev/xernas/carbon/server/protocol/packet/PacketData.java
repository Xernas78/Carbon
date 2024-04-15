package dev.xernas.carbon.server.protocol.packet;

import java.util.HashMap;
import java.util.Map;

public class PacketData<T> {

    private final Map<T, Object> data = new HashMap<>();

    private boolean valid = true;

    @SuppressWarnings("unchecked")
    public <V> V get(T key) {
        return (V) data.get(key);
    }

    public void set(T key, Object value) {
        data.put(key, value);
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("------Packet Data------\n");
        for (Map.Entry<T, Object> entry : data.entrySet()) {
            builder.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        return builder.toString();
    }

}
