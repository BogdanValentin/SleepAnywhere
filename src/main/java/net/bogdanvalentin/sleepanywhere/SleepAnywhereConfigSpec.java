package net.bogdanvalentin.sleepanywhere;

import net.neoforged.neoforge.common.ModConfigSpec;

public final class SleepAnywhereConfigSpec {
    public static final ModConfigSpec SPEC;

    private static final Entry HUNGER;
    private static final Entry NAUSEA;
    private static final Entry BLINDNESS;
    private static final Entry DARKNESS;
    private static final Entry MINING_FATIGUE;
    private static final Entry WEAKNESS;
    private static final Entry SLOWNESS;
    private static final ModConfigSpec.ConfigValue<Boolean> SET_SPAWN_POINT;
    private static final ModConfigSpec.ConfigValue<Boolean> SLEEP_THROUGH_THUNDERSTORMS;

    private SleepAnywhereConfigSpec() {
    }

    private record Entry(ModConfigSpec.IntValue seconds, ModConfigSpec.IntValue level) {
    }

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        HUNGER = effect(builder, "hunger", 120);
        NAUSEA = effect(builder, "nausea", 20);
        BLINDNESS = effect(builder, "blindness", 10);
        DARKNESS = effect(builder, "darkness", 30);
        MINING_FATIGUE = effect(builder, "miningFatigue", 90);
        WEAKNESS = effect(builder, "weakness", 90);
        SLOWNESS = effect(builder, "slowness", 60);

        SET_SPAWN_POINT = builder
                .comment("Set your respawn point where you sleep, like a bed would")
                .translation("sleepanywhere.configuration.setSpawnPoint")
                .define("setSpawnPoint", false);
        SLEEP_THROUGH_THUNDERSTORMS = builder
                .comment("Allow sleeping during a thunderstorm, like a bed would")
                .translation("sleepanywhere.configuration.sleepThroughThunderstorms")
                .define("sleepThroughThunderstorms", true);

        SPEC = builder.build();
    }

    private static Entry effect(ModConfigSpec.Builder builder, String name, int defaultSeconds) {
        builder.translation("sleepanywhere.configuration." + name).push(name);
        ModConfigSpec.IntValue seconds = builder
                .comment("Duration in seconds, 0 disables it")
                .translation("sleepanywhere.configuration." + name + ".seconds")
                .defineInRange("seconds", defaultSeconds, 0, 3600);
        ModConfigSpec.IntValue level = builder
                .comment("Effect level, 1 is normal potion strength")
                .translation("sleepanywhere.configuration." + name + ".level")
                .defineInRange("level", 1, 1, 10);
        builder.pop();
        return new Entry(seconds, level);
    }

    public static SleepAnywhereConfig toConfig() {
        SleepAnywhereConfig config = new SleepAnywhereConfig();
        config.hunger = read(HUNGER);
        config.nausea = read(NAUSEA);
        config.blindness = read(BLINDNESS);
        config.darkness = read(DARKNESS);
        config.miningFatigue = read(MINING_FATIGUE);
        config.weakness = read(WEAKNESS);
        config.slowness = read(SLOWNESS);
        config.setSpawnPoint = SET_SPAWN_POINT.get();
        config.sleepThroughThunderstorms = SLEEP_THROUGH_THUNDERSTORMS.get();
        return config;
    }

    private static SleepAnywhereConfig.Effect read(Entry entry) {
        return new SleepAnywhereConfig.Effect(entry.seconds().get(), entry.level().get());
    }
}
