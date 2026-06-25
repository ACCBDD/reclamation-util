package com.accbdd.reclamation_util.mixins;

import dev.ghen.thirst.content.purity.WaterPurity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.fluids.FluidStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vazkii.botania.forge.xplat.ForgeXplatImpl;

@Mixin(ForgeXplatImpl.class)
public class BotaniaCauldronPatch {
    @ModifyArg(method = "fillItemWithWater", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/fluids/capability/IFluidHandlerItem;fill(Lnet/minecraftforge/fluids/FluidStack;Lnet/minecraftforge/fluids/capability/IFluidHandler$FluidAction;)I"), remap = false)
    public FluidStack addPurity(FluidStack passed) {
        return WaterPurity.addPurity(passed, 1);
    }

    @Inject(method = "fillItemWithWater", at = @At(value = "RETURN", ordinal = 1), remap = false, cancellable = true)
    public void addBottlePurity(ItemStack stackToFill, Player player, CallbackInfoReturnable<ItemStack> cir) {
        cir.setReturnValue(WaterPurity.addPurity(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.WATER), 1));
    }
}
