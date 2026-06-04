package com.accbdd.reclamation_util.mixins;

import com.simibubi.create.content.fluids.pipes.VanillaFluidTargets;
import dev.ghen.thirst.content.purity.WaterPurity;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(VanillaFluidTargets.class)
public class CreateFluidExtractPatch {
    @Inject(method = "drainBlock", at = @At(value = "RETURN", ordinal = 3), cancellable = true, remap = false)
    private static void addPurityToWater(Level level, BlockPos pos, BlockState state, boolean simulate, CallbackInfoReturnable<FluidStack> cir) {
        FluidStack purityAdded = WaterPurity.addPurity(new FluidStack(Fluids.WATER, 1000), Mth.clamp(state.getValue(WaterPurity.BLOCK_PURITY) - 1, 0, 3));
        cir.setReturnValue(purityAdded);
    }
}
