package com.rebbxyz.client.gui;

import com.rebbxyz.client.module.*;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public final class ModuleSettingsScreen extends Screen {
    private final Module module;
    private final Screen parent;

    public ModuleSettingsScreen(Module module, Screen parent) {
        super(Text.literal(module.name() + " settings"));
        this.module = module;
        this.parent = parent;
    }

    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        int w = 390, h = 300;
        int x = (width - w) / 2, y = (height - h) / 2;

        ctx.fill(0, 0, width, height, 0x77000000);
        ctx.fill(x, y, x + w, y + h, 0xEC111117);
        ctx.fill(x, y, x + w, y + 34, 0xFF191920);
        ctx.fill(x, y, x + 3, y + h, 0xFF8B5CF6);

        ctx.drawTextWithShadow(textRenderer, Text.literal(module.name() + "  /  settings"),
                x + 16, y + 11, 0xFFFFFFFF);

        int line = y + 52;

        if (module instanceof StorageFinder storage) {
            ctx.drawTextWithShadow(textRenderer, Text.literal("Left click a category to toggle tracer."),
                    x + 18, line, 0xFF888895);
            line += 26;

            for (StorageType type : StorageType.values()) {
                ColorSetting c = storage.color(type);
                ctx.drawTextWithShadow(textRenderer, Text.literal(type.display()),
                        x + 20, line + 5, 0xFFEAEAF0);
                ctx.drawTextWithShadow(textRenderer,
                        Text.literal(storage.tracer(type) ? "TRACER ON" : "TRACER OFF"),
                        x + 210, line + 5, storage.tracer(type) ? 0xFFBDA7FF : 0xFF777783);
                ctx.fill(x + 315, line, x + 350, line + 18, c.argb());
                ctx.drawTextWithShadow(textRenderer,
                        Text.literal("A " + Math.round(c.alpha() * 100) + "%"),
                        x + 20, line + 20, 0xFF777783);
                line += 54;
            }

            ctx.drawTextWithShadow(textRenderer,
                    Text.literal("Range: " + storage.range() + " blocks   |   Line width: " + storage.lineWidth()),
                    x + 20, y + h - 42, 0xFFAAAAAF);
        } else if (module instanceof SpawnerFinder spawner) {
            ColorSetting c = spawner.color();
            ctx.drawTextWithShadow(textRenderer, Text.literal("Spawner color / opacity"),
                    x + 20, line, 0xFFEAEAF0);
            ctx.fill(x + 20, line + 22, x + 90, line + 44, c.argb());
            ctx.drawTextWithShadow(textRenderer,
                    Text.literal(spawner.tracer() ? "TRACER ON" : "TRACER OFF"),
                    x + 120, line + 27, spawner.tracer() ? 0xFFBDA7FF : 0xFF777783);
            ctx.drawTextWithShadow(textRenderer,
                    Text.literal("Range: " + spawner.range() + " blocks"),
                    x + 20, y + h - 55, 0xFFAAAAAF);
            ctx.drawTextWithShadow(textRenderer,
                    Text.literal("Left click the tracer label to toggle it."),
                    x + 20, y + h - 34, 0xFF777783);
        }

        ctx.drawTextWithShadow(textRenderer, Text.literal("ESC  •  Back"),
                x + w - 82, y + h - 20, 0xFF777783);
        super.render(ctx, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0 && module instanceof StorageFinder storage) {
            int w = 390, h = 300;
            int x = (width - w) / 2, y = (height - h) / 2;
            int line = y + 78;

            for (StorageType type : StorageType.values()) {
                if (mouseX >= x + 190 && mouseX <= x + 370
                        && mouseY >= line && mouseY <= line + 48) {
                    storage.toggleTracer(type);
                    return true;
                }
                line += 54;
            }
        }

        if (button == 0 && module instanceof SpawnerFinder spawner) {
            int w = 390, h = 300;
            int x = (width - w) / 2, y = (height - h) / 2;
            if (mouseX >= x + 110 && mouseX <= x + 300
                    && mouseY >= y + 74 && mouseY <= y + 125) {
                spawner.toggleTracer();
                return true;
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void close() {
        client.setScreen(parent);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
