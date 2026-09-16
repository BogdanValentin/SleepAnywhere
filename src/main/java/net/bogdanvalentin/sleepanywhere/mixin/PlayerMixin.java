package net.bogdanvalentin.sleepanywhere.mixin;

import net.bogdanvalentin.sleepanywhere.BedlessSleepers;
import net.bogdanvalentin.sleepanywhere.SleepHandler;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public class PlayerMixin {
    @Inject(method = "stopSleeping", at = @At("HEAD"))
    private void sleepanywhere$onStopSleeping(CallbackInfo ci) {
        if (!((Object) this instanceof ServerPlayer player)) {
            return;
        }
        if (BedlessSleepers.remove(player.getUUID()) && player.isSleepingLongEnough()) {
            SleepHandler.applyBedlessEffects(player);
        }
    }
}
