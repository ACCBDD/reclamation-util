package com.accbdd.reclamation_util.mixins;

import com.blakebr0.mysticalagriculture.tileentity.HarvesterTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(HarvesterTileEntity.class)
public interface MystAgriHarvesterInvoker {
    @Invoker("addItemToInventory")
    void invokeAddItemToInventory(ItemStack stack, Level level, BlockPos pos);
}
