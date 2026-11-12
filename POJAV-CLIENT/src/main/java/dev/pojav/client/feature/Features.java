package dev.pojav.client.feature;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.item.ItemStack;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

public final class Features {
    private static final Map<String, Boolean> ENABLED = new LinkedHashMap<>();
    private static int previousFov = 70;
    private static boolean fovCaptured;

    private Features() {}

    public static void init() {
        ENABLED.put("Fullbright", false);
        ENABLED.put("Zoom", false);
        ENABLED.put("Toggle Sprint", false);
        ENABLED.put("CPS Counter", true);
        ENABLED.put("Ping Display", true);
        ENABLED.put("FPS Counter", true);
        ENABLED.put("Coordinates", true);
        ENABLED.put("Keystrokes", true);
        ENABLED.put("Armor HUD", true);
        ENABLED.put("Totem Counter", true);
        ENABLED.put("Potion Effects", true);
        load();
    }

    public static boolean isEnabled(String name) {
        return ENABLED.getOrDefault(name, false);
    }

    public static void toggle(String name) {
        ENABLED.computeIfPresent(name, (key, value) -> !value);
        save();
        applyImmediate(MinecraftClient.getInstance());
    }

    public static Map<String, Boolean> all() {
        return java.util.Collections.unmodifiableMap(new LinkedHashMap<>(ENABLED));
    }

    public static void tick(MinecraftClient client, boolean zoomHeld) {
        if (client.options == null) return;

        if (client.player != null && isEnabled("Toggle Sprint") && !client.player.isSneaking()) {
            client.player.setSprinting(true);
        }

        boolean zoom = zoomHeld && client.player != null && client.currentScreen == null;
        if (zoom) {
            SimpleOption<Integer> fov = client.options.getFov();
            if (!fovCaptured) {
                previousFov = fov.getValue();
                fovCaptured = true;
            }
            fov.setValue(Math.min(previousFov, 30));
        } else if (fovCaptured) {
            client.options.getFov().setValue(previousFov);
            fovCaptured = false;
        }
    }

    private static void applyImmediate(MinecraftClient client) {
        // Fullbright is applied in LightmapTextureManagerMixin so the vanilla gamma slider is untouched.
    }

    private static Path configPath() {
        return MinecraftClient.getInstance().runDirectory.toPath().resolve("config").resolve("pojav-client.properties");
    }

    private static void load() {
        Path path = configPath();
        if (!Files.exists(path)) return;
        Properties properties = new Properties();
        try (var reader = Files.newBufferedReader(path)) {
            properties.load(reader);
            ENABLED.replaceAll((name, fallback) -> Boolean.parseBoolean(properties.getProperty(name, Boolean.toString(fallback))));
        } catch (IOException ignored) {
            // Keep defaults if the config cannot be read.
        }
    }

    private static void save() {
        Path path = configPath();
        Properties properties = new Properties();
        ENABLED.forEach((name, enabled) -> properties.setProperty(name, Boolean.toString(enabled)));
        try {
            Files.createDirectories(path.getParent());
            try (var writer = Files.newBufferedWriter(path)) {
                properties.store(writer, "POJAV CLIENT module settings");
            }
        } catch (IOException ignored) {
            // Config saving is best-effort; the client remains usable without it.
        }
    }

    public static int enabledHudCount() {
        int count = 0;
        for (String name : ENABLED.keySet()) {
            if (!name.equals("Fullbright") && !name.equals("Zoom") && !name.equals("Toggle Sprint") && isEnabled(name)) count++;
        }
        return count;
    }
}
