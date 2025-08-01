package com.accbdd.reclamation_util.item;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Tier;

public class HammerItem extends AreaBreakItem {
    public HammerItem(Tier pTier, float pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pAttackDamageModifier, pAttackSpeedModifier, pTier, BlockTags.MINEABLE_WITH_PICKAXE, pProperties);
    }
}
