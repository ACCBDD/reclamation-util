package com.accbdd.reclamation_util.mixins;

import dev.ghen.thirst.content.purity.WaterPurity;
import dev.ghen.thirst.foundation.config.CommonConfig;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = AbstractCookingRecipe.class, priority = 500)
public class ContainerNoTagPatch {
    @Shadow @Final protected Ingredient ingredient;

    /**
     * @author ACCBDD
     * @reason fix dumb thirst was taken logic
     */
    @Inject(method = "matches", at = @At("HEAD"), cancellable = true)
    public void matches(Container container, Level level, CallbackInfoReturnable<Boolean> cir) {
        ItemStack itemStack = container.getItem(0);
        if (WaterPurity.isWaterFilledContainer(itemStack) && (!itemStack.hasTag() || !itemStack.getTag().contains("Purity"))) {
            ItemStack matched = itemStack.copy();
            CompoundTag tag = matched.getOrCreateTag();
            tag.putInt("Purity", CommonConfig.DEFAULT_PURITY.get());
            cir.setReturnValue(this.ingredient.test(matched));
        } else {
            cir.setReturnValue(this.ingredient.test(itemStack));
        }
    }
}
