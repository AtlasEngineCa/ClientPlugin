package ca.atlasengine.custom_blocks;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.PacketByteBuf;
import ca.atlasengine.gui.GUIScreen;
import ca.atlasengine.gui.windows.BlockAnimationGUI;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BlockLoader {
    private static GUIScreen providedScreen;

    public record BlockData(String name, int id, Map<String, String> props) {}
    public static Map<String, BlockData> blocks = new HashMap<>();

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
                int blockId = packetByteBuf.readInt();
                String name = packetByteBuf.readString();

                Map<String, String> props = new HashMap<>();
                int size = packetByteBuf.readInt();
                for (int i = 0; i < size; i++) {
                    String key = packetByteBuf.readString();
                    String value = packetByteBuf.readString();
                    props.put(key, value);
                }

                blocks.put(name, new BlockData(name, blockId, props));
            } else if (id == 1) {
                blocks.clear();
            } else if (id == 2) {
                UUID entryId = packetByteBuf.readUuid();
                int frameTime = packetByteBuf.readByte() & 0xFF;
                boolean playOnLoad = packetByteBuf.readBoolean();

                providedScreen = new GUIScreen(new BlockAnimationGUI(entryId, frameTime, playOnLoad));
            }
        }
    }
}