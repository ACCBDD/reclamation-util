package com.accbdd.reclamation_util.item;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import vazkii.botania.common.item.equipment.tool.manasteel.ManasteelShovelItem;

import java.util.List;

public class ManasteelExcavatorItem extends ManasteelShovelItem implements IAreaBreakItem {
    public ManasteelExcavatorItem(Properties props) {
        super(props);
    }

    @Override
    public float getDestroySpeed(ItemStack pStack, BlockState pState) {
        return super.getDestroySpeed(pStack, pState) * 0.65f;
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return super.getMaxDamage(stack) * 3;
    }

    @Override
    public List<BlockPos> getBlocksToDestroy(BlockPos initial, Player player) {
        return AreaBreakItem.getBlocksToBeDestroyed(1, 1, initial, player);
    }
}
