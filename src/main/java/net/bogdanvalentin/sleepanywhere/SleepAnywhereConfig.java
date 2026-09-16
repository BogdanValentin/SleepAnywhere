package net.bogdanvalentin.sleepanywhere;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class SleepAnywhereConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public int hungerEffect = 5;
    public int nauseaEffect = 5;
    public int blindnessEffect = 5;
    public int darknessEffect = 0;
    public int fatigueEffect = 10;
    public int weaknessEffect = 10;
    public int slownessEffect = 10;
    public boolean setSpawnPoint = false;
    public boolean sleepThroughThunderstorms = true;

    public static SleepAnywhereConfig load(Path configDir) {
        Path file = configDir.resolve(SleepAnywhere.MOD_ID + ".json");
        SleepAnywhereConfig config = new SleepAnywhereConfig();

        if (Files.exists(file)) {
            try (Reader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
                SleepAnywhereConfig loaded = GSON.fromJson(reader, SleepAnywhereConfig.class);
                if (loaded != null) {
                    config = loaded;
                }
            } catch (IOException | RuntimeException e) {
                SleepAnywhere.LOGGER.error("Failed to read {}, falling back to defaults", file, e);
                return config;
            }
        }

        config.save(file);
        return config;
    }

    private void save(Path file) {
        try {
            Files.createDirectories(file.getParent());
            try (Writer writer = Files.newBufferedWriter(file, StandardCharsets.UTF_8)) {
                GSON.toJson(this, writer);
            }
        } catch (IOException e) {
            SleepAnywhere.LOGGER.error("Failed to write {}", file, e);
        }
    }
}
