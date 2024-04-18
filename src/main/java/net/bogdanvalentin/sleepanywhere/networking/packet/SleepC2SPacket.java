package net.bogdanvalentin.sleepanywhere.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public class SleepC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        if (player.isSpectator()) {
            player.sendMessage(Text.translatable("You can not sleep while in Spectator Mode"), true);
            return;
        }

        // only allow to sleep if in overworld
        if (player.getWorld().getRegistryKey() != World.OVERWORLD) {
            player.sendMessage(Text.translatable("You can only sleep in the Overworld"), true);
            return;
        }

        // Overworld as we do not care about the other dimensions
        ServerWorld world = server.getOverworld();

        if (world.getTimeOfDay() % 24000 > 13000) { // check if it's night
            player.sleep(player.getBlockPos());     // put the player in the sleeping position
        } else {
            player.sendMessage(Text.translatable("You can sleep only at night"), true);
        }
    }
}

