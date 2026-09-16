package net.bogdanvalentin.sleepanywhere;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class BedlessSleepers {
    private static final Set<UUID> SLEEPERS = ConcurrentHashMap.newKeySet();

    private BedlessSleepers() {
    }

    public static void add(UUID player) {
        SLEEPERS.add(player);
    }

    public static boolean contains(UUID player) {
        return SLEEPERS.contains(player);
    }

    public static boolean remove(UUID player) {
        return SLEEPERS.remove(player);
    }
}
