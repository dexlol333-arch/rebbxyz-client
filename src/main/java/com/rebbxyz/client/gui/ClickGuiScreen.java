package com.rebbxyz.client.gui;

import com.rebbxyz.client.RebbxyzClient;
import com.rebbxyz.client.module.Module;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public final class ClickGuiScreen extends Screen {
    private final int panelW = 300;
    private final int panelH = 230;

    public ClickGuiScreen() {
        super(Text.literal("rebbxyz client"));
    }

    @Override
    protected void init() {
        // Intentionally custom-rendered for a compact client-style UI.
    }

    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        int x = (width - panelW) / 2;
        int y = (height - panelH) / 2;

        ctx.fill(0, 0, width, height, 0x66000000);
        ctx.fill(x, y, x + panelW, y + panelH, 0xE8121218);
        ctx.fill(x, y, x + 3, y + panelH, 0xFF8B5CF6);
        ctx.fill(x + 3, y, x + panelW, y + 32, 0xFF181820);

        ctx.drawTextWithShadow(textRenderer, Text.literal("rebbxyz client"), x + 16, y + 10, 0xFFFFFFFF);
        ctx.drawTextWithShadow(textRenderer, Text.literal("MODULES"), x + 16, y + 43, 0xFF8B5CF6);

        int rowY = y + 62;
        for (Module module : RebbxyzClient.MODULES.all()) {
            boolean hovered = mouseX >= x + 12 && mouseX <= x + panelW - 12
                    && mouseY >= rowY && mouseY <= rowY + 38;

            ctx.fill(x + 12, rowY, x + panelW - 12, rowY + 38,
                    hovered ? 0xFF252531 : 0xFF1D1D26);

            int accent = module.enabled() ? 0xFF8B5CF6 : 0xFF555563;
            ctx.fill(x + 12, rowY, x + 15, rowY + 38, accent);

            ctx.drawTextWithShadow(textRenderer, Text.literal(module.name()),
                    x + 24, rowY + 8, module.enabled() ? 0xFFFFFFFF : 0xFFB6B6C0);

            ctx.drawTextWithShadow(textRenderer,
                    Text.literal(module.enabled() ? "ON" : "OFF"),
                    x + panelW - 58, rowY + 8,
                    module.enabled() ? 0xFFBDA7FF : 0xFF777783);

            ctx.drawTextWithShadow(textRenderer, Text.literal("Right-click: settings"),
                    x + 24, rowY + 22, 0xFF777783);

            rowY += 48;
        }

        ctx.drawTextWithShadow(textRenderer,
                Text.literal("RShift  •  Toggle GUI"),
                x + 16, y + panelH - 20, 0xFF777783);

        super.render(ctx, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int x = (width - panelW) / 2;
        int y = (height - panelH) / 2;
        int rowY = y + 62;

        for (Module module : RebbxyzClient.MODULES.all()) {
            if (mouseX >= x + 12 && mouseX <= x + panelW - 12
                    && mouseY >= rowY && mouseY <= rowY + 38) {
                if (button == 0) {
                    module.toggle();
                    return true;
                }
                if (button == 1) {
                    module.setSettingsOpen(true);
                    client.setScreen(new ModuleSettingsScreen(module, this));
                    return true;
                }
            }
            rowY += 48;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
