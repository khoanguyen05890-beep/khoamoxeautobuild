package com.khoamoxe.autobuilder;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ActionResult;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main implements ClientModInitializer {
    public static final String MOD_ID = "khoamoxe_autobuilder";
    public static final Logger LOGGER = LoggerFactory.getLogger("KHOAMOXE AUTO BUILDER");

    private boolean wasAltRightDown = false;

    @Override
    public void onInitializeClient() {
        ConfigManager.load();
        HUDRenderer.register();

        // Khi giu ALT, chuot phai khong duoc dat block / dung vat pham.
        UseBlockCallback.EVENT.register((player, world, hand, hit) ->
                (world.isClient() && Screen.hasAltDown()) ? ActionResult.FAIL : ActionResult.PASS);
        UseItemCallback.EVENT.register((player, world, hand) ->
                (world.isClient() && Screen.hasAltDown()) ? ActionResult.FAIL : ActionResult.PASS);

        // ALT + chuot phai -> mo menu (nhan dien canh len, tranh mo lap lai).
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;
            long window = client.getWindow().getHandle();
            boolean down = Screen.hasAltDown()
                    && GLFW.glfwGetMouseButton(window, GLFW.GLFW_MOUSE_BUTTON_RIGHT) == GLFW.GLFW_PRESS;
            if (down && !wasAltRightDown && client.currentScreen == null) {
                client.setScreen(new AutoBuildGUI());
            }
            wasAltRightDown = down;
        });

        LOGGER.info("KHOAMOXE AUTO BUILDER da khoi dong (Giai doan 1).");
    }
}
