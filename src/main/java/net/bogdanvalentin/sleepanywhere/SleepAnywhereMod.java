package net.bogdanvalentin.sleepanywhere;

import net.bogdanvalentin.sleepanywhere.network.SleepPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@Mod(SleepAnywhere.MOD_ID)
public class SleepAnywhereMod {
    public SleepAnywhereMod(IEventBus modBus, ModContainer container) {
        container.registerConfig(ModConfig.Type.COMMON, SleepAnywhereConfigSpec.SPEC);

        modBus.addListener(ModConfigEvent.Loading.class, this::onConfigChanged);
        modBus.addListener(ModConfigEvent.Reloading.class, this::onConfigChanged);
        modBus.addListener(this::registerPayloads);
    }

    private void onConfigChanged(ModConfigEvent event) {
        if (event.getConfig().getSpec() == SleepAnywhereConfigSpec.SPEC) {
            SleepAnywhere.setConfig(SleepAnywhereConfigSpec.toConfig());
        }
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
