package net.bogdanvalentin.sleepanywhere.network;

import net.bogdanvalentin.sleepanywhere.SleepAnywhere;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record SleepPayload() implements CustomPacketPayload {
    public static final SleepPayload INSTANCE = new SleepPayload();

    public static final CustomPacketPayload.Type<SleepPayload> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(SleepAnywhere.MOD_ID, "sleep"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SleepPayload> CODEC = StreamCodec.unit(INSTANCE);

    @Override
    public CustomPacketPayload.Type<SleepPayload> type() {
        return TYPE;
    }
}
