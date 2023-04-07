package ca.atlasengine.mixin;

import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ca.atlasengine.models.ModelManager;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Shadow public abstract float getHeadYaw();

    @Shadow public abstract float getPitch();

    float lastYaw = 0;
    float lastPitch = 0;

    @Inject(at = @At("RETURN"), method = "changeLookDirection")
    public void changeLookDirection(double cursorDeltaX, double cursorDeltaY, CallbackInfo ci) {
        if (ModelManager.isPlacing()) {
            boolean needsUpdate = false;

            if (lastYaw != this.getHeadYaw()) {
                lastYaw = this.getHeadYaw();
                needsUpdate = true;
            }

            if (lastPitch != this.getPitch()) {
                lastPitch = this.getPitch();
                needsUpdate = true;
            }

            if (needsUpdate) {
                ModelManager.changeLookDirection(this.getHeadYaw(), this.getPitch());
            }
        }
    }
}
