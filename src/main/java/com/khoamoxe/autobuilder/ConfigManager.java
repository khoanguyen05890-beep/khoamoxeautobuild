package com.khoamoxe.autobuilder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

/** Doc/ghi file cau hinh tai .minecraft/config/khoamoxe_autobuilder.json */
public final class ConfigManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path FILE =
            FabricLoader.getInstance().getConfigDir().resolve("khoamoxe_autobuilder.json");

    private static Data data = new Data();

    public static final class Data {
        public boolean hudEnabled = true;
        public Map<String, Boolean> features = new LinkedHashMap<>();
    }

    private ConfigManager() {}

    public static void load() {
        if (Files.exists(FILE)) {
            try (Reader r = Files.newBufferedReader(FILE)) {
                Data loaded = GSON.fromJson(r, Data.class);
                if (loaded != null) data = loaded;
            } catch (Exception e) {
                Main.LOGGER.error("Khong doc duoc config, dung mac dinh", e);
            }
        }
        if (data.features == null) data.features = new LinkedHashMap<>();
        for (Feature f : Feature.values()) {
            data.features.putIfAbsent(f.name(), f != Feature.SETTINGS);
        }
        save();
    }

    public static void save() {
        try (Writer w = Files.newBufferedWriter(FILE)) {
            GSON.toJson(data, w);
        } catch (IOException e) {
            Main.LOGGER.error("Khong ghi duoc config", e);
        }
    }

    public static boolean isEnabled(Feature f) {
        return data.features.getOrDefault(f.name(), false);
    }

    public static void setEnabled(Feature f, boolean on) {
        data.features.put(f.name(), on);
        save();
    }

    public static boolean isHudEnabled() {
        return data.hudEnabled;
    }

    public static void setHudEnabled(boolean on) {
        data.hudEnabled = on;
        save();
    }
}
