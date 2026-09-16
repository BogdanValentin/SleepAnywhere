package net.bogdanvalentin.sleepanywhere;

import net.bogdanvalentin.sleepanywhere.network.SleepPayload;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import com.mojang.blaze3d.platform.InputConstants;

public class SleepAnywhereClient implements ClientModInitializer {
    public static final KeyMapping.Category KEY_CATEGORY =
            KeyMapping.Category.register(Identifier.fromNamespaceAndPath(SleepAnywhere.MOD_ID, SleepAnywhere.MOD_ID));
    public static final String KEY_SLEEP = "key.sleepanywhere.sleep";

    private static KeyMapping sleepKey;

    @Override
    public void onInitializeClient() {
        sleepKey = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                KEY_SLEEP,
                InputConstants.Type.KEYBOARD,
                InputConstants.KEY_M,
                KEY_CATEGORY
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (sleepKey.consumeClick()) {
                if (ClientPlayNetworking.canSend(SleepPayload.TYPE)) {
                    ClientPlayNetworking.send(SleepPayload.INSTANCE);
                }
            }
        });
    }
}
