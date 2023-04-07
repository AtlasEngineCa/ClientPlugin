package ca.atlasengine;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;
import ca.atlasengine.custom_blocks.BlockLoader;
import ca.atlasengine.cutscenes.CutsceneReceiver;
import ca.atlasengine.models.ModelLoader;

@Environment(EnvType.CLIENT)
public class ClientMod implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(
                new Identifier("atlasengine", "blocks"),
                (minecraftClient, clientPlayNetworkHandler, packetByteBuf, packetSender) -> BlockLoader.receive(packetByteBuf)
        );

        ClientPlayNetworking.registerGlobalReceiver(
                new Identifier("atlasengine", "models"),
                (minecraftClient, clientPlayNetworkHandler, packetByteBuf, packetSender) -> ModelLoader.receive(packetByteBuf)
        );

        ClientPlayNetworking.registerGlobalReceiver(
                new Identifier("atlasengine", "cutscenes"),
                (minecraftClient, clientPlayNetworkHandler, packetByteBuf, packetSender) -> CutsceneReceiver.receive(packetByteBuf)
        );

        Keybinds.next = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.atlasengine.nextframe", // The translation key of the keybinding's name
                InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
                GLFW.GLFW_KEY_RIGHT_BRACKET, // The keycode of the key
                "category.atlasengine.animation" // The translation key of the keybinding's category.
        ));

        Keybinds.previous = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.atlasengine.previousframe", // The translation key of the keybinding's name
                InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
                GLFW.GLFW_KEY_LEFT_BRACKET, // The keycode of the key
                "category.atlasengine.animation" // The translation key of the keybinding's category.
        ));

        Keybinds.createBlocks = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.atlasengine.createblockregion", // The translation key of the keybinding's name
                InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
                GLFW.GLFW_KEY_P, // The keycode of the key
                "category.atlasengine.animation" // The translation key of the keybinding's category.
        ));

        Keybinds.createCutscene = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.atlasengine.createcutscene", // The translation key of the keybinding's name
                InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
                GLFW.GLFW_KEY_U, // The keycode of the key
                "category.atlasengine.animation" // The translation key of the keybinding's category.
        ));

        Keybinds.destroy = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.atlasengine.destroy", // The translation key of the keybinding's name
                InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
                GLFW.GLFW_KEY_X, // The keycode of the key
                "category.atlasengine.animation" // The translation key of the keybinding's category.
        ));

        Keybinds.add = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.atlasengine.add", // The translation key of the keybinding's name
                InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
                61, // The keycode of the key
                "category.atlasengine.animation" // The translation key of the keybinding's category.
        ));

        Keybinds.remove = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.atlasengine.remove", // The translation key of the keybinding's name
                InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
                173, // The keycode of the key
                "category.atlasengine.animation" // The translation key of the keybinding's category.
        ));

        Keybinds.open = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.atlasengine.open", // The translation key of the keybinding's name
                InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
                GLFW.GLFW_KEY_O, // The keycode of the key
                "category.atlasengine.animation" // The translation key of the keybinding's category.
        ));

        Keybinds.createRegion = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.atlasengine.createregion", // The translation key of the keybinding's name
                InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
                GLFW.GLFW_KEY_R, // The keycode of the key
                "category.atlasengine.region" // The translation key of the keybinding's category.
        ));
    }
}
