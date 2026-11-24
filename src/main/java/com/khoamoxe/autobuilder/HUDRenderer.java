package com.khoamoxe.autobuilder;

import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.Identifier;

/** HUD thong ke ben goc tren-trai man hinh. */
public final class HUDRenderer {
    private HUDRenderer() {}

    public static void register() {
        HudElementRegistry.attachElementAfter(
                VanillaHudElements.MISC_OVERLAYS,
                Identifier.of("khoamoxe_autobuilder", "hud"),
                HUDRenderer::render);
    }

    private static void render(DrawContext ctx, RenderTickCounter tick) {
        if (!ConfigManager.isHudEnabled()) return;
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null || mc.options.hudHidden) return;

        TextRenderer font = mc.textRenderer;
        String pos = String.format("%d %d %d",
                (int) Math.floor(mc.player.getX()),
                (int) Math.floor(mc.player.getY()),
                (int) Math.floor(mc.player.getZ()));
        String[] lines = {
                "KHOAMOXE AUTO BUILDER",
                "Status: " + BotState.status,
                "Current Layer: " + BotState.currentLayer + "/" + BotState.totalLayers,
                "Progress: " + BotState.percent() + "%",
                "Blocks Placed: " + BotState.blocksPlaced,
                "Blocks Remaining: " + BotState.blocksRemaining,
                "Missing Items: " + BotState.missingItems,
                "Current Task: " + BotState.currentTask,
                "Bot Position: " + pos
        };

        int x = 4, y = 4, maxW = 0;
        for (String l : lines) maxW = Math.max(maxW, font.getWidth(l));
        ctx.fill(x - 2, y - 2, x + maxW + 2, y + lines.length * 10 + 1, 0x90000000);
        for (int i = 0; i < lines.length; i++) {
            int color = (i == 0) ? 0xFFFFAA00 : 0xFFFFFFFF;
            ctx.drawTextWithShadow(font, lines[i], x, y + i * 10, color);
        }
    }
}
