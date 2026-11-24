package com.khoamoxe.autobuilder;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.ActionResult;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main implements ClientModInitializer {
    public static final String MOD_ID = "khoamoxe_autobuilder";
    public static final Logger LOGGER = LoggerFactory.getLogger("KHOAMOXE AUTO BUILDER");

    private boolean wasAltRightDown = false;

    /** Kiem tra ALT trai/phai truc tiep bang GLFW (Screen.hasAltDown() da bi go bo). */
    public static boolean isAltDown() {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc == null || mc.getWindow() == null) return false;
        long window = mc.getWindow().getHandle();
        return GLFW.glfwGetKey(window, GLFW.GLFW_KEY_LEFT_ALT) == GLFW.GLFW_PRESS
                || GLFW.glfwGetKey(window, GLFW.GLFW_KEY_RIGHT_ALT) == GLFW.GLFW_PRESS;
    }

    @Override
    public void onInitializeClient() {
        ConfigManager.load();
        HUDRenderer.register();

        // Khi giu ALT, chuot phai khong duoc dat block / dung vat pham.
        UseBlockCallback.EVENT.register((player, world, hand, hit) ->
                (world.isClient() && isAltDown()) ? ActionResult.FAIL : ActionResult.PASS);
        UseItemCallback.EVENT.register((player, world, hand) ->
                (world.isClient() && isAltDown()) ? ActionResult.FAIL : ActionResult.PASS);

        // ALT + chuot phai -> mo menu (nhan dien canh len, tranh mo lap lai).
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;
            long window = client.getWindow().getHandle();
            boolean down = isAltDown()
                    && GLFW.glfwGetMouseButton(window, GLFW.GLFW_MOUSE_BUTTON_RIGHT) == GLFW.GLFW_PRESS;
            if (down && !wasAltRightDown && client.currentScreen == null) {
                client.setScreen(new AutoBuildGUI());
            }
            wasAltRightDown = down;
        });

        LOGGER.info("KHOAMOXE AUTO BUILDER da khoi dong (Giai doan 1).");
    }
}
