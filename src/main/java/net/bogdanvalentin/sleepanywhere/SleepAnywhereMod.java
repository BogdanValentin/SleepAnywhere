package net.bogdanvalentin.sleepanywhere;

import net.bogdanvalentin.sleepanywhere.network.SleepNetwork;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.event.entity.player.SleepingLocationCheckEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.server.ServerLifecycleHooks;

@Mod(SleepAnywhere.MOD_ID)
public class SleepAnywhereMod {
    public SleepAnywhereMod() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, SleepAnywhereConfigSpec.SPEC);

        modBus.addListener(this::onConfigLoading);
        modBus.addListener(this::onConfigReloading);
        modBus.addListener(this::onCommonSetup);

        MinecraftForge.EVENT_BUS.addListener(SleepAnywhereMod::onSleepingLocationCheck);
        MinecraftForge.EVENT_BUS.addListener(SleepAnywhereMod::onWakeUp);
        MinecraftForge.EVENT_BUS.addListener(SleepAnywhereMod::onServerTick);

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> SleepAnywhereClient.init(modBus));
    }

    private void onConfigLoading(ModConfigEvent.Loading event) {
        onConfigChanged(event);
    }

    private void onConfigReloading(ModConfigEvent.Reloading event) {
        onConfigChanged(event);
    }

    private void onConfigChanged(ModConfigEvent event) {
        if (event.getConfig().getSpec() == SleepAnywhereConfigSpec.SPEC) {
            SleepAnywhere.setConfig(SleepAnywhereConfigSpec.toConfig());
        }
    }

    private void onCommonSetup(FMLCommonSetupEvent event) {
        SleepNetwork.register();
    }

    private static void onSleepingLocationCheck(SleepingLocationCheckEvent event) {
        if (BedlessSleepers.contains(event.getEntity().getUUID())) {
            event.setResult(Event.Result.ALLOW);
        }
    }

    private static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            SleepHandler.tickNotices(ServerLifecycleHooks.getCurrentServer());
        }
    }

    private static void onWakeUp(PlayerWakeUpEvent event) {
        if (!(event.getPlayer() instanceof ServerPlayer player)) {
            return;
        }
        if (BedlessSleepers.remove(player.getUUID()) && !event.wakeImmediately() && player.isSleepingLongEnough()) {
            SleepHandler.applyBedlessEffects(player);
        }
    }
}
