package dev.pojav.client.mixin;

import dev.pojav.client.feature.ClickTracker;
import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.lwjgl.glfw.GLFW;

@Mixin(Mouse.class)
public abstract class MouseMixin {
    @Inject(method = "onMouseButton", at = @At("HEAD"))
    private void pojav$trackClick(long window, int button, int action, int modifiers, CallbackInfo ci) {
        if (action == GLFW.GLFW_PRESS) ClickTracker.register(button);
    }
}
