package com.accbdd.reclamation_util.mixins;

import com.agricraft.agricraft.common.block.entity.CropBlockEntity;
import com.simibubi.create.api.behaviour.movement.MovementBehaviour;
import com.simibubi.create.content.contraptions.actors.harvester.HarvesterMovementBehaviour;
import com.simibubi.create.content.contraptions.behaviour.MovementContext;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(HarvesterMovementBehaviour.class)
public abstract class CreateHarvesterPatch implements MovementBehaviour {
    @Inject(method = "visitNewPosition", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getBlockState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;", ordinal = 0), locals = LocalCapture.CAPTURE_FAILHARD)
    public void onVisitNewPosition(MovementContext context, BlockPos pos, CallbackInfo ci, Level world) {
        if (world.getBlockEntity(pos) instanceof CropBlockEntity crop) {
            if (crop.canBeHarvested()) {
                crop.getHarvestProducts(stack -> dropItem(context, stack));
                crop.setGrowthStage(crop.getPlant().getGrowthStageAfterHarvest());
            }
        }
    }
}
