package com.accbdd.reclamation_util.mixins;

import com.accbdd.reclamation_util.compat.ICauldronTankHolder;
import net.favouriteless.enchanted.common.blocks.entity.CauldronBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CauldronBlockEntity.class)
public class CauldronFluidHandlerPatch implements ICauldronTankHolder {
    @Unique
    private FluidTank reclamation_util$tank;

    @Unique
    private LazyOptional<IFluidHandler> reclamation_util$fluidHandler;

    @Shadow
    private int fluidAmount;
    @Shadow
    protected ItemStack itemOut;

    @Override
    public FluidTank reclamation_util$getTank() {
        return this.reclamation_util$tank;
    }

    @Override
    public LazyOptional<IFluidHandler> reclamation_util$getFluidHandler() {
        return this.reclamation_util$fluidHandler;
    }

    @Inject(method = "<init>", at = @At("TAIL"), remap = false)
    public void initializeHandler(BlockEntityType type, BlockPos pos, BlockState state, int bucketCapacity, int cookDuration, CallbackInfo ci) {
        this.reclamation_util$tank = new FluidTank(bucketCapacity * 1000, fluid -> fluid.getFluid().isSame(Fluids.WATER) && this.itemOut.isEmpty()) {
            @Override
            public @NotNull FluidStack drain(int maxDrain, FluidAction action) {
                return FluidStack.EMPTY;
            }

            @Override
            public int fill(FluidStack resource, FluidAction action) {
                if (resource.isEmpty() || !isFluidValid(resource)) {
                    return 0;
                }
                if (action.simulate()) {
                    if (fluid.isEmpty()) {
                        return Math.min(capacity, resource.getAmount());
                    }
                    if (!fluid.getFluid().isSame(resource.getFluid())) {
                        return 0;
                    }
                    return Math.min(capacity - fluid.getAmount(), resource.getAmount());
                }
                if (fluid.isEmpty()) {
                    fluid = new FluidStack(resource.getFluid(), Math.min(capacity, resource.getAmount()));
                    onContentsChanged();
                    return fluid.getAmount();
                }
                if (!fluid.getFluid().isSame(resource.getFluid())) {
                    return 0;
                }
                int filled = capacity - fluid.getAmount();

                if (resource.getAmount() < filled) {
                    fluid.grow(resource.getAmount());
                    filled = resource.getAmount();
                } else {
                    fluid.setAmount(capacity);
                }
                if (filled > 0)
                    onContentsChanged();
                return filled;
            }

            @Override
            protected void onContentsChanged() {
                CauldronFluidHandlerPatch.this.fluidAmount = this.getFluidAmount();
                CauldronFluidHandlerPatch.this.reclamation_util$setChanged();
            }
        };
        this.reclamation_util$fluidHandler = LazyOptional.of(() -> reclamation_util$tank);
    }

    @Unique
    private void reclamation_util$setChanged() {
        ((CauldronBlockEntity<?>) (Object) this).setChanged();
    }

    @Inject(method = "setComplete", at = @At("TAIL"), remap = false)
    public void completeClearTank(CallbackInfo ci) {
        this.reclamation_util$tank.setFluid(FluidStack.EMPTY);
        ((IContainerBlockEntityAccessor)this).reclamation_util$getInventory().clear();
        ((IContainerBlockEntityAccessor)this).reclamation_util$getInventory().add(this.itemOut);
    }

    @Inject(method = "takeContents", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;shrink(I)V"), remap = false)
    public void shrinkInventory(Player player, CallbackInfo ci) {
        var item = ((IContainerBlockEntityAccessor)this).reclamation_util$getInventory().get(0);
        item.shrink(1);
        if (item.isEmpty()) {
            ((IContainerBlockEntityAccessor)this).reclamation_util$getInventory().clear();
        }
    }

    @Inject(method = "saveBase", at = @At("HEAD"), remap = false)
    public void saveFluidHandler(CompoundTag nbt, CallbackInfo ci) {
        this.reclamation_util$tank.writeToNBT(nbt);
    }

    @Inject(method = "loadBase", at = @At("HEAD"), remap = false)
    public void loadFluidHandler(CompoundTag nbt, CallbackInfo ci) {
        this.reclamation_util$tank.readFromNBT(nbt);
    }

    @Inject(method = "removeWater", at = @At(value = "INVOKE", target = "Lnet/favouriteless/enchanted/common/blocks/entity/CauldronBlockEntity;setChanged()V"), remap = false)
    public void removeFluidHandler(int amount, CallbackInfoReturnable<Boolean> cir) {
        this.reclamation_util$tank.getFluid().shrink(amount);
    }

    @Inject(method = "takeContents", at = @At(value = "INVOKE", target = "Lnet/favouriteless/enchanted/common/blocks/entity/CauldronBlockEntity;setWater(I)V"), remap = false)
    public void clearWaterFromFailed(Player player, CallbackInfo ci) {
        this.reclamation_util$tank.setFluid(FluidStack.EMPTY);
    }

    @Inject(method = "addWater", at = @At(value = "INVOKE", target = "Lnet/favouriteless/enchanted/common/blocks/entity/CauldronBlockEntity;setChanged()V"), remap = false)
    public void syncAddedWater(int amount, CallbackInfoReturnable<Boolean> cir) {
        this.reclamation_util$tank.fill(new FluidStack(Fluids.WATER, amount), IFluidHandler.FluidAction.EXECUTE);
    }
}
