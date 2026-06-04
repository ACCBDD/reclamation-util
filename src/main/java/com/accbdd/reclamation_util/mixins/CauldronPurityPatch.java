package com.accbdd.reclamation_util.mixins;

import dev.ghen.thirst.content.purity.WaterPurity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(CauldronBlock.class)
public class CauldronPurityPatch {
    @Redirect(method = "handlePrecipitation", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;setBlockAndUpdate(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Z", ordinal = 0))
    public boolean rainPatch(Level instance, BlockPos pPos, BlockState pState) {
        BlockState newState = pState.setValue(WaterPurity.BLOCK_PURITY, 1);
        return instance.setBlockAndUpdate(pPos, newState);
    }

    @Redirect(method = "receiveStalactiteDrip", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;setBlockAndUpdate(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Z", ordinal = 0))
    public boolean dripPatch(Level instance, BlockPos pPos, BlockState pState) {
        BlockState newState = pState.setValue(WaterPurity.BLOCK_PURITY, 3);
        return instance.setBlockAndUpdate(pPos, newState);
    }
}
