package net.bogdanvalentin.sleepanywhere;

import net.bogdanvalentin.sleepanywhere.event.KeyInputHandler;
import net.bogdanvalentin.sleepanywhere.networking.ModMessages;
import net.fabricmc.api.ClientModInitializer;

public class SleepAnywhereClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        KeyInputHandler.register();
        ModMessages.registerS2CPackets();
    }
}
