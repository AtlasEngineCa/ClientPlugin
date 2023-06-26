package ca.atlasengine;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final Item LOGO = registerItem("logo",
            new Item(new FabricItemSettings()));

    public static final Item MODEL = registerItem("model",
            new Item(new FabricItemSettings()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier("atlasengine", name), item);
    }

    public static void registerModItems() {
        System.out.println("Registering Mod Items for AtlasEngine");
    }
}