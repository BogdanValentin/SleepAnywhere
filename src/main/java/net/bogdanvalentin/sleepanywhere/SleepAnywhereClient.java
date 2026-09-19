package net.bogdanvalentin.sleepanywhere;

import com.mojang.blaze3d.platform.InputConstants;
import net.bogdanvalentin.sleepanywhere.network.SleepPayload;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import net.neoforged.neoforge.common.NeoForge;

@Mod(value = SleepAnywhere.MOD_ID, dist = Dist.CLIENT)
public class SleepAnywhereClient {
    public static final KeyMapping.Category KEY_CATEGORY =
            new KeyMapping.Category(Identifier.fromNamespaceAndPath(SleepAnywhere.MOD_ID, SleepAnywhere.MOD_ID));
    public static final String KEY_SLEEP = "key.sleepanywhere.sleep";

    private static final KeyMapping SLEEP_KEY = new KeyMapping(
            KEY_SLEEP,
            InputConstants.Type.KEYBOARD,
            InputConstants.KEY_M,
            KEY_CATEGORY
    );

    public SleepAnywhereClient(IEventBus modBus, ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
        modBus.addListener(this::registerKeyMappings);
        NeoForge.EVENT_BUS.addListener(SleepAnywhereClient::onClientTick);
    }

    private void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.registerCategory(KEY_CATEGORY);
        event.register(SLEEP_KEY);
    }

    private static void onClientTick(ClientTickEvent.Post event) {
        while (SLEEP_KEY.consumeClick()) {
            ClientPacketDistributor.sendToServer(SleepPayload.INSTANCE);
        }
    }
}
