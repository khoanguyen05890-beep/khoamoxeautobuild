package com.khoamoxe.autobuilder;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

/** Menu chinh, mo bang ALT + chuot phai. */
public class AutoBuildGUI extends Screen {
    private static final int BTN_W = 150;
    private static final int BTN_H = 20;
    private static final int GAP = 4;

    public AutoBuildGUI() {
        super(Text.literal("KHOAMOXE AUTO BUILDER"));
    }

    @Override
    protected void init() {
        int left = this.width / 2 - BTN_W - GAP / 2;
        int right = this.width / 2 + GAP / 2;
        int top = 40;

        Feature[] features = Feature.values();
        for (int i = 0; i < features.length; i++) {
            Feature f = features[i];
            int x = (i % 2 == 0) ? left : right;
            int y = top + (i / 2) * (BTN_H + GAP);
            ButtonWidget btn = ButtonWidget.builder(toggleText(f), b -> {
                ConfigManager.setEnabled(f, !ConfigManager.isEnabled(f));
                b.setMessage(toggleText(f));
            }).dimensions(x, y, BTN_W, BTN_H).build();
            this.addDrawableChild(btn);
        }

        int rowY = top + 5 * (BTN_H + GAP) + 8;
        int w4 = (BTN_W * 2 + GAP - 3 * GAP) / 4;
        int x0 = left;
        this.addDrawableChild(ButtonWidget.builder(Text.literal("START"), b -> BotState.start())
                .dimensions(x0, rowY, w4, BTN_H).build());
        this.addDrawableChild(ButtonWidget.builder(Text.literal("PAUSE"), b -> BotState.pause())
                .dimensions(x0 + (w4 + GAP), rowY, w4, BTN_H).build());
        this.addDrawableChild(ButtonWidget.builder(Text.literal("RESUME"), b -> BotState.resume())
                .dimensions(x0 + 2 * (w4 + GAP), rowY, w4, BTN_H).build());
        this.addDrawableChild(ButtonWidget.builder(Text.literal("STOP"), b -> BotState.stop())
                .dimensions(x0 + 3 * (w4 + GAP), rowY, w4, BTN_H).build());

        int row2 = rowY + BTN_H + GAP;
        this.addDrawableChild(ButtonWidget.builder(hudText(), b -> {
            ConfigManager.setHudEnabled(!ConfigManager.isHudEnabled());
            b.setMessage(hudText());
        }).dimensions(left, row2, BTN_W, BTN_H).build());
        this.addDrawableChild(ButtonWidget.builder(Text.literal("CLOSE"), b -> this.close())
                .dimensions(right, row2, BTN_W, BTN_H).build());
    }

    private static Text toggleText(Feature f) {
        return Text.literal(f.label + ": " + (ConfigManager.isEnabled(f) ? "ON" : "OFF"));
    }

    private static Text hudText() {
        return Text.literal("HUD: " + (ConfigManager.isHudEnabled() ? "ON" : "OFF"));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 15, 0xFFFFFF);

        int y = 40 + 5 * (BTN_H + GAP) + 8 + 2 * (BTN_H + GAP) + 6;
        int cx = this.width / 2;
        String[] lines = {
                "Status: " + BotState.status + "  |  Task: " + BotState.currentTask,
                "Progress: " + BotState.percent() + "%  |  Layer: " + BotState.currentLayer + "/" + BotState.totalLayers,
                "Placed: " + BotState.blocksPlaced + "  |  Remaining: " + BotState.blocksRemaining
                        + "  |  Missing items: " + BotState.missingItems
        };
        for (String line : lines) {
            context.drawCenteredTextWithShadow(this.textRenderer, Text.literal(line), cx, y, 0xAAAAAA);
            y += 11;
        }
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
