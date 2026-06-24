package com.accbdd.reclamation_util.mixins;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BoneMealItem.class)
public class DisableWaterPlantPatch {
    @Inject(method = "growWaterPlant", at = @At("HEAD"), cancellable = true)
    private static void disable(ItemStack pStack, Level pLevel, BlockPos pPos, Direction pClickedSide, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(false);
    }
}
