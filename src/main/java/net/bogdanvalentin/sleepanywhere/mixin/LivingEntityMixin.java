package net.bogdanvalentin.sleepanywhere.mixin;

import net.bogdanvalentin.sleepanywhere.BedlessSleepers;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(method = "checkBedExists", at = @At("HEAD"), cancellable = true)
    private void sleepanywhere$allowBedlessSleep(CallbackInfoReturnable<Boolean> cir) {
        if ((Object) this instanceof Player player && BedlessSleepers.contains(player.getUUID())) {
            cir.setReturnValue(true);
        }
    }
}
