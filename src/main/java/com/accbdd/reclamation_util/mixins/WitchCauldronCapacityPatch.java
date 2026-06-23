package com.accbdd.reclamation_util.mixins;

import net.favouriteless.enchanted.common.blocks.entity.WitchCauldronBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(WitchCauldronBlockEntity.class)
public class WitchCauldronCapacityPatch {
    @ModifyConstant(method = "<init>", constant = @Constant(intValue = 3), remap = false)
    private static int modifyBucketCount(int constant) {
        return 1;
    }

}
