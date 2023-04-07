package ca.atlasengine.cutscenes;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.PacketByteBuf;
import ca.atlasengine.gui.GUIScreen;
import ca.atlasengine.gui.windows.CutsceneGUI;

public class CutsceneReceiver {
    private static GUIScreen providedScreen;
    private static CutsceneGUI triggerGUI;

    public static void receive(PacketByteBuf packetByteBuf) {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (providedScreen != null) {
                MinecraftClient.getInstance().setScreen(providedScreen);
                providedScreen = null;
            }
        });

        while (packetByteBuf.readableBytes() > 0) {
            int id = packetByteBuf.readByte();

            if (id == 0) {
                triggerGUI = new CutsceneGUI(packetByteBuf);
                providedScreen = new GUIScreen(triggerGUI);
            }
        }

    }
}
