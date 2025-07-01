package com.accbdd.reclamation_util.mixins;

import de.maxhenkel.gravestone.events.DeathEvents;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(DeathEvents.class)
public class GravestoneMixin {

    @ModifyArg(method = "playerDeath", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;setBlockAndUpdate(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Z", ordinal = 1), index = 1)
    private BlockState clay(BlockState state) {
        return Blocks.CLAY.defaultBlockState();
    }
}
