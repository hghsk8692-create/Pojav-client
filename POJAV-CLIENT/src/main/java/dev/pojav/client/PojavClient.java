package dev.pojav.client;

import dev.pojav.client.feature.Features;
import dev.pojav.client.ui.ClickGuiScreen;
import dev.pojav.client.ui.HudOverlay;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public final class PojavClient implements ClientModInitializer {
    public static final String MOD_ID = "pojav";
    private static KeyBinding openGuiKey;
    private static KeyBinding zoomKey;

    @Override
    public void onInitializeClient() {
        Features.init();
        HudOverlay.register();
        openGuiKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.pojav.open_gui", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_SHIFT, KeyBinding.Category.MISC));
        zoomKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.pojav.zoom", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_C, KeyBinding.Category.MISC));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openGuiKey.wasPressed()) {
                if (client.currentScreen == null) client.setScreen(new ClickGuiScreen());
            }
            Features.tick(client, Features.isEnabled("Zoom") && zoomKey.isPressed());
        });
    }

    public static MinecraftClient client() {
        return MinecraftClient.getInstance();
    }
}
