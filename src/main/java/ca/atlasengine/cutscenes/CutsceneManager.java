package ca.atlasengine.cutscenes;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

import java.util.List;

public class CutsceneManager {
    static Identifier cutsceneIdentifier = new Identifier("atlasengine", "cutscene");

    public static void sendSave(List<CutsceneSubFrame> frames) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeByte(0);

        buf.writeInt(frames.size());
        for (CutsceneSubFrame frame : frames) {
            float yaw = 0;
            float pitch = 0;
            int transitionTime = 0;
            int waitTime = 0;

            try {
                yaw = Float.parseFloat(frame.yaw());
            } catch (NumberFormatException ignored) { }

            try {
                pitch = Float.parseFloat(frame.pitch());
            } catch (NumberFormatException ignored) { }

            try {
                transitionTime = Integer.parseInt(frame.transitionTime());
            } catch (NumberFormatException ignored) { }

            try {
                waitTime = Integer.parseInt(frame.waitTime());
            } catch (NumberFormatException ignored) { }

            buf.writeFloat(yaw);
            buf.writeFloat(pitch);
            buf.writeInt(transitionTime);
            buf.writeInt(waitTime);
        }

        ClientPlayNetworking.send(cutsceneIdentifier, buf);
    }

    public static void sendCapture(int index) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeByte(1);
        buf.writeInt(index);

        ClientPlayNetworking.send(cutsceneIdentifier, buf);
    }
}
