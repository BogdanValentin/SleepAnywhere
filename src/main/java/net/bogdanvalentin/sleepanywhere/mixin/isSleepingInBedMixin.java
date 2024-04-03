package net.bogdanvalentin.sleepanywhere.mixin;

import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(LivingEntity.class)
public class isSleepingInBedMixin {
	@Overwrite
	private boolean isSleepingInBed() {
		return true;
	}
}