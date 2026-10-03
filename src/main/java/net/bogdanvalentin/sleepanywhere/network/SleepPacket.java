package net.bogdanvalentin.sleepanywhere.network;

import net.bogdanvalentin.sleepanywhere.SleepHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public final class SleepPacket {
    public static final SleepPacket INSTANCE = new SleepPacket();

    private SleepPacket() {
    }

    public static void encode(SleepPacket packet, FriendlyByteBuf buf) {
    }

    public static SleepPacket decode(FriendlyByteBuf buf) {
        return INSTANCE;
    }

    public static void handle(SleepPacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player != null) {
                SleepHandler.requestSleep(player);
            }
        });
        context.setPacketHandled(true);
    }
}
