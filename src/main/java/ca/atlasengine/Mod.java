package ca.atlasengine;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class Mod implements ModInitializer {
    public static final ItemGroup ATLASENGINE_LOGO = FabricItemGroup.builder()
            .displayName(Text.translatable("itemGroup.atlasengine.logo"))
            .icon(() -> new ItemStack(ModItems.LOGO))
            .build();

    public static final ItemGroup ATLASENGINE_MODEL = FabricItemGroup.builder()
            .displayName(Text.translatable("itemGroup.atlasengine.model"))
            .icon(() -> new ItemStack(ModItems.MODEL))
            .build();

    @Override
    public void onInitialize() {
        ModItems.registerModItems();

        var LOGO = RegistryKey.of(RegistryKeys.ITEM_GROUP, new Identifier("atlasengine", "logo"));
        var MODEL = RegistryKey.of(RegistryKeys.ITEM_GROUP, new Identifier("atlasengine", "model"));

        Registry.register(Registries.ITEM_GROUP,
                LOGO,
                ATLASENGINE_LOGO);

        Registry.register(Registries.ITEM_GROUP,
                MODEL,
                ATLASENGINE_MODEL);

        ItemGroupEvents.modifyEntriesEvent(LOGO).register(content -> {
            content.add(ModItems.LOGO);
        });

        ItemGroupEvents.modifyEntriesEvent(MODEL).register(content -> {
            content.add(ModItems.MODEL);
        });
    }
}