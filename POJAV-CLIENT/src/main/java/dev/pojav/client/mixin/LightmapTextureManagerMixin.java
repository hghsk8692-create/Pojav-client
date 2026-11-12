package dev.pojav.client.mixin;

import dev.pojav.client.feature.Features;
import net.minecraft.client.render.LightmapTextureManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LightmapTextureManager.class)
public abstract class LightmapTextureManagerMixin {
    @Inject(method = "getBrightness(FI)F", at = @At("RETURN"), cancellable = true)
    private static void pojav$fullbright(float ambientLight, int lightLevel, CallbackInfoReturnable<Float> cir) {
        if (Features.isEnabled("Fullbright")) cir.setReturnValue(1.0F);
    }
}
