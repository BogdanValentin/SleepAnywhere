package net.bogdanvalentin.sleepanywhere;

import net.bogdanvalentin.sleepanywhere.config.SleepAnywhereConfig;
import net.bogdanvalentin.sleepanywhere.networking.ModMessages;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.minecraft.server.world.ServerWorld;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.bogdanvalentin.sleepanywhere.Sleep.scheduleSleep;

public class SleepAnywhere implements ModInitializer {
	public static final String MOD_ID = "sleepanywhere";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final SleepAnywhereConfig CONFIG = SleepAnywhereConfig.createAndLoad();

	@Override
	public void onInitialize() {
		ModMessages.registerC2SPackets();

		ServerWorldEvents.LOAD.register((server, world) -> {
			// Check if the loaded world is the overworld
			if (world.getRegistryKey() == ServerWorld.OVERWORLD) {
				scheduleSleep(world);
			}
		});
	}
}