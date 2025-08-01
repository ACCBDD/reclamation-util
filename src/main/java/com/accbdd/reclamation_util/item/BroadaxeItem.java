package com.accbdd.reclamation_util.item;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Tier;

import java.util.List;

public class BroadaxeItem extends AreaBreakItem {
    public BroadaxeItem(Tier pTier, float pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pAttackDamageModifier, pAttackSpeedModifier, pTier, BlockTags.MINEABLE_WITH_AXE, pProperties);
    }

    @Override
    public List<BlockPos> getBlocksToDestroy(BlockPos initial, Player player) {
        return AreaBreakItem.getBlocksToBeDestroyed(1, 3, initial, player);
    }
}
