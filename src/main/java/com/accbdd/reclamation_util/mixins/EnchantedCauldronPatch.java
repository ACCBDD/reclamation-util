package com.accbdd.reclamation_util.mixins;

import net.favouriteless.enchanted.common.blocks.entity.CauldronBlockEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CauldronBlockEntity.class)
public abstract class EnchantedCauldronPatch {
    @Shadow(remap = false)
    private int fluidAmount;

    @Inject(method = "takeContents", at = @At("TAIL"), remap = false)
    public void mixinTakeContents(Player player, CallbackInfo clr) {
        if (fluidAmount < 0)
            setWater(0);
    }

    @Shadow(remap = false)
    public abstract void setWater(int amount);
}
