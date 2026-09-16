package net.bogdanvalentin.sleepanywhere;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SleepAnywhere {
    public static final String MOD_ID = "sleepanywhere";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private static SleepAnywhereConfig config = new SleepAnywhereConfig();

    private SleepAnywhere() {
    }

    public static SleepAnywhereConfig config() {
        return config;
    }

    public static void setConfig(SleepAnywhereConfig loaded) {
        config = loaded;
    }
}
