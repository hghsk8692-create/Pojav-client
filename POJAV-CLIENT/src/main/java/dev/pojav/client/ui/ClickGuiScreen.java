package dev.pojav.client.ui;

import dev.pojav.client.feature.Features;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class ClickGuiScreen extends Screen {
    private static final int PURPLE = 0xFF9B72FF;
    private static final int PANEL = 0xF0181928;
    private static final int CARD = 0xFF24263A;
    private static final int ACTIVE = 0xFF6547B8;
    private final List<ButtonWidget> moduleButtons = new ArrayList<>();

    public ClickGuiScreen() {
        super(Text.literal("POJAV CLIENT"));
    }

    @Override
    protected void init() {
        moduleButtons.clear();
        int panelWidth = Math.min(560, this.width - 32);
        int left = (this.width - panelWidth) / 2;
        int top = Math.max(34, (this.height - 390) / 2);
        int colWidth = (panelWidth - 48) / 2;
        int row = 0;
        int col = 0;

        for (Map.Entry<String, Boolean> feature : Features.all().entrySet()) {
            final String name = feature.getKey();
            int x = left + 18 + col * (colWidth + 12);
            int y = top + 92 + row * 34;
            ButtonWidget button = ButtonWidget.builder(moduleLabel(name), b -> {
                Features.toggle(name);
                b.setMessage(moduleLabel(name));
            }).dimensions(x, y, colWidth, 26).build();
            moduleButtons.add(button);
            addDrawableChild(button);

            col++;
            if (col > 1) {
                col = 0;
                row++;
            }
        }

        addDrawableChild(ButtonWidget.builder(Text.literal("CLOSE  [ESC]"), b -> close())
                .dimensions(left + 18, top + 92 + 6 * 34 + 10, panelWidth - 36, 28).build());
    }

    private Text moduleLabel(String name) {
        return Text.literal((Features.isEnabled(name) ? "ON  " : "OFF  ") + name);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        int panelWidth = Math.min(560, this.width - 32);
        int left = (this.width - panelWidth) / 2;
        int top = Math.max(34, (this.height - 390) / 2);

        context.fill(0, 0, width, height, 0xB9080A16);
        context.fill(left, top, left + panelWidth, top + 356, PANEL);
        context.fill(left, top, left + panelWidth, top + 3, PURPLE);
        context.fill(left + 14, top + 14, left + 58, top + 58, ACTIVE);
        context.drawCenteredTextWithShadow(textRenderer, "P", left + 36, top + 27, 0xFFFFFFFF);
        context.drawTextWithShadow(textRenderer, "POJAV CLIENT", left + 70, top + 17, 0xFFFFFFFF);
        context.drawTextWithShadow(textRenderer, "CPVP UTILITY  /  FABRIC 1.21.11", left + 70, top + 34, 0xFFB8A9E8);
        context.fill(left + 16, top + 72, left + panelWidth - 16, top + 73, 0xFF41435B);
        context.drawTextWithShadow(textRenderer, "MODULES", left + 18, top + 80, 0xFFB9A7F6);
        super.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(textRenderer, "RIGHT SHIFT  •  TOGGLE MENU", width / 2, top + 342, 0xFF8F91A8);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
