package com.accbdd.reclamation_util.mixins;

import net.favouriteless.enchanted.common.blocks.entity.ContainerBlockEntityBase;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ContainerBlockEntityBase.class)
public interface IContainerBlockEntityAccessor {
    @Accessor("inventory")
    NonNullList<ItemStack> reclamation_util$getInventory();
}
