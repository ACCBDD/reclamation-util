package com.accbdd.reclamation_util.item;

import mekanism.tools.common.ToolsTags;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.TierSortingRegistry;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import org.jetbrains.annotations.NotNull;
import vazkii.botania.api.item.SortableTool;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.item.equipment.CustomDamageItem;
import vazkii.botania.common.item.equipment.tool.ToolCommons;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;
import java.util.function.Consumer;

public class ManasteelPaxelItem extends AxeItem implements CustomDamageItem, SortableTool {
    private static final int MANA_PER_DAMAGE = 60;
    private static final ToolAction PAXEL_DIG = ToolAction.get("paxel_dig");
    private static final Set<ToolAction> PAXEL_ACTIONS = Collections.newSetFromMap(new IdentityHashMap<>());

    static {
        PAXEL_ACTIONS.add(PAXEL_DIG);
        PAXEL_ACTIONS.addAll(ToolActions.DEFAULT_PICKAXE_ACTIONS);
        PAXEL_ACTIONS.addAll(ToolActions.DEFAULT_SHOVEL_ACTIONS);
        PAXEL_ACTIONS.addAll(ToolActions.DEFAULT_AXE_ACTIONS);
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return super.getMaxDamage(stack) * 2;
    }

    public ManasteelPaxelItem(Tier mat, float attackDamage, float attackSpeed, Item.Properties props) {
        super(mat, attackDamage, attackSpeed, props);
    }

    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
        return ToolCommons.damageItemIfPossible(stack, amount, entity, MANA_PER_DAMAGE);
    }

    public int getManaPerDamage() {
        return 60;
    }

    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
        if (!world.isClientSide && entity instanceof Player player) {
            if (stack.getDamageValue() > 0 && ManaItemHandler.instance().requestManaExactForTool(stack, player, this.getManaPerDamage() * 2, true)) {
                stack.setDamageValue(stack.getDamageValue() - 1);
            }
        }
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
        return PAXEL_ACTIONS.contains(toolAction);
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

    public int getSortingPriority(ItemStack stack, BlockState state) {
        return ToolCommons.getToolPriority(stack);
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
