package com.accbdd.reclamation_util.mixins;

import com.klikli_dev.theurgy.content.apparatus.digestionvat.DigestionCachedCheck;
import net.minecraftforge.fluids.FluidStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(DigestionCachedCheck.class)
public class DigestionCachedPatch {
    @ModifyArg(method = "getRecipeFor(Lnet/minecraftforge/fluids/FluidStack;Lnet/minecraft/world/level/Level;)Ljava/util/Optional;", at = @At(value = "INVOKE", target = "Lcom/klikli_dev/theurgy/content/apparatus/digestionvat/DigestionCachedCheck;getRecipeFor(Lnet/minecraftforge/fluids/FluidStack;Lnet/minecraft/world/level/Level;Lnet/minecraft/resources/ResourceLocation;)Ljava/util/Optional;"), remap = false)
    public FluidStack stripPurity(FluidStack stack) {
        return new FluidStack(stack.getFluid(), stack.getAmount());
    }
}
