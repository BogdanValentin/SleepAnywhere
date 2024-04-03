package net.bogdanvalentin.sleepanywhere.networking;

import net.bogdanvalentin.sleepanywhere.SleepAnywhere;
import net.bogdanvalentin.sleepanywhere.networking.packet.SleepC2SPacket;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

public class ModMessages {
    public static final Identifier SLEEP_ID = new Identifier(SleepAnywhere.MOD_ID, "sleep");

    public static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(SLEEP_ID, SleepC2SPacket::receive);
    }

    public static void registerS2CPackets() { }
}
