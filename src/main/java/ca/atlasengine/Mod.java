package ca.atlasengine;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class Mod implements ModInitializer {
    public static final ItemGroup ATLASENGINE_LOGO = FabricItemGroupBuilder
            .build(new Identifier("atlasengine", "logo"), () -> new ItemStack(ModItems.LOGO));

    public static final ItemGroup ATLASENGINE_MODEL = FabricItemGroupBuilder
            .build(new Identifier("atlasengine", "model"), () -> new ItemStack(ModItems.MODEL));

    @Override
    public void onInitialize() {
        ModItems.registerModItems();
    }
}