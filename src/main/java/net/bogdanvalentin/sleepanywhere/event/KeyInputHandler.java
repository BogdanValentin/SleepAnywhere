package net.bogdanvalentin.sleepanywhere.event;

import net.bogdanvalentin.sleepanywhere.networking.ModMessages;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {
    public static final String KEY_CATEGORY_TUTORIAL = "key.category.sleepanywhere.sleepanywhere";
    public static final String KEY_SLEEP = "key.category.sleepanywhere.sleep";

    public static KeyBinding sleepingKey;

    public static void registerKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (sleepingKey.wasPressed()) {
                ClientPlayNetworking.send(ModMessages.SLEEP_ID, PacketByteBufs.create());
            }
        });
    }

    public static void register() {
        sleepingKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_SLEEP,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_M,
                KEY_CATEGORY_TUTORIAL
        ));
        registerKeyInputs();
    }
}
