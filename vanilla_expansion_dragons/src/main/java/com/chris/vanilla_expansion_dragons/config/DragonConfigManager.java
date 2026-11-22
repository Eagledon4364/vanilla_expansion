package com.chris.vanilla_expansion_dragons.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class DragonConfigManager {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Map<String, DragonStatsConfig> LOADED_CONFIGS = new HashMap<>();

    /**
     * Loads a species config by name, generating a default JSON file if it doesn't exist yet.
     *
     * @param speciesId
     * @param fallbackDefault The hardcoded default stats to use & save if the file is missing
     * @return The loaded or newly generated DragonStatsConfig
     */
    public static DragonStatsConfig getOrCreateConfig(String speciesId, DragonStatsConfig fallbackDefault) {
        // Return cached version if already loaded
        if (LOADED_CONFIGS.containsKey(speciesId)) {
            return LOADED_CONFIGS.get(speciesId);
        }

        // Points to: <minecraft_folder>/config/vanilla_expansion_dragons/<speciesId>.json

        Path configDir = FabricLoader.getInstance().getConfigDir().resolve("vanilla_expansion_dragons");
        File configFile = configDir.resolve(speciesId + ".json").toFile();

        if (!configDir.toFile().exists()) {
            configDir.toFile().mkdirs();
        }

        // If the JSON doesn't exist, write the fallback defaults to disk and return them
        if (!configFile.exists()) {
            saveConfig(configFile, fallbackDefault);
            LOADED_CONFIGS.put(speciesId, fallbackDefault);
            return fallbackDefault;
        }

        // Otherwise, read the existing JSON
        try (FileReader reader = new FileReader(configFile)) {
            DragonStatsConfig loaded = GSON.fromJson(reader, DragonStatsConfig.class);
            if (loaded == null) {
                // Handle empty/corrupted JSON file gracefully
                saveConfig(configFile, fallbackDefault);
                LOADED_CONFIGS.put(speciesId, fallbackDefault);
                return fallbackDefault;
            }
            LOADED_CONFIGS.put(speciesId, loaded);
            return loaded;
        } catch (Exception e) {
            System.err.println("[VanillaExpansionDragons] Failed to read config for " + speciesId + ", using defaults.");
            e.printStackTrace();
            return fallbackDefault;
        }
    }

    private static void saveConfig(File file, DragonStatsConfig config) {
        try (FileWriter writer = new FileWriter(file)) {
            GSON.toJson(config, writer);
        } catch (IOException e) {
            System.err.println("[VanillaExpansionDragons] Could not save default config to " + file.getPath());
            e.printStackTrace();
        }
    }

    public static void clearCache() {
        LOADED_CONFIGS.clear();
    }
}