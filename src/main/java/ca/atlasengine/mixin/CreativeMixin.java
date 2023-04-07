package ca.atlasengine.mixin;

import com.google.common.collect.Sets;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtInt;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ca.atlasengine.Mod;
import ca.atlasengine.custom_blocks.BlockLoader;
import ca.atlasengine.models.ModelLoader;

import java.util.Set;

@Mixin(CreativeInventoryScreen.class)
public class CreativeMixin {
    protected final Set<Slot> cursorDragSlots = Sets.newHashSet();

    @Final
    @Shadow
    static final SimpleInventory INVENTORY = new SimpleInventory(45);

    @Inject(at = @At("TAIL"), method = "setSelectedTab")
    private void setSelectedTab(ItemGroup group, CallbackInfo ci) {
        if (group == Mod.ATLASENGINE_LOGO) {
            this.cursorDragSlots.clear();
            INVENTORY.clear();

            CreativeInventoryScreen.CreativeScreenHandler handler = ((CreativeInventoryScreen.CreativeScreenHandler) MinecraftClient.getInstance().player.currentScreenHandler);
            handler.itemList.clear();

            int i = 0;
            for (BlockLoader.BlockData blockData : BlockLoader.blocks.values()) {
                NbtCompound compound = new NbtCompound();
                compound.put("CustomModelData", NbtInt.of(blockData.id()));

                ItemStack item = new ItemStack(Items.PAPER);
                item.setNbt(compound);
                item.setCustomName(Text.of(blockData.name()).getWithStyle(Style.EMPTY).get(0));

                if (i <= 44) {
                    INVENTORY.setStack(i, item);
                }

                handler.itemList.add(item);

                i++;
            }

        } else if (group == Mod.ATLASENGINE_MODEL) {
            this.cursorDragSlots.clear();
            INVENTORY.clear();

            CreativeInventoryScreen.CreativeScreenHandler handler = ((CreativeInventoryScreen.CreativeScreenHandler) MinecraftClient.getInstance().player.currentScreenHandler);
            handler.itemList.clear();

            int i = 0;
            for (ModelLoader.ModelData blockData : ModelLoader.models.values()) {
                NbtCompound compound = new NbtCompound();
                compound.put("CustomModelData", NbtInt.of(blockData.customModelData()));

                ItemStack item = new ItemStack(Items.INK_SAC);
                item.setNbt(compound);
                item.setCustomName(Text.of(blockData.name()).getWithStyle(Style.EMPTY).get(0));

                if (i <= 44) {
                    INVENTORY.setStack(i, item);
                }

                handler.itemList.add(item);

                i++;
            }
        }
    }
}
