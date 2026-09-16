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

        ServerLevel level = player.serverLevel();
        if (level.dimension() != Level.OVERWORLD) {
            deny(player, "overworld_only");
            return;
        }
        if (level.isDay() && !(SleepAnywhere.config().sleepThroughThunderstorms && level.isThundering())) {
            deny(player, "night_only");
            return;
        }

        BlockPos pos = player.blockPosition();
        BedlessSleepers.add(player.getUUID());
        player.startSleeping(pos);

        if (SleepAnywhere.config().setSpawnPoint) {
            player.setRespawnPosition(level.dimension(), pos, player.getYRot(), false, true);
        }

        level.updateSleepingPlayerList();
    }

    public static void applyBedlessEffects(ServerPlayer player) {
        SleepAnywhereConfig config = SleepAnywhere.config();
        apply(player, MobEffects.HUNGER, config.hungerEffect);
        apply(player, MobEffects.CONFUSION, config.nauseaEffect);
        apply(player, MobEffects.BLINDNESS, config.blindnessEffect);
        apply(player, MobEffects.DARKNESS, config.darknessEffect);
        apply(player, MobEffects.DIG_SLOWDOWN, config.fatigueEffect);
        apply(player, MobEffects.WEAKNESS, config.weaknessEffect);
        apply(player, MobEffects.MOVEMENT_SLOWDOWN, config.slownessEffect);
    }

    private static void apply(ServerPlayer player, Holder<MobEffect> effect, int seconds) {
        if (seconds > 0) {
            player.addEffect(new MobEffectInstance(effect, seconds * 20, 0));
        }
    }

    private static void deny(Player player, String reason) {
        player.displayClientMessage(Component.translatable("message." + SleepAnywhere.MOD_ID + "." + reason), true);
    }
}
