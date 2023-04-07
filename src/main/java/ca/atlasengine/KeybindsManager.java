package ca.atlasengine;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class KeybindsManager {
    static Identifier keypressIdentifier = new Identifier("atlasengine", "keypress");

    public static void sendDestroy() {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeByte(0);

        ClientPlayNetworking.send(keypressIdentifier, buf);
    }

    public static void sendFrameBackwards() {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeByte(1);

        ClientPlayNetworking.send(keypressIdentifier, buf);
    }

    public static void sendFrameForwards() {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeByte(2);

        ClientPlayNetworking.send(keypressIdentifier, buf);
    }

    public static void sendRemove() {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeByte(3);

        ClientPlayNetworking.send(keypressIdentifier, buf);
    }

    public static void sendAdd() {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeByte(4);

        ClientPlayNetworking.send(keypressIdentifier, buf);
    }

    public static void sendOpen() {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeByte(5);

        ClientPlayNetworking.send(keypressIdentifier, buf);
    }

    public static void sendCreateRegion() {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeByte(0);
        buf.writeByte(0);

        ClientPlayNetworking.send(keypressIdentifier, buf);
    }

    public static void sendCreateBlocks() {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeByte(1);
        buf.writeByte(0);

        ClientPlayNetworking.send(keypressIdentifier, buf);
    }

    public static void sendCreateCutscene() {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeByte(2);
        buf.writeByte(0);

        ClientPlayNetworking.send(keypressIdentifier, buf);
    }
}
