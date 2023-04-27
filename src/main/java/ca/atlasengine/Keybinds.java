package ca.atlasengine;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.option.KeyBinding;
import ca.atlasengine.models.ModelManager;

public class Keybinds {
    public static KeyBinding add;
    public static KeyBinding remove;

    public static KeyBinding next;
    public static KeyBinding previous;
    public static KeyBinding destroy;
    public static KeyBinding open;

    public static KeyBinding createRegion;
    public static KeyBinding createBlocks;
    public static KeyBinding createCutscene;
    public static KeyBinding toggle;

    static {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (createBlocks.wasPressed()) {
                KeybindsManager.sendCreateBlocks();
            }

            while (createRegion.wasPressed()) {
                KeybindsManager.sendCreateRegion();
            }

            while (createCutscene.wasPressed()) {
                KeybindsManager.sendCreateCutscene();
            }

            while (add.wasPressed()) {
                KeybindsManager.sendAdd();
            }

            while (toggle.wasPressed()) {
                KeybindsManager.sendToggle();
            }

            while (next.wasPressed()) {
                if (ModelManager.isPlacing()) {
                    ModelManager.updateRotation(1);
                } else {
                    KeybindsManager.sendFrameForwards();
                }
            }

            while (previous.wasPressed()) {
                if (ModelManager.isPlacing()) {
                    ModelManager.updateRotation(-1);
                } else {
                    KeybindsManager.sendFrameBackwards();
                }
            }

            while (destroy.wasPressed()) {
                KeybindsManager.sendDestroy();
            }

            while (remove.wasPressed()) {
                KeybindsManager.sendRemove();
            }

            while (open.wasPressed()) {
                KeybindsManager.sendOpen();
            }
        });
    }
}
