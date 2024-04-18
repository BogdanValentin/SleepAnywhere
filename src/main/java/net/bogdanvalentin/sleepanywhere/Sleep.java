package net.bogdanvalentin.sleepanywhere;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

import java.util.List;

import static net.bogdanvalentin.sleepanywhere.SleepAnywhere.CONFIG;

public class Sleep {
    private static int tickCounter = 0;

    /**
     * Schedule sleep for all players in the server world.
     * This method registers an event handler to check if all players are sleeping.
     * If all players are sleeping, it increments a tick counter until a certain threshold,
     * after which it performs a specific action and resets the tick counter.
     * If not all players are sleeping, it resets the tick counter.
     *
     * @param world The server world in which the players reside.
     */
    static void scheduleSleep(ServerWorld world) {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            List<ServerPlayerEntity> playerList =  world.getPlayers();
            playerList.removeIf(ServerPlayerEntity::isSpectator);
            if(!playerList.isEmpty()) {
                boolean allPlayersSleeping = true;
                for (ServerPlayerEntity it : playerList) {
                    if(!it.isSleeping()) {
                        allPlayersSleeping = false;
                        break;
                    }
                }
                if(allPlayersSleeping) {
                    if(tickCounter < 95) { // IF IT'S BIGGER THAN 100 BEDS TAKE CONTROL AND YOU SKIP TO THE NEXT NIGHT BEWARE
                        tickCounter++;
                    } else {
                        applySleepEffectsAndAdvanceTime(world);
                        tickCounter = 0;
                    }
                } else {
                    tickCounter = 0;
                }
            }
        });
    }

    /**
     * Applies sleep-related effects to players who sleep without a bed and advances the time to the next day while setting clear weather for 2 days.
     *
     * @param world The server world where the action takes place.
     */
    private static void applySleepEffectsAndAdvanceTime(ServerWorld world) {
        List<ServerPlayerEntity> effectsGiversList = world.getPlayers();
        for( ServerPlayerEntity it : effectsGiversList) {
            if(!BedChecker.isBedNearby(world, it.getBlockPos())) {
                if(CONFIG.hungerEffect() != 0) {
                    StatusEffectInstance hungerEffect = new StatusEffectInstance(StatusEffects.HUNGER, CONFIG.hungerEffect() * 20, 0);
                    it.addStatusEffect(hungerEffect);
                }
                if(CONFIG.nauseaEffect() != 0) {
                    StatusEffectInstance nauseaEffect = new StatusEffectInstance(StatusEffects.NAUSEA, CONFIG.nauseaEffect() * 20, 0);
                    it.addStatusEffect(nauseaEffect);
                }
                if(CONFIG.blindnessEffect() != 0) {
                    StatusEffectInstance blindnessEffect = new StatusEffectInstance(StatusEffects.BLINDNESS, CONFIG.blindnessEffect() * 20, 0);
                    it.addStatusEffect(blindnessEffect);
                }
                if(CONFIG.darknessEffect() != 0) {
                    StatusEffectInstance darknessEffect = new StatusEffectInstance(StatusEffects.DARKNESS, CONFIG.darknessEffect() * 20, 0);
                    it.addStatusEffect(darknessEffect);
                }
                if(CONFIG.fatigueEffect() != 0) {
                    StatusEffectInstance fatigueEffect = new StatusEffectInstance(StatusEffects.MINING_FATIGUE, CONFIG.fatigueEffect() * 20, 0);
                    it.addStatusEffect(fatigueEffect);
                }
                if(CONFIG.weaknessEffect() != 0) {
                    StatusEffectInstance weaknessEffect = new StatusEffectInstance(StatusEffects.WEAKNESS, CONFIG.weaknessEffect() * 20, 0);
                    it.addStatusEffect(weaknessEffect);
                }
                if(CONFIG.slownessEffect() != 0) {
                    StatusEffectInstance slownessEffect = new StatusEffectInstance(StatusEffects.SLOWNESS, CONFIG.slownessEffect() * 20, 0);
                    it.addStatusEffect(slownessEffect);
                }
            }
        }

        world.setTimeOfDay(world.getTimeOfDay() / 24000 * 24000 + 24000);
        world.setWeather(48000, 0, false, false);
    }
}
