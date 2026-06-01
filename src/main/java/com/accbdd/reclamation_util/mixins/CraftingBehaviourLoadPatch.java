package com.accbdd.reclamation_util.mixins;

import com.klikli_dev.theurgy.content.behaviour.CraftingBehaviour;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CraftingBehaviour.class)
public abstract class CraftingBehaviourLoadPatch<W extends RecipeWrapper, R extends Recipe<W>, C extends RecipeManager.CachedCheck<W, R>> {
    @Shadow protected abstract int getTotalTime();

    @Shadow protected int progress;

    @Shadow protected int totalTime;

    @Shadow protected abstract boolean craft(R pRecipe);

    @Shadow protected abstract void sendBlockUpdated();

    @Shadow protected boolean isProcessing;

    @Shadow protected abstract void tryStartProcessing();

    /**
     * @author ACCBDD
     * @reason fix load behavior instantly finishing a recipe
     */
    @Overwrite(remap = false)
    protected void tryFinishProcessing(R pRecipe) {
        if (this.progress >= this.getTotalTime()) {
            this.progress = 0;
            this.totalTime = this.getTotalTime();
            this.craft(pRecipe);
            this.sendBlockUpdated();
        }
    }

    @Inject(method = "load", at = @At(value = "TAIL"), remap = false)
    public void loadProcessing(CompoundTag pTag, CallbackInfo ci) {
        if (this.progress > 0) {
            this.isProcessing = true;
        }
    }
}
