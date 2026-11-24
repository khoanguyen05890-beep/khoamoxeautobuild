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

    private static boolean wasDown = false;

    /** Chi nhan ALT BEN PHAI. */
    public static boolean isRightAltDown() {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc == null || mc.getWindow() == null) return false;
        return GLFW.glfwGetKey(mc.getWindow().getHandle(), GLFW.GLFW_KEY_RIGHT_ALT) == GLFW.GLFW_PRESS;
    }

    /** Goi moi tick va moi khung hinh de khong bo lo cu click ngan. */
    public static void pollMenuHotkey() {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc == null || mc.player == null || mc.getWindow() == null) {
            wasDown = false;
            return;
        }
        long window = mc.getWindow().getHandle();
        boolean down = isRightAltDown()
                && GLFW.glfwGetMouseButton(window, GLFW.GLFW_MOUSE_BUTTON_RIGHT) == GLFW.GLFW_PRESS;
        if (down && !wasDown && mc.currentScreen == null) {
            LOGGER.info("Mo menu (Right ALT + Right Click)");
            mc.setScreen(new AutoBuildGUI());
        }
        wasDown = down;
    }

    @Override
    public void onInitializeClient() {
        ConfigManager.load();
        HUDRenderer.register();

        // Giu ALT phai: chuot phai khong dat block / dung vat pham.
        UseBlockCallback.EVENT.register((player, world, hand, hit) ->
                (world.isClient() && isRightAltDown()) ? ActionResult.FAIL : ActionResult.PASS);
        UseItemCallback.EVENT.register((player, world, hand) ->
                (world.isClient() && isRightAltDown()) ? ActionResult.FAIL : ActionResult.PASS);

        ClientTickEvents.END_CLIENT_TICK.register(client -> pollMenuHotkey());

        LOGGER.info("KHOAMOXE AUTO BUILDER da khoi dong (Giai doan 1).");
    }
}
