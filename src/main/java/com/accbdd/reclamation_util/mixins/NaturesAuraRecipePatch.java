package com.accbdd.reclamation_util.mixins;

import de.ellpeck.naturesaura.compat.jei.AnimalSpawnerCategory;
import de.ellpeck.naturesaura.recipes.AnimalSpawnerRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraftforge.common.ForgeSpawnEggItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;

@Mixin(AnimalSpawnerCategory.class)
public class NaturesAuraRecipePatch {

    @Inject(method = "setRecipe(Lmezz/jei/api/gui/builder/IRecipeLayoutBuilder;Lde/ellpeck/naturesaura/recipes/AnimalSpawnerRecipe;Lmezz/jei/api/recipe/IFocusGroup;)V", cancellable = true, at = @At("HEAD"), remap = false)
    public void onSetRecipe(IRecipeLayoutBuilder builder, AnimalSpawnerRecipe recipe, IFocusGroup focuses, CallbackInfo ci) {
        if (ForgeSpawnEggItem.fromEntityType(recipe.entity) == null) {
            for (int i = 0; i < recipe.ingredients.length; ++i) {
                builder.addSlot(RecipeIngredientRole.INPUT, i * 18 + 1, 69).addItemStacks(Arrays.asList(recipe.ingredients[i].getItems()));
            }

            ci.cancel();
        }
    }
}
