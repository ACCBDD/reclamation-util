package com.accbdd.reclamation_util.item;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public interface IAreaBreakItem {
    List<BlockPos> getBlocksToDestroy(BlockPos initial, Player player);
}
