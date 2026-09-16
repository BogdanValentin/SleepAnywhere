package net.bogdanvalentin.sleepanywhere.mixin;

import net.bogdanvalentin.sleepanywhere.BedlessSleepers;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.AbstractBedBlock;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(method = "startSleeping", at = @At("HEAD"), cancellable = true)
    private void sleepanywhere$startBedlessSleep(BlockPos bedPosition, CallbackInfoReturnable<Boolean> cir) {
        if (!((Object) this instanceof Player player) || !BedlessSleepers.contains(player.getUUID())) {
            return;
        }
        if (player.level().getBlockState(bedPosition).getBlock() instanceof AbstractBedBlock) {
            return;
        }

        if (player.isPassenger()) {
            player.stopRiding();
        }
        player.setPos(bedPosition.getX() + 0.5, bedPosition.getY() + 0.6875, bedPosition.getZ() + 0.5);
        player.setPose(Pose.SLEEPING);
        player.setSleepingPos(bedPosition);
        player.setDeltaMovement(Vec3.ZERO);
        player.needsSync = true;
        cir.setReturnValue(true);
    }

    @Inject(method = "checkBedExists", at = @At("HEAD"), cancellable = true)
    private void sleepanywhere$allowBedlessSleep(CallbackInfoReturnable<Boolean> cir) {
        if ((Object) this instanceof Player player && BedlessSleepers.contains(player.getUUID())) {
            cir.setReturnValue(true);
        }
    }
}
