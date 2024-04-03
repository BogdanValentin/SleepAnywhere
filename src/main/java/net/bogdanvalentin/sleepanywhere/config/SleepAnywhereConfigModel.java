package net.bogdanvalentin.sleepanywhere.config;

import io.wispforest.owo.config.Option;
import io.wispforest.owo.config.annotation.Config;
import io.wispforest.owo.config.annotation.Modmenu;
import io.wispforest.owo.config.annotation.Sync;

@Modmenu(modId = "fabric")
@Config(name = "sleepanywhere-config", wrapperName = "SleepAnywhereConfig")
@Sync(Option.SyncMode.OVERRIDE_CLIENT)
public class SleepAnywhereConfigModel {
    public int hungerEffect = 5;
    public int nauseaEffect = 5;
    public int blindnessEffect = 5;
    public int darknessEffect = 0;
    public int fatigueEffect = 10;
    public int weaknessEffect = 10;
    public int slownessEffect = 10;
}

