package com.accbdd.reclamation_util.mixins;

import com.klikli_dev.theurgy.content.recipe.DigestionRecipe;
import com.klikli_dev.theurgy.content.recipe.ingredient.FluidIngredient;
import net.minecraftforge.fluids.FluidStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(DigestionRecipe.class)
public class DigestionRecipePatch {
    @Redirect(method = "matches(Lcom/klikli_dev/theurgy/content/recipe/wrapper/RecipeWrapperWithFluid;Lnet/minecraft/world/level/Level;)Z", at = @At(value = "INVOKE", target = "Lcom/klikli_dev/theurgy/content/recipe/ingredient/FluidIngredient;test(Lnet/minecraftforge/fluids/FluidStack;)Z"), remap = false)
    public boolean testIgnoreNbt(FluidIngredient instance, FluidStack stack) {
        return instance.testIgnoreNbt(stack);
    }
}
