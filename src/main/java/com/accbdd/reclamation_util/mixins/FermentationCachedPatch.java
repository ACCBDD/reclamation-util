package com.accbdd.reclamation_util.mixins;

import com.klikli_dev.theurgy.content.apparatus.fermentationvat.FermentationCachedCheck;
import net.minecraftforge.fluids.FluidStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(FermentationCachedCheck.class)
public class FermentationCachedPatch {
    @ModifyArg(method = "getRecipeFor(Lnet/minecraftforge/fluids/FluidStack;Lnet/minecraft/world/level/Level;)Ljava/util/Optional;", at = @At(value = "INVOKE", target = "Lcom/klikli_dev/theurgy/content/apparatus/fermentationvat/FermentationCachedCheck;getRecipeFor(Lnet/minecraftforge/fluids/FluidStack;Lnet/minecraft/world/level/Level;Lnet/minecraft/resources/ResourceLocation;)Ljava/util/Optional;"), remap = false)
    public FluidStack stripPurity(FluidStack stack) {
        return new FluidStack(stack.getFluid(), stack.getAmount());
    }
}
