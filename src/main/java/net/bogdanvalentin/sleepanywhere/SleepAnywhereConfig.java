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

    public Effect hunger = new Effect(120, 1);
    public Effect nausea = new Effect(20, 1);
    public Effect blindness = new Effect(10, 1);
    public Effect darkness = new Effect(30, 1);
    public Effect miningFatigue = new Effect(90, 1);
    public Effect weakness = new Effect(90, 1);
    public Effect slowness = new Effect(60, 1);
    public boolean setSpawnPoint = false;
    public boolean sleepThroughThunderstorms = true;

    public static class Effect {
        public int seconds;
        public int level;

        public Effect(int seconds, int level) {
            this.seconds = seconds;
            this.level = level;
        }

        private static Effect sanitized(Effect value, Effect fallback) {
            if (value == null) {
                return fallback;
            }
            value.seconds = Math.max(0, value.seconds);
            value.level = Math.max(1, value.level);
            return value;
        }
    }

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
                return new SleepAnywhereConfig();
            }
        }

        config.sanitize();
        config.save(file);
        return config;
    }

    private void sanitize() {
        SleepAnywhereConfig defaults = new SleepAnywhereConfig();
        hunger = Effect.sanitized(hunger, defaults.hunger);
        nausea = Effect.sanitized(nausea, defaults.nausea);
        blindness = Effect.sanitized(blindness, defaults.blindness);
        darkness = Effect.sanitized(darkness, defaults.darkness);
        miningFatigue = Effect.sanitized(miningFatigue, defaults.miningFatigue);
        weakness = Effect.sanitized(weakness, defaults.weakness);
        slowness = Effect.sanitized(slowness, defaults.slowness);
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
