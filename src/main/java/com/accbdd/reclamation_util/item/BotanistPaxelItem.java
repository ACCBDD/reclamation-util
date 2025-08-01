package com.accbdd.reclamation_util.item;

import de.ellpeck.naturesaura.Helper;
import mekanism.tools.common.ToolsTags;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Set;

public class BotanistPaxelItem extends DiggerItem {
    private static final ToolAction PAXEL_DIG = ToolAction.get("paxel_dig");
    private static final Set<ToolAction> PAXEL_ACTIONS = Collections.newSetFromMap(new IdentityHashMap<>());

    static {
        PAXEL_ACTIONS.add(PAXEL_DIG);
        PAXEL_ACTIONS.addAll(ToolActions.DEFAULT_PICKAXE_ACTIONS);
        PAXEL_ACTIONS.addAll(ToolActions.DEFAULT_SHOVEL_ACTIONS);
        PAXEL_ACTIONS.addAll(ToolActions.DEFAULT_AXE_ACTIONS);
    }

    public BotanistPaxelItem(float pAttackDamageModifier, float pAttackSpeedModifier, Tier pTier, Properties pProperties) {
        super(pAttackDamageModifier, pAttackSpeedModifier, pTier, ToolsTags.Blocks.MINEABLE_WITH_PAXEL, pProperties);
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return super.getMaxDamage(stack) * 2;
    }

    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return Helper.makeRechargeProvider(stack, true);
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
        return PAXEL_ACTIONS.contains(toolAction);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        pTooltipComponents.add(Component.literal("Does not have special botanist abilities!").withStyle(ChatFormatting.GRAY));
    }
}
