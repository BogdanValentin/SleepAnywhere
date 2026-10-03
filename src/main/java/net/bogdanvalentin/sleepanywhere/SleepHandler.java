package net.bogdanvalentin.sleepanywhere;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class SleepHandler {
    private static final Set<ResourceLocation> MISSING_EFFECTS = ConcurrentHashMap.newKeySet();
    private static final Map<UUID, Notice> NOTICES = new ConcurrentHashMap<>();
    private static final int NOTICE_TICKS = 200;
    private static final int NOTICE_RESEND_TICKS = 40;

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

        ServerLevel level = player.getLevel();
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
        apply(player, MobEffects.HUNGER, config.hunger);
        apply(player, MobEffects.CONFUSION, config.nausea);
        apply(player, MobEffects.BLINDNESS, config.blindness);
        apply(player, MobEffects.DIG_SLOWDOWN, config.miningFatigue);
        apply(player, MobEffects.WEAKNESS, config.weakness);
        apply(player, MobEffects.MOVEMENT_SLOWDOWN, config.slowness);
        List<String> skipped = new ArrayList<>(config.invalidCustomEffects);
        for (SleepAnywhereConfig.CustomEffect custom : config.customEffects) {
            MobEffect effect = ForgeRegistries.MOB_EFFECTS.getValue(custom.id());
            if (effect == null) {
                skipped.add(custom.id().toString());
                if (MISSING_EFFECTS.add(custom.id())) {
                    SleepAnywhere.LOGGER.warn("Custom effect {} is not registered. Is the mod installed, or is there a typo?", custom.id());
                }
            } else {
                apply(player, effect, custom.setting());
            }
        }
        if (!skipped.isEmpty() && canFixConfig(player)) {
            Component message = new TranslatableComponent(
                    "message." + SleepAnywhere.MOD_ID + ".skipped_effects", String.join(", ", skipped))
                    .withStyle(ChatFormatting.RED);
            NOTICES.put(player.getUUID(), new Notice(message, NOTICE_TICKS));
        }
    }

    private static void apply(ServerPlayer player, MobEffect effect, SleepAnywhereConfig.Effect setting) {
        if (setting.seconds > 0) {
            player.addEffect(new MobEffectInstance(effect, setting.seconds * 20, setting.level - 1));
        }
    }

    public static void tickNotices(MinecraftServer server) {
        Iterator<Map.Entry<UUID, Notice>> iterator = NOTICES.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<UUID, Notice> entry = iterator.next();
            Notice notice = entry.getValue();
            ServerPlayer player = server.getPlayerList().getPlayer(entry.getKey());
            if (player == null || notice.ticksLeft() <= 0) {
                iterator.remove();
                continue;
            }
            if (notice.ticksLeft() % NOTICE_RESEND_TICKS == 0) {
                player.displayClientMessage(notice.message(), true);
            }
            entry.setValue(new Notice(notice.message(), notice.ticksLeft() - 1));
        }
    }

    private record Notice(Component message, int ticksLeft) {
    }

    private static boolean canFixConfig(ServerPlayer player) {
        return player.hasPermissions(2) || player.server.isSingleplayerOwner(player.getGameProfile());
    }

    private static void deny(Player player, String reason) {
        player.displayClientMessage(new TranslatableComponent("message." + SleepAnywhere.MOD_ID + "." + reason), true);
    }
}
