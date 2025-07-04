package com.accbdd.reclamation_util.mixins;

import com.agricraft.agricraft.common.block.CropBlock;
import com.agricraft.agricraft.common.block.entity.CropBlockEntity;
import com.agricraft.agricraft.common.forge.block.entity.ForgeCropBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CropBlock.class)
public class HornHarvestablePatch { //thank you, @PiotrO15!
    @Inject(method = "newBlockEntity", at = @At("RETURN"), cancellable = true)
    private void injectNewBlockEntity(BlockPos pos, BlockState state, CallbackInfoReturnable<CropBlockEntity> cir) {
        cir.setReturnValue(new ForgeCropBlockEntity(pos, state));
    }
}
