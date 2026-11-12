package dev.pojav.client.mixin;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin {
    @Inject(method = "render", at = @At("TAIL"))
    private void pojav$drawBrand(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        int center = context.getScaledWindowWidth() / 2;
        context.fill(center - 132, 18, center + 132, 67, 0xC90C0D1C);
        context.fill(center - 132, 18, center + 132, 20, 0xFF9B72FF);
        context.drawCenteredTextWithShadow(net.minecraft.client.MinecraftClient.getInstance().textRenderer,
                Text.literal("POJAV CLIENT"), center, 30, 0xFFFFFFFF);
        context.drawCenteredTextWithShadow(net.minecraft.client.MinecraftClient.getInstance().textRenderer,
                Text.literal("CPVP EDITION  •  FABRIC"), center, 48, 0xFFBCA8FF);
    }
}
