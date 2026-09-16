package net.bogdanvalentin.sleepanywhere;

import net.bogdanvalentin.sleepanywhere.network.SleepPayload;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.KeyMapping;
import com.mojang.blaze3d.platform.InputConstants;
import org.lwjgl.glfw.GLFW;

public class SleepAnywhereClient implements ClientModInitializer {
    public static final String KEY_CATEGORY = "key.category.sleepanywhere.sleepanywhere";
    public static final String KEY_SLEEP = "key.sleepanywhere.sleep";

    private static KeyMapping sleepKey;

    @Override
    public void onInitializeClient() {
        sleepKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                KEY_SLEEP,
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_M,
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
