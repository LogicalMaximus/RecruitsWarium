package com.logic.recruitswr.mixin.block;

import net.mcreator.crustychunks.block.LandMineBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(LandMineBlock.class)
public abstract class MixinLandMineBlock extends Block implements SimpleWaterloggedBlock {
    public MixinLandMineBlock(Properties p_49795_) {
        super(p_49795_);
    }

    /**
     * @author LogicalMaximus
     * @reason So They Avoid Mines
     */
    @Overwrite(remap = false)
    public @Nullable BlockPathTypes getBlockPathType(BlockState state, BlockGetter level, BlockPos pos, @Nullable Mob mob) {
        return BlockPathTypes.DAMAGE_OTHER;
    }
}
