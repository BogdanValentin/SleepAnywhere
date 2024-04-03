package net.bogdanvalentin.sleepanywhere;

import net.minecraft.block.BedBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BedChecker {

    /**
     * Checks if a bed is nearby the given position in the world.
     *
     * @param world The world where the check is performed.
     * @param pos The position to check around.
     * @return True if a bed is nearby, otherwise false.
     */
    public static boolean isBedNearby(World world, BlockPos pos) {
        // Get the block at the player's position
        return world.getBlockState(pos).getBlock() instanceof BedBlock;
    }
}

