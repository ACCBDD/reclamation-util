package com.accbdd.reclamation_util.item;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import vazkii.botania.common.item.equipment.tool.manasteel.ManasteelPickaxeItem;

public class ManasteelHammerItem extends ManasteelPickaxeItem implements IAreaBreakItem {
    public ManasteelHammerItem(Properties props) {
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
}
