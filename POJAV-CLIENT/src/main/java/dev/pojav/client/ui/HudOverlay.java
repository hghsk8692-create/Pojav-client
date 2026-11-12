package dev.pojav.client.ui;

import dev.pojav.client.feature.Features;
import dev.pojav.client.feature.ClickTracker;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public final class HudOverlay {
    private static final int PURPLE = 0xFFB69AFF;
    private static final int PANEL = 0xB9101120;

    private HudOverlay() {}

    public static void register() {
        HudElementRegistry.addLast(Identifier.of("pojav", "client_hud"), (context, tickCounter) -> render(context));
    }

    private static void render(DrawContext context) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.world == null || client.options.hudHidden || Features.enabledHudCount() == 0) return;

        int x = 8;
        int y = 8;
        List<String> lines = new ArrayList<>();
        lines.add("POJAV CLIENT");
        if (Features.isEnabled("FPS Counter")) lines.add("FPS  " + client.getCurrentFps());
        if (Features.isEnabled("CPS Counter")) lines.add("CPS  " + ClickTracker.cps(0) + " / " + ClickTracker.cps(1));
        if (Features.isEnabled("Ping Display")) {
            var entry = client.getNetworkHandler() == null ? null : client.getNetworkHandler().getPlayerListEntry(client.player.getUuid());
            lines.add("PING  " + (entry == null ? "--" : entry.getLatency() + " ms"));
        }
        if (Features.isEnabled("Coordinates")) {
            lines.add(String.format(java.util.Locale.ROOT, "XYZ  %.1f / %.1f / %.1f", client.player.getX(), client.player.getY(), client.player.getZ()));
        }

        int maxWidth = 0;
        for (String line : lines) maxWidth = Math.max(maxWidth, client.textRenderer.getWidth(line));
        int panelWidth = maxWidth + 18;
        int panelHeight = lines.size() * 12 + 10;
        context.fill(x, y, x + panelWidth, y + panelHeight, PANEL);
        context.fill(x, y, x + 2, y + panelHeight, PURPLE);
        for (int i = 0; i < lines.size(); i++) {
            context.drawTextWithShadow(client.textRenderer, lines.get(i), x + 8, y + 5 + i * 12,
                    i == 0 ? PURPLE : 0xFFE8E8F2);
        }

        int right = context.getScaledWindowWidth() - 8;
        int bottom = context.getScaledWindowHeight() - 8;
        if (Features.isEnabled("Keystrokes")) drawKeystrokes(context, right - 66, bottom - 58);
        if (Features.isEnabled("Armor HUD")) drawArmor(context, right - 104, bottom - 18);
        if (Features.isEnabled("Totem Counter")) drawTotems(context, right - 104, bottom - 78);
        if (Features.isEnabled("Potion Effects")) drawEffects(context, right - 8, 8);
    }

    private static void drawKeystrokes(DrawContext context, int x, int y) {
        MinecraftClient client = MinecraftClient.getInstance();
        drawKey(context, x + 22, y, "W", client.options.forwardKey.isPressed());
        drawKey(context, x, y + 22, "A", client.options.leftKey.isPressed());
        drawKey(context, x + 22, y + 22, "S", client.options.backKey.isPressed());
        drawKey(context, x + 44, y + 22, "D", client.options.rightKey.isPressed());
        drawKey(context, x + 22, y + 44, "J", client.options.jumpKey.isPressed());
    }

    private static void drawKey(DrawContext context, int x, int y, String label, boolean pressed) {
        int color = pressed ? 0xFF6847B8 : 0xD91A1B2B;
        context.fill(x, y, x + 20, y + 19, color);
        context.drawCenteredTextWithShadow(MinecraftClient.getInstance().textRenderer, label, x + 10, y + 6, 0xFFFFFFFF);
    }

    private static void drawArmor(DrawContext context, int x, int y) {
        MinecraftClient client = MinecraftClient.getInstance();
        int slot = 0;
        for (ItemStack stack : client.player.getArmorItems()) {
            if (!stack.isEmpty()) {
                int drawX = x + slot * 24;
                context.drawItem(stack, drawX, y - 18);
                if (stack.isDamageable()) {
                    int remaining = stack.getMaxDamage() - stack.getDamage();
                    int percent = Math.round(remaining * 100.0f / stack.getMaxDamage());
                    context.drawTextWithShadow(client.textRenderer, percent + "%", drawX - 1, y - 29,
                            percent <= 20 ? 0xFFFF5555 : 0xFFFFFFFF);
                }
            }
            slot++;
        }
    }

    private static void drawTotems(DrawContext context, int x, int y) {
        MinecraftClient client = MinecraftClient.getInstance();
        int count = 0;
        for (int i = 0; i < client.player.getInventory().size(); i++) {
            if (client.player.getInventory().getStack(i).isOf(Items.TOTEM_OF_UNDYING)) count += client.player.getInventory().getStack(i).getCount();
        }
        context.fill(x, y, x + 86, y + 24, PANEL);
        context.drawItem(new ItemStack(Items.TOTEM_OF_UNDYING), x + 4, y + 4);
        context.drawTextWithShadow(client.textRenderer, "x" + count, x + 27, y + 8, 0xFFFFFFFF);
    }

    private static void drawEffects(DrawContext context, int right, int y) {
        MinecraftClient client = MinecraftClient.getInstance();
        int row = 0;
        for (StatusEffectInstance effect : client.player.getStatusEffects()) {
            String name = effect.getEffectType().value().getName().getString();
            int seconds = effect.getDuration() / 20;
            String line = name + " " + seconds + "s";
            int width = client.textRenderer.getWidth(line) + 12;
            context.fill(right - width, y + row * 14, right, y + row * 14 + 12, PANEL);
            context.drawTextWithShadow(client.textRenderer, line, right - width + 6, y + row * 14 + 2, 0xFFE8E8F2);
            row++;
        }
    }
}
