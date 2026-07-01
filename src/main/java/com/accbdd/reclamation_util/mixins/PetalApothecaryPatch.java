package com.accbdd.reclamation_util.mixins;

import dev.ghen.thirst.content.purity.WaterPurity;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import vazkii.botania.forge.xplat.ForgeXplatImpl;

@Mixin(ForgeXplatImpl.class)
public class PetalApothecaryPatch {
    @ModifyVariable(method = "insertFluidIntoPlayerItem", at = @At(value = "STORE"), ordinal = 0, remap = false)
    public FluidStack addPurity(FluidStack original) {
        if (original.getFluid().isSame(Fluids.WATER)) {
            return WaterPurity.addPurity(original, 0);
        }
        return original;
    }
}
