package com.accbdd.reclamation_util.block;

import com.accbdd.reclamation_util.register.Items;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.phys.BlockHitResult;

public class FlimsyDoor extends DoorBlock {
    public FlimsyDoor(Properties pProperties, BlockSetType pType) {
        super(pProperties, pType);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide() && pLevel.getRandom().nextFloat() < 0.1f) {
            pLevel.setBlock(pPos, Blocks.AIR.defaultBlockState(), 3);
            pLevel.playSound(null, pPos, SoundEvents.ITEM_BREAK, SoundSource.BLOCKS);
            return InteractionResult.CONSUME;
        }

        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }
}
