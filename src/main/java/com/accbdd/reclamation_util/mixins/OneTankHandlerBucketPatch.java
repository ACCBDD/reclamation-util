package com.accbdd.reclamation_util.mixins;

import com.klikli_dev.theurgy.content.behaviour.fluidhandler.OneTankFluidHandlerBehaviour;
import net.minecraft.world.item.BucketItem;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(OneTankFluidHandlerBehaviour.class)
public class OneTankHandlerBucketPatch {
    @Redirect(method = "useFluidHandler", remap = false, at = @At(value = "INVOKE", target = "Lnet/minecraftforge/fluids/capability/IFluidHandlerItem;drain(ILnet/minecraftforge/fluids/capability/IFluidHandler$FluidAction;)Lnet/minecraftforge/fluids/FluidStack;", ordinal = 1))
    public FluidStack emptyBucketAlways(IFluidHandlerItem fluidHandlerItem, int amountFilled, IFluidHandler.FluidAction fluidAction) {
        if (fluidHandlerItem.getContainer().getItem() instanceof BucketItem) {
            return fluidHandlerItem.drain(1000, fluidAction);
        }
        return fluidHandlerItem.drain(amountFilled, fluidAction);
    }
}
