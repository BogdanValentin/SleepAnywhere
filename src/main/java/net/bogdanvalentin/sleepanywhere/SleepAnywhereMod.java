package net.bogdanvalentin.sleepanywhere;

import net.bogdanvalentin.sleepanywhere.network.SleepPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@Mod(SleepAnywhere.MOD_ID)
public class SleepAnywhereMod {
    public SleepAnywhereMod(IEventBus modBus) {
        SleepAnywhere.setConfig(SleepAnywhereConfig.load(FMLPaths.CONFIGDIR.get()));
        modBus.addListener(this::registerPayloads);
    }

    private void registerPayloads(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1");
        registrar.playToServer(SleepPayload.TYPE, SleepPayload.CODEC, (payload, context) -> {
            if (context.player() instanceof ServerPlayer player) {
                SleepHandler.requestSleep(player);
            }
        });
    }
}
