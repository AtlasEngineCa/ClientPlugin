package ca.atlasengine.gui.windows;

import ca.atlasengine.models.ModelManager;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtInt;
import net.minecraft.text.Text;

import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;

public class ModelModifyGUI extends LightweightGuiDescription {
    private static class AnimationSelect extends WPlainPanel {
        public WButton button;

        public AnimationSelect() {
            button = new WButton(Text.of("ERROR"));
            this.add(button, 18, 0, 5*18, 18);
            this.setSize(7*18, 18);
        }
    }

    public ModelModifyGUI(List<String> animations, UUID uuid, String modelName, int customModelData, boolean hideOnLoad, boolean gravity) {
        WGridPanel root = new WGridPanel();
        setRootPanel(root);

        root.setSize((int) (MinecraftClient.getInstance().getWindow().getScaledWidth() * 0.8), (int) (MinecraftClient.getInstance().getWindow().getScaledHeight() * 0.8));

        WLabel label = new WLabel(Text.of(modelName));
        root.add(label, 2, 1, 4, 1);

        BiConsumer<String, AnimationSelect> configurator = (String s, AnimationSelect destination) -> {
            destination.button.setOnClick(() -> {
                ModelManager.setAnimation(uuid, s);
                MinecraftClient.getInstance().setScreen(null);
            });
            destination.button.setLabel(Text.of(s));
        };

        NbtCompound compound = new NbtCompound();
        compound.put("CustomModelData", NbtInt.of(customModelData));
        ItemStack item = new ItemStack(Items.INK_SAC);
        item.setNbt(compound);

        WItem slot = new WItem(item);
        root.add(slot, 1, 1, 1, 1);

        WListPanel<String, AnimationSelect> list = new WListPanel<>(animations, AnimationSelect::new, configurator);
        list.setListItemHeight(18);

        WLabel labelA = new WLabel(Text.of("Animations"));
        root.add(labelA, root.getWidth() / 16 - 11, 1, 4, 1);
        root.add(list, root.getWidth() / 16 - 11, 4, 7, root.getHeight() / 16 - 6);

        WToggleButton hideOnLoadButton = new WToggleButton(Text.of("Hide on load"));
        hideOnLoadButton.setOnToggle((b) -> {
            ModelManager.setHideOnLoad(uuid, b);
        });

        hideOnLoadButton.setToggle(hideOnLoad);
        root.add(hideOnLoadButton, 2, 4, 5, 1);

        WToggleButton gravityButton = new WToggleButton(Text.of("Has Gravity"));
        gravityButton.setOnToggle((b) -> {
            ModelManager.setGravity(uuid, b);
        });

        gravityButton.setToggle(gravity);
        root.add(gravityButton, 2, 5, 5, 1);

        var moveButton = new WButton(Text.of("Move Model"));
        moveButton.setOnClick(() -> {
            ModelManager.moveModel(uuid);
            MinecraftClient.getInstance().setScreen(null);
        });

        root.add(moveButton, 1, 2, 5, 1);

        var stopAnimations = new WButton(Text.of("Stop Animation"));
        stopAnimations.setOnClick(() -> {
            ModelManager.setAnimation(uuid, "");
            MinecraftClient.getInstance().setScreen(null);
        });
        root.add(stopAnimations, root.getWidth() / 16 - 10, 2, 5, root.getHeight() / 16 - 5);

        root.validate(this);
    }
}
