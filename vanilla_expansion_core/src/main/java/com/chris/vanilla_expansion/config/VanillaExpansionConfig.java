package com.chris.vanilla_expansion.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class VanillaExpansionConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File CONFIG_FILE = FabricLoader.getInstance().getConfigDir().resolve("vanilla_expansion.json").toFile();

    // Default configuration values
    public boolean enableStorage = true;

    private static VanillaExpansionConfig INSTANCE;

    public static VanillaExpansionConfig get() {
        if (INSTANCE == null) {
            INSTANCE = load();
        }
        return INSTANCE;
    }

    private static VanillaExpansionConfig load() {
        if (CONFIG_FILE.exists()) {
            try (FileReader reader = new FileReader(CONFIG_FILE)) {
                VanillaExpansionConfig config = GSON.fromJson(reader, VanillaExpansionConfig.class);
                return config != null ? config : new VanillaExpansionConfig();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Create default config file if it doesn't exist
        VanillaExpansionConfig defaultConfig = new VanillaExpansionConfig();
        defaultConfig.save();
        return defaultConfig;
    }

    public void save() {
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            GSON.toJson(this, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}