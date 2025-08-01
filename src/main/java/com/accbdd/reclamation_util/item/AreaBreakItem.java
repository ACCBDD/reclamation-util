package com.accbdd.reclamation_util.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.ArrayList;
import java.util.List;

public class AreaBreakItem extends DiggerItem implements IAreaBreakItem {
    public AreaBreakItem(float pAttackDamageModifier, float pAttackSpeedModifier, Tier pTier, TagKey<Block> pBlocks, Properties pProperties) {
        super(pAttackDamageModifier, pAttackSpeedModifier, pTier, pBlocks, pProperties);
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return super.getMaxDamage(stack) * 3;
    }

    @Override
    public float getDestroySpeed(ItemStack pStack, BlockState pState) {
        return super.getDestroySpeed(pStack, pState) * 0.65f;
    }

    public static List<BlockPos> getBlocksToBeDestroyed(int range, int depth, BlockPos initial, Player player) {
        List<BlockPos> positions = new ArrayList<>();

        BlockHitResult traceResult = player.level().clip(new ClipContext(player.getEyePosition(1f),
                (player.getEyePosition(1f).add(player.getViewVector(1f).scale(6f))),
                ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));
        if (traceResult.getType() == HitResult.Type.MISS) {
            return positions;
        }

        if (traceResult.getDirection().getAxis() == Direction.Axis.Y) {
            for (int y = 0; y < depth; y++) {
                for (int x = -range; x <= range; x++) {
                    for (int z = -range; z <= range; z++) {
                        positions.add(new BlockPos(initial.getX() + x, initial.getY() + y * (traceResult.getDirection() == Direction.UP ? 1 : -1), initial.getZ() + z));
                    }
                }
            }
        }

        if (traceResult.getDirection().getAxis() == Direction.Axis.Z) {
            for (int z = 0; z < depth; z++) {
                for (int x = -range; x <= range; x++) {
                    for (int y = -range; y <= range; y++) {
                        positions.add(new BlockPos(initial.getX() + x, initial.getY() + y, initial.getZ() + z * (traceResult.getDirection() == Direction.NORTH ? 1 : -1)));
                    }
                }
            }
        }

        if (traceResult.getDirection().getAxis() == Direction.Axis.X) {
            for (int x = 0; x < depth; x++) {
                for (int z = -range; z <= range; z++) {
                    for (int y = -range; y <= range; y++) {
                        positions.add(new BlockPos(initial.getX() + x * (traceResult.getDirection() == Direction.WEST ? 1 : -1), initial.getY() + y, initial.getZ() + z));
                    }
                }
            }
        }
        return positions;
    }

    @Override
    public List<BlockPos> getBlocksToDestroy(BlockPos initial, Player player) {
        return AreaBreakItem.getBlocksToBeDestroyed(1, 1, initial, player);
    }
}
