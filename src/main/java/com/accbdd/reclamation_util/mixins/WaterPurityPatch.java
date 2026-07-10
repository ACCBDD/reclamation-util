package com.accbdd.reclamation_util.mixins;

import dev.ghen.thirst.content.purity.WaterPurity;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WaterPurity.class)
public class WaterPurityPatch {

    @Inject(method = "harvestRunningWater", at = @At("HEAD"), cancellable = true, remap = false)
    private static void scarcity$cancelFlowingWaterBottleFill(PlayerInteractEvent.RightClickItem event, CallbackInfo ci) {
        if (event.getItemStack().getItem() != Items.GLASS_BOTTLE) {
            return;
        }
        ci.cancel();
    }
}
