package ca.atlasengine;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems {
    public static final Item LOGO = registerItem("logo",
            new Item(new FabricItemSettings().group(Mod.ATLASENGINE_LOGO)));

    public static final Item MODEL = registerItem("model",
            new Item(new FabricItemSettings().group(Mod.ATLASENGINE_MODEL)));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier("atlasengine", name), item);
    }

    public static void registerModItems() {
        System.out.println("Registering Mod Items for AtlasEngine");
    }
}