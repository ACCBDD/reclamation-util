package com.accbdd.reclamation_util.mixins;

import alexthw.ars_elemental.common.blocks.EverfullUrnTile;
import dev.ghen.thirst.content.purity.WaterPurity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(EverfullUrnTile.class)
public class EverfullUrnPatch {
    @ModifyArg(method = "tryRefill", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;setBlockAndUpdate(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Z"), remap = false, index = 1)
    public BlockState addCauldronPurity(BlockState pState) {
        return pState.setValue(WaterPurity.BLOCK_PURITY, 3);
    }

    @ModifyArg(method = "tryRefill", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/fluids/capability/IFluidHandler;fill(Lnet/minecraftforge/fluids/FluidStack;Lnet/minecraftforge/fluids/capability/IFluidHandler$FluidAction;)I"), remap = false)
    public FluidStack addStackPurity(FluidStack resource) {
        return WaterPurity.addPurity(resource, 2);
    }
}
