package dev.xernas.carbon.server.io;

import dev.xernas.carbon.server.GameProfile;
import dev.xernas.carbon.server.Identifier;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MCByteBuf {
    private final DataInputStream in;
    private final DataOutputStream out;

    private final int SEGMENT_BITS = 0x7F;
    private final int CONTINUE_BIT = 0x80;

    public MCByteBuf(DataInputStream in, DataOutputStream out) {
        this.in = in;
        this.out = out;
    }

    public MCByteBuf(DataInputStream in, int length, DataOutputStream out) throws IOException {
        ByteBuffer buffer = ByteBuffer.wrap(in.readNBytes(length));
        this.in = new DataInputStream(new ByteBufferInputStream(buffer));
        this.out = out;
    }

    public DataInputStream getIn() {
        return in;
    }

    public DataOutputStream getOut() {
        return out;
    }

    public int readVarInt() throws IOException {
        int value = 0;
        int position = 0;
        byte currentByte;

        while (true) {
            currentByte = in.readByte();
            value |= (currentByte & SEGMENT_BITS) << position;
            if ((currentByte & CONTINUE_BIT) == 0) break;

            position += 7;

            if (position >= 32) throw new RuntimeException("VarInt is too big");
        }

        return value;
    }

    public void writeVarInt(int value) throws IOException {
        while (true) {
            if ((value & ~SEGMENT_BITS) == 0) {
                out.writeByte(value);
                return;
            }

            out.writeByte((value & SEGMENT_BITS) | CONTINUE_BIT);

            // Note: >>> means that the sign bit is shifted with the rest of the number rather than being left alone
            value >>>= 7;
        }
    }

    public String readString(int limit) throws IOException {
        int size = readVarInt();
        if (size > limit * 4) {
            System.out.println("String too long (" + size + " > " + limit + " * 4)");
        }
        byte[] stringBytes = new byte[size];
        in.readFully(stringBytes);
        String string = new String(stringBytes);
        if (string.length() > limit) {
            System.out.println("String too long (" + string.length() + " > " + limit + ")");
        }
        return string;
    }

    public void writeString(String s, int i) throws IOException {
        if (s.length() > i) {
            int j = s.length();

            System.out.println("String too big (was " + j + " characters, max " + i + ")");
        } else {
            byte[] abyte = s.getBytes(StandardCharsets.UTF_8);
            int k = i * 3;

            if (abyte.length > k) {
                System.out.println("String too big (was " + abyte.length + " bytes encoded, max " + k + ")");
            } else {
                writeVarInt(abyte.length);
                out.write(abyte);
            }
        }
    }

    public UUID readUUID() throws IOException {
        return new UUID(in.readLong(), in.readLong());
    }

    public void writeUUID(UUID uuid) throws IOException {
        out.writeLong(uuid.getMostSignificantBits());
        out.writeLong(uuid.getLeastSignificantBits());
    }

    public void writeProperty(String name, String value, String signature) throws IOException {
        writeString(name, 32767);
        writeString(value, 32767);
        out.writeBoolean(signature != null && !signature.isEmpty());
        if (signature != null) {
            writeString(signature, 32767);
        }
    }

    public void writeGameProfile(GameProfile profile) throws IOException {
        writeUUID(profile.getUUID());
        writeString(profile.getUsername(), 16);
        Map<String, GameProfile.Property> properties = profile.getProperties();
        writeVarInt(properties.size());
        for (Map.Entry<String, GameProfile.Property> entry : properties.entrySet()) {
            GameProfile.Property property = entry.getValue();
            writeProperty(entry.getKey(), property.getValue(), property.getSignature());
        }
    }

    public void writeIdentifier(Identifier identifier) throws IOException {
        writeString(identifier.toString(), 32767);
    }

    public void writeIdentifierList(List<Identifier> list) throws IOException {
        writeVarInt(list.size());
        for (Identifier identifier : list) {
            writeIdentifier(identifier);
        }
    }
}
