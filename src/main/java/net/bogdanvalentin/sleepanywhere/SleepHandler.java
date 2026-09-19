package net.bogdanvalentin.sleepanywhere;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.LevelData;

public final class SleepHandler {
    private SleepHandler() {
    }

    public static void requestSleep(ServerPlayer player) {
        if (player.isSleeping()) {
            return;
        }
        if (player.isSpectator()) {
            deny(player, "spectator");
            return;
        }

        ServerLevel level = player.level();
        if (level.dimension() != Level.OVERWORLD) {
            deny(player, "overworld_only");
            return;
        }
        if (level.isBrightOutside() && !(SleepAnywhere.config().sleepThroughThunderstorms && level.isThundering())) {
            deny(player, "night_only");
            return;
        }

        BlockPos pos = player.blockPosition();
        BedlessSleepers.add(player.getUUID());
        if (!player.startSleeping(pos)) {
            BedlessSleepers.remove(player.getUUID());
            return;
        }

        if (SleepAnywhere.config().setSpawnPoint) {
            player.setRespawnPosition(new ServerPlayer.RespawnConfig(
                    LevelData.RespawnData.of(level.dimension(), pos, player.getYRot(), player.getXRot()), false), true);
        }

        level.updateSleepingPlayerList();
    }

    public static void applyBedlessEffects(ServerPlayer player) {
        SleepAnywhereConfig config = SleepAnywhere.config();
        apply(player, MobEffects.HUNGER, config.hunger);
        apply(player, MobEffects.NAUSEA, config.nausea);
        apply(player, MobEffects.BLINDNESS, config.blindness);
        apply(player, MobEffects.DARKNESS, config.darkness);
        apply(player, MobEffects.MINING_FATIGUE, config.miningFatigue);
        apply(player, MobEffects.WEAKNESS, config.weakness);
        apply(player, MobEffects.SLOWNESS, config.slowness);
    }

    private static void apply(ServerPlayer player, Holder<MobEffect> effect, SleepAnywhereConfig.Effect setting) {
        if (setting.seconds > 0) {
            player.addEffect(new MobEffectInstance(effect, setting.seconds * 20, setting.level - 1));
        }
    }

    private static void deny(Player player, String reason) {
        player.sendOverlayMessage(Component.translatable("message." + SleepAnywhere.MOD_ID + "." + reason));
    }
}
