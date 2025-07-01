package com.accbdd.reclamation_util.mixins;

import com.agricraft.agricraft.common.block.entity.CropBlockEntity;
import com.blakebr0.mysticalagriculture.tileentity.HarvesterTileEntity;
import com.blakebr0.mysticalagriculture.util.MachineUpgradeTier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(HarvesterTileEntity.class)
public abstract class MystAgriHarvesterPatch {
    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;getBlock()Lnet/minecraft/world/level/block/Block;", ordinal = 0), locals = LocalCapture.CAPTURE_FAILHARD)
    private static void onTick(Level level, BlockPos pos, BlockState state, HarvesterTileEntity tile, CallbackInfo ci, MachineUpgradeTier tier, Direction direction, boolean isDisabled, int operationTime, BlockPos nextPos, BlockState cropState) {
        if (level.getBlockEntity(nextPos) instanceof CropBlockEntity crop) {
            if (crop.canBeHarvested()) {
                crop.getHarvestProducts(stack -> ((MystAgriHarvesterInvoker)tile).invokeAddItemToInventory(stack, level, nextPos));
                crop.setGrowthStage(crop.getPlant().getGrowthStageAfterHarvest());
                tile.getEnergy().extractEnergy(tile.getFuelUsage(), false);
            } else {
                tile.getEnergy().extractEnergy(10, false);
            }
        }
    }


}
