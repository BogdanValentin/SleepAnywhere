package net.bogdanvalentin.sleepanywhere;

import com.mojang.blaze3d.platform.InputConstants;
import net.bogdanvalentin.sleepanywhere.network.SleepNetwork;
import net.bogdanvalentin.sleepanywhere.network.SleepPacket;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.lwjgl.glfw.GLFW;

public final class SleepAnywhereClient {
    public static final String KEY_CATEGORY = "key.category.sleepanywhere.sleepanywhere";
    public static final String KEY_SLEEP = "key.sleepanywhere.sleep";

    private static final KeyMapping SLEEP_KEY = new KeyMapping(
            KEY_SLEEP,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_M,
            KEY_CATEGORY
    );

    private SleepAnywhereClient() {
    }

    public static void init(IEventBus modBus) {
        modBus.addListener(SleepAnywhereClient::onClientSetup);
        MinecraftForge.EVENT_BUS.addListener(SleepAnywhereClient::onClientTick);
    }

    private static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> ClientRegistry.registerKeyBinding(SLEEP_KEY));
    }

    private static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }
        while (SLEEP_KEY.consumeClick()) {
            SleepNetwork.CHANNEL.sendToServer(SleepPacket.INSTANCE);
        }
    }
}
