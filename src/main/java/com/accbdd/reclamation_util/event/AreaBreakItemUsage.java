package com.accbdd.reclamation_util.event;

import com.accbdd.reclamation_util.item.AreaBreakItem;
import com.accbdd.reclamation_util.item.IAreaBreakItem;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.level.BlockEvent;

import java.util.HashSet;
import java.util.Set;

public class AreaBreakItemUsage {
    private static final Set<BlockPos> HARVESTED_BLOCKS = new HashSet<>();

    public static void onAreaBreakItemUsage(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        ItemStack mainHandItem = player.getMainHandItem();

        if (mainHandItem.getItem() instanceof DiggerItem areaBreakTool && mainHandItem.getItem() instanceof IAreaBreakItem && player instanceof ServerPlayer serverPlayer) {
            BlockPos initial = event.getPos();
            if (HARVESTED_BLOCKS.contains(initial) || serverPlayer.isCrouching()) {
                return;
            }

            for (BlockPos pos : AreaBreakItem.getBlocksToBeDestroyed(1, initial, serverPlayer)) {
                if(pos.equals(initial) || !areaBreakTool.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
                    continue;
                }

                HARVESTED_BLOCKS.add(pos);
                serverPlayer.gameMode.destroyBlock(pos);
                HARVESTED_BLOCKS.remove(pos);
            }
        }
    }
}
