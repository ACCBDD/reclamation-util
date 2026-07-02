package com.accbdd.reclamation_util.bees.effect;

import com.accbdd.complicated_bees.bees.effect.BeeEffect;
import com.accbdd.complicated_bees.util.BlockPosBoxIterator;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public class ConversionEffect extends BeeEffect {
    private final BlockState convertFrom;
    private final BlockState convertTo;

    public ConversionEffect(BlockState convertFrom, BlockState convertTo) {
        this.convertFrom = convertFrom;
        this.convertTo = convertTo;
    }

    @Override
    public void runEffect(BlockEntity apiary, ItemStack queen, int cycleProgress) {
        if (apiary.getLevel() != null) {
            if (cycleProgress == 0) {
                Level level = apiary.getLevel();
                if (level != null) {
                    BlockPosBoxIterator iterator = this.getBlockIterator(apiary, queen);
                    List<BlockPos> convertables = new ArrayList();

                    while (iterator.hasNext()) {
                        BlockPos pos = iterator.next();
                        BlockState state = level.getBlockState(pos);
                        if (state.equals(convertFrom)) {
                            convertables.add(pos);
                        }
                    }

                    if ((double) level.random.nextFloat() < 0.1 || !convertables.isEmpty()) {
                        BlockPos switchPos = convertables.get(level.random.nextInt(convertables.size()));
                        level.setBlockAndUpdate(switchPos, convertTo);
                    }
                }
            }
        }
    }
}
