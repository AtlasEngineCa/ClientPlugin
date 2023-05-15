package ca.atlasengine.models;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

import java.util.UUID;

public class ModelManager {
    static Identifier animationIdentifier = new Identifier("atlasengine", "model");
    static boolean placing = false;

    public static boolean updateScroll(double vertical) {
        if (placing) {
            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeByte(0);
            buf.writeDouble(vertical);

            ClientPlayNetworking.send(animationIdentifier, buf);
        }

        return placing;
    }

    public static void setPlacing(boolean placing) {
        ModelManager.placing = placing;
    }

    public static boolean isPlacing() {
        return placing;
    }

    public static void updateRotation(double i) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeByte(1);
        buf.writeDouble(i);

        ClientPlayNetworking.send(animationIdentifier, buf);
    }

    private static long lastUpdate = 0;
    public static void changeLookDirection(float headYaw, float pitch) {
        if (System.currentTimeMillis() - lastUpdate < 100) {
            return;
        }

        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeByte(2);
        buf.writeFloat(headYaw);
        buf.writeFloat(pitch);

        ClientPlayNetworking.send(animationIdentifier, buf);
        lastUpdate = System.currentTimeMillis();
    }

    public static void setAnimation(UUID uuid, String animation) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeByte(3);
        buf.writeUuid(uuid);
        buf.writeString(animation);

        ClientPlayNetworking.send(animationIdentifier, buf);
    }

    public static void deleteModel(UUID uuid) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeByte(4);
        buf.writeUuid(uuid);

        ClientPlayNetworking.send(animationIdentifier, buf);
    }

    public static void moveModel(UUID uuid) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeByte(5);
        buf.writeUuid(uuid);

        ClientPlayNetworking.send(animationIdentifier, buf);
    }

    public static void setHideOnLoad(UUID uuid, Boolean b) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeByte(6);
        buf.writeUuid(uuid);
        buf.writeBoolean(b);

        ClientPlayNetworking.send(animationIdentifier, buf);
    }

    public static void setGravity(UUID uuid, Boolean b) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeByte(7);
        buf.writeUuid(uuid);
        buf.writeBoolean(b);

        ClientPlayNetworking.send(animationIdentifier, buf);
    }
}
