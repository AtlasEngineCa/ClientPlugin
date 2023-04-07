package ca.atlasengine.gui.windows;

import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.*;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.UUID;

public class BlockAnimationGUI extends LightweightGuiDescription {
    static Identifier blockIdentifier = new Identifier("atlasengine", "blocks");

    public BlockAnimationGUI(UUID id, int frameTime, boolean playOnLoad) {
        WGridPanel root = new WGridPanel();
        setRootPanel(root);

        root.setSize(250, 140);

        WLabel label = new WLabel(Text.of("Block Animation (" + id + ")"));
        root.add(label, 1, 1, 5, 1);

        WLabeledSlider slider = new WLabeledSlider(0, 255, Text.of(String.valueOf(frameTime)));
        slider.setSize(200, 20);
        slider.setValue(frameTime);

        slider.setLabelUpdater(value -> Text.of(String.valueOf(value)));

        WLabel label2 = new WLabel(Text.of("Frame Time (Ticks)"));

        root.add(label2, 2, 4, 4, 1);
        root.add(slider, 2, 5, 4, 1);

        WToggleButton toggleButton = new WToggleButton(Text.of("Play on load"));
        toggleButton.setToggle(playOnLoad);

        root.add(toggleButton, 2, 2, 4, 1);

        WButton save = new WButton(Text.of("Save"));
        save.setOnClick(() -> {
            sendUpdate(slider.getValue(), toggleButton.getToggle());
            MinecraftClient.getInstance().setScreen(null);
        });

        root.add(save, 9, 6, 4, 1);

        root.validate(this);
    }

    public static void sendUpdate(int value, Boolean toggle) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeByte(0);
        buf.writeByte((byte) value);
        buf.writeByte(toggle ? 1 : 0);

        ClientPlayNetworking.send(blockIdentifier, buf);
    }
}
