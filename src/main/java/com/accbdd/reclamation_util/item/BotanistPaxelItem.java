package com.accbdd.reclamation_util.item;

import de.ellpeck.naturesaura.Helper;
import mekanism.tools.common.ToolsTags;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.TierSortingRegistry;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

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

    @Override
    public float getDestroySpeed(ItemStack pStack, BlockState pState) {
        return pState.is(ToolsTags.Blocks.MINEABLE_WITH_PAXEL) ? getTier().getSpeed() : 1.0F;
    }

    @Override
    public boolean isCorrectToolForDrops(BlockState state) {
        return state.is(ToolsTags.Blocks.MINEABLE_WITH_PAXEL) && TierSortingRegistry.isCorrectTierForDrops(this.getTier(), state);
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
        return this.isCorrectToolForDrops(state);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        InteractionResult axeResult = super.useOn(context);
        if (axeResult != InteractionResult.PASS) {
            return axeResult;
        } else {
            Level world = context.getLevel();
            BlockPos blockpos = context.getClickedPos();
            Player player = context.getPlayer();
            BlockState blockstate = world.getBlockState(blockpos);
            BlockState resultToSet = null;
            if (context.getClickedFace() == Direction.DOWN) {
                return InteractionResult.PASS;
            } else {
                BlockState foundResult = blockstate.getToolModifiedState(context, ToolActions.SHOVEL_FLATTEN, false);
                if (foundResult != null && world.isEmptyBlock(blockpos.above())) {
                    world.playSound(player, blockpos, SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS, 1.0F, 1.0F);
                    resultToSet = foundResult;
                } else if (blockstate.getBlock() instanceof CampfireBlock && (Boolean)blockstate.getValue(CampfireBlock.LIT)) {
                    if (!world.isClientSide) {
                        world.levelEvent((Player)null, 1009, blockpos, 0);
                    }

                    CampfireBlock.dowse(player, world, blockpos, blockstate);
                    resultToSet = (BlockState)blockstate.setValue(CampfireBlock.LIT, false);
                }

                if (resultToSet == null) {
                    return InteractionResult.PASS;
                } else {
                    if (!world.isClientSide) {
                        ItemStack stack = context.getItemInHand();
                        if (player instanceof ServerPlayer) {
                            ServerPlayer serverPlayer = (ServerPlayer)player;
                            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(serverPlayer, blockpos, stack);
                        }

                        world.setBlock(blockpos, resultToSet, 11);
                        if (player != null) {
                            stack.hurtAndBreak(1, player, (onBroken) -> {
                                onBroken.broadcastBreakEvent(context.getHand());
                            });
                        }
                    }

                    return InteractionResult.sidedSuccess(world.isClientSide);
                }
            }
        }
    }
}
