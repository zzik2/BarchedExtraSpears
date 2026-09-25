package zzik2.barched.extra.spears.config.spears;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.architectury.platform.Platform;
import zzik2.barched.extra.spears.BarchedES;
import zzik2.barched.extra.spears.data.items.SpearAttributeData;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public final class SpearAttributeDataConfigManager {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_DIR = Platform.getConfigFolder()
            .resolve(BarchedES.MOD_ID)
            .resolve("materials");

    private static final Map<String, SpearAttributeData> LOADED_CONFIGS = new HashMap<>();

    private SpearAttributeDataConfigManager() {
    }

    public static SpearAttributeData loadOrCreate(String modId, String materialName, SpearAttributeData defaults) {
        String cacheKey = modId + "/" + materialName;
        if (LOADED_CONFIGS.containsKey(cacheKey)) {
            return LOADED_CONFIGS.get(cacheKey);
        }

        Path modDir = CONFIG_DIR.resolve(modId);
        try {
            Files.createDirectories(modDir);
        } catch (IOException e) {
            BarchedES.LOGGER.error("Failed to create config directory: {}", modDir, e);
        }

        Path configFile = modDir.resolve(materialName + ".json");
        SpearAttributeData result;

        if (Files.exists(configFile)) {
            result = load(configFile, materialName);
            if (result == null) {
                BarchedES.LOGGER.warn("Failed to load config for '{}', using defaults", materialName);
                result = defaults;
            }
        } else {
            save(configFile, defaults, materialName);
            result = defaults;
        }

        LOADED_CONFIGS.put(cacheKey, result);
        return result;
    }

    private static SpearAttributeData load(Path configFile, String materialName) {
        try (Reader reader = Files.newBufferedReader(configFile)) {
            SpearAttributeDataConfig config = GSON.fromJson(reader, SpearAttributeDataConfig.class);
            if (config == null) {
                return null;
            }
            BarchedES.LOGGER.info("Loaded SpearAttributeData config for material: {}", materialName);
            return config.toSpearAttributeData();
        } catch (Exception e) {
            BarchedES.LOGGER.error("Failed to read config file for '{}': {}", materialName, e.getMessage());
            return null;
        }
    }

    private static void save(Path configFile, SpearAttributeData SpearAttributeData, String materialName) {
        SpearAttributeDataConfig config = SpearAttributeDataConfig.fromSpearAttributeData(SpearAttributeData);
        try (Writer writer = Files.newBufferedWriter(configFile)) {
            GSON.toJson(config, writer);
            BarchedES.LOGGER.info("Created default config for material: {}", materialName);
        } catch (IOException e) {
            BarchedES.LOGGER.error("Failed to write config file for '{}': {}", materialName, e.getMessage());
        }
    }
}
