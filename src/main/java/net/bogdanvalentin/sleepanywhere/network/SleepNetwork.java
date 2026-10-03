package net.bogdanvalentin.sleepanywhere.network;

import net.bogdanvalentin.sleepanywhere.SleepAnywhere;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.Optional;

public final class SleepNetwork {
    private static final String PROTOCOL_VERSION = "1";

    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(SleepAnywhere.MOD_ID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    private SleepNetwork() {
    }

    public static void register() {
        CHANNEL.registerMessage(0, SleepPacket.class, SleepPacket::encode, SleepPacket::decode, SleepPacket::handle,
                Optional.of(NetworkDirection.PLAY_TO_SERVER));
    }
}
