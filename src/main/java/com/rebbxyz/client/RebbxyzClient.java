package com.rebbxyz.client;

import com.mojang.blaze3d.platform.InputConstants;
import com.rebbxyz.client.gui.ClickGuiScreen;
import com.rebbxyz.client.module.ModuleManager;
import com.rebbxyz.client.render.WorldHighlighter;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

public final class RebbxyzClient implements ClientModInitializer {
    public static final String MOD_ID = "rebbxyz";
    public static final String NAME = "rebbxyz client";

    public static ModuleManager MODULES;
    private static KeyMapping clickGuiKey;

    @Override
    public void onInitializeClient() {
        MODULES = new ModuleManager();

        clickGuiKey = KeyBindingHelper.registerKeyBinding(
                new KeyMapping(
                        "key.rebbxyz.clickgui",
                        InputUtil.Type.KEYSYM,
                        GLFW.GLFW_KEY_RIGHT_SHIFT,
                        KeyMapping.Category.MISC
                )
        );

        WorldHighlighter.register();

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (clickGuiKey.consumeClick()) {
                if (client.currentScreen == null) {
                    client.setScreen(new ClickGuiScreen());
                }
            }
        });
    }

    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }
}
