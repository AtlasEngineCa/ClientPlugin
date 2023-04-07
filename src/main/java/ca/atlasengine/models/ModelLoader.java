package ca.atlasengine.models;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.PacketByteBuf;
import ca.atlasengine.gui.GUIScreen;
import ca.atlasengine.gui.windows.ModelModifyGUI;

import java.util.*;

public class ModelLoader {
    private static GUIScreen providedScreen;

    public record ModelData (UUID id, String name, int customModelData) implements Comparator {
        @Override
        public int compare(Object o, Object t1) {
            if (o instanceof ModelData modelData && t1 instanceof ModelData modelData1) {
                return modelData.id().compareTo(modelData1.id());
            }
            return 0;
        }
    }
    public static Map<String, ModelData> models = new TreeMap<>();

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
                UUID modelId = packetByteBuf.readUuid();
                String name = packetByteBuf.readString();
                int customModelData = packetByteBuf.readInt();
                models.put(name, new ModelData(modelId, name, customModelData));
            } else if (id == 1) {
                models.clear();
            } else if (id == 2) {
                ModelManager.setPlacing(true);
            } else if (id == 3) {
                ModelManager.setPlacing(false);
            } else if (id == 4) {
                UUID uuid = packetByteBuf.readUuid();
                String name = packetByteBuf.readString();
                int customModelData = packetByteBuf.readInt();
                boolean hideOnLoad = packetByteBuf.readBoolean();

                int count = packetByteBuf.readInt();
                List<String> animations = new ArrayList<>();
                for (int i = 0; i < count; i++) {
                    animations.add(packetByteBuf.readString());
                }

                providedScreen = new GUIScreen(new ModelModifyGUI(animations, uuid, name, customModelData, hideOnLoad));
            }
        }
    }
}