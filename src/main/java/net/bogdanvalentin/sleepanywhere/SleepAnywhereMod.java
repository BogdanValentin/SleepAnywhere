package net.bogdanvalentin.sleepanywhere;

import net.bogdanvalentin.sleepanywhere.network.SleepPayload;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;

public class SleepAnywhereMod implements ModInitializer {
    @Override
    public void onInitialize() {
        SleepAnywhere.setConfig(SleepAnywhereConfig.load(FabricLoader.getInstance().getConfigDir()));

        PayloadTypeRegistry.serverboundPlay().register(SleepPayload.TYPE, SleepPayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(SleepPayload.TYPE,
                (payload, context) -> SleepHandler.requestSleep(context.player()));
    }
}
