package com.accbdd.reclamation_util.mixins;

import com.accbdd.reclamation_util.compat.PreventExtractWrapper;
import com.klikli_dev.theurgy.content.apparatus.digestionvat.DigestionStorageBehaviour;
import com.klikli_dev.theurgy.content.behaviour.StorageBehaviour;
import com.klikli_dev.theurgy.content.storage.PreventInsertWrapper;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Supplier;

@Mixin(DigestionStorageBehaviour.class)
public abstract class DigestionStoragePatch extends StorageBehaviour<DigestionStoragePatch> {
    @Shadow
    public LazyOptional<IItemHandler> inventoryCapability;
    @Shadow
    public ItemStackHandler inputInventory;
    @Shadow
    public LazyOptional<IItemHandler> outputInventoryExtractOnlyCapability;
    @Shadow
    public ItemStackHandler outputInventory;
    @Shadow
    public LazyOptional<IItemHandler> inventoryReadOnlyCapability;
    @Unique
    IItemHandler reclamation_util$automationHandler;
    @Unique
    private LazyOptional<IItemHandler> reclamation_util$automationCapability;

    public DigestionStoragePatch(BlockEntity blockEntity) {
        super(blockEntity);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void addPreventExtractWrapper(BlockEntity blockEntity, Supplier craftingBehaviour, CallbackInfo ci) {
        this.reclamation_util$automationHandler = new CombinedInvWrapper(new PreventExtractWrapper(inputInventory), new PreventInsertWrapper(outputInventory));
        this.reclamation_util$automationCapability = LazyOptional.of(() -> this.reclamation_util$automationHandler);
        this.register(this.reclamation_util$automationCapability);
    }

    @Inject(method = "getCapability", at = @At(value = "HEAD"), cancellable = true, remap = false)
    private <T> void omniItemCapability(@NotNull Capability<T> cap, @Nullable Direction side, CallbackInfoReturnable<LazyOptional<T>> cir) {
        Boolean isOpen = this.blockEntity.getBlockState().getValue(BlockStateProperties.OPEN);
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            if (isOpen) {
                if (side == null) {
                    cir.setReturnValue(this.inventoryCapability.cast());
                } else {
                    cir.setReturnValue(this.reclamation_util$automationCapability.cast());
                }
            } else {
                cir.setReturnValue(this.inventoryReadOnlyCapability.cast());
            }
        }
    }
}
