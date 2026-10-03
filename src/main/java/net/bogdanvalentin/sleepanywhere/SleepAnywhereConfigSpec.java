package net.bogdanvalentin.sleepanywhere;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.List;

public final class SleepAnywhereConfigSpec {
    public static final ForgeConfigSpec SPEC;

    private static final Entry HUNGER;
    private static final Entry NAUSEA;
    private static final Entry BLINDNESS;
    private static final Entry MINING_FATIGUE;
    private static final Entry WEAKNESS;
    private static final Entry SLOWNESS;
    private static final ForgeConfigSpec.ConfigValue<Boolean> SET_SPAWN_POINT;
    private static final ForgeConfigSpec.ConfigValue<Boolean> SLEEP_THROUGH_THUNDERSTORMS;
    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> CUSTOM_EFFECTS;

    private SleepAnywhereConfigSpec() {
    }

    private record Entry(ForgeConfigSpec.IntValue seconds, ForgeConfigSpec.IntValue level) {
    }

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        HUNGER = effect(builder, "hunger", 120);
        NAUSEA = effect(builder, "nausea", 20);
        BLINDNESS = effect(builder, "blindness", 10);
        MINING_FATIGUE = effect(builder, "miningFatigue", 90);
        WEAKNESS = effect(builder, "weakness", 90);
        SLOWNESS = effect(builder, "slowness", 60);

        SET_SPAWN_POINT = builder
                .comment("Set your respawn point where you sleep, like a bed would")
                .define("setSpawnPoint", false);
        SLEEP_THROUGH_THUNDERSTORMS = builder
                .comment("Allow sleeping during a thunderstorm, like a bed would")
                .define("sleepThroughThunderstorms", true);
        CUSTOM_EFFECTS = builder
                .comment("Extra debuffs, including ones from other mods, as \"effect_id,seconds,level\"",
                        "The level is optional and defaults to 1, e.g. [\"minecraft:poison,10\", \"somemod:drowsy,60,2\"]",
                        "Use /effect give in game to find an effect's id")
                .defineListAllowEmpty(List.of("customEffects"), List::of, entry -> entry instanceof String);

        SPEC = builder.build();
    }

    private static Entry effect(ForgeConfigSpec.Builder builder, String name, int defaultSeconds) {
        builder.push(name);
        ForgeConfigSpec.IntValue seconds = builder
                .comment("Duration in seconds, 0 disables it")
                .defineInRange("seconds", defaultSeconds, 0, 3600);
        ForgeConfigSpec.IntValue level = builder
                .comment("Effect level, 1 is normal potion strength")
                .defineInRange("level", 1, 1, 10);
        builder.pop();
        return new Entry(seconds, level);
    }

    public static SleepAnywhereConfig toConfig() {
        SleepAnywhereConfig config = new SleepAnywhereConfig();
        config.hunger = read(HUNGER);
        config.nausea = read(NAUSEA);
        config.blindness = read(BLINDNESS);
        config.miningFatigue = read(MINING_FATIGUE);
        config.weakness = read(WEAKNESS);
        config.slowness = read(SLOWNESS);
        config.setSpawnPoint = SET_SPAWN_POINT.get();
        config.sleepThroughThunderstorms = SLEEP_THROUGH_THUNDERSTORMS.get();
        List<String> invalid = new ArrayList<>();
        config.customEffects = parseCustomEffects(CUSTOM_EFFECTS.get(), invalid);
        config.invalidCustomEffects = List.copyOf(invalid);
        return config;
    }

    private static SleepAnywhereConfig.Effect read(Entry entry) {
        return new SleepAnywhereConfig.Effect(entry.seconds().get(), entry.level().get());
    }

    private static List<SleepAnywhereConfig.CustomEffect> parseCustomEffects(List<? extends String> entries, List<String> invalid) {
        List<SleepAnywhereConfig.CustomEffect> parsed = new ArrayList<>();
        for (String entry : entries) {
            SleepAnywhereConfig.CustomEffect effect = parseCustomEffect(entry);
            if (effect == null) {
                invalid.add(entry);
                SleepAnywhere.LOGGER.warn("Ignoring custom effect \"{}\", expected \"effect_id,seconds,level\" with seconds 0-3600 and level 1-10", entry);
            } else {
                parsed.add(effect);
            }
        }
        return List.copyOf(parsed);
    }

    private static SleepAnywhereConfig.CustomEffect parseCustomEffect(String entry) {
        String[] parts = entry.split(",");
        if (parts.length < 2 || parts.length > 3) {
            return null;
        }
        ResourceLocation id = ResourceLocation.tryParse(parts[0].trim());
        if (id == null) {
            return null;
        }
        try {
            int seconds = Integer.parseInt(parts[1].trim());
            int level = parts.length == 3 ? Integer.parseInt(parts[2].trim()) : 1;
            if (seconds < 0 || seconds > 3600 || level < 1 || level > 10) {
                return null;
            }
            return new SleepAnywhereConfig.CustomEffect(id, new SleepAnywhereConfig.Effect(seconds, level));
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
