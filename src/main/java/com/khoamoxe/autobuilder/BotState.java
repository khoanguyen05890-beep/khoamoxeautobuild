package com.khoamoxe.autobuilder;

/** Trang thai chung cua bot, dung cho GUI va HUD. Cac giai doan sau se cap nhat cac so lieu nay. */
public final class BotState {
    public enum Status { IDLE, BUILDING, PAUSED, STOPPED }

    public static volatile Status status = Status.IDLE;
    public static volatile int currentLayer = 0;
    public static volatile int totalLayers = 0;
    public static volatile int blocksPlaced = 0;
    public static volatile int blocksRemaining = 0;
    public static volatile int missingItems = 0;
    public static volatile String currentTask = "Idle";

    private BotState() {}

    public static int percent() {
        int total = blocksPlaced + blocksRemaining;
        return total <= 0 ? 0 : (int) (100L * blocksPlaced / total);
    }

    public static void start() {
        status = Status.BUILDING;
        currentTask = "Starting";
    }

    public static void pause() {
        if (status == Status.BUILDING) {
            status = Status.PAUSED;
            currentTask = "Paused";
        }
    }

    public static void resume() {
        if (status == Status.PAUSED) {
            status = Status.BUILDING;
            currentTask = "Resuming";
        }
    }

    public static void stop() {
        status = Status.STOPPED;
        currentTask = "Stopped";
    }
}
