package com.accbdd.reclamation_util.item;

import com.accbdd.reclamation_util.register.Items;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SculkChargeParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;

public class SculkAwakenerItem extends Item {
    public SculkAwakenerItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public boolean isFoil(ItemStack pStack) {
        return true;
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();
        BlockPos clickedPos = pContext.getClickedPos();
        BlockState state = level.getBlockState(clickedPos);
        if (state.is(Blocks.SCULK_SHRIEKER) && !state.getValue(BlockStateProperties.CAN_SUMMON)) {
            if (!level.isClientSide() && level instanceof ServerLevel serverLevel) {
                serverLevel.setBlock(clickedPos, state.setValue(BlockStateProperties.CAN_SUMMON, true), 3);
                Vec3 center = clickedPos.getCenter();
                serverLevel.sendParticles(new SculkChargeParticleOptions(0), center.x, center.y+0.25, center.z, 5, 0.5, 0, 0.5, 0);
                serverLevel.playSound(null, clickedPos, SoundEvents.SPLASH_POTION_BREAK, SoundSource.BLOCKS);
                serverLevel.playSound(null, clickedPos, SoundEvents.SCULK_CLICKING, SoundSource.BLOCKS);
                if (pContext.getPlayer() != null)
                    pContext.getPlayer().setItemInHand(pContext.getHand(), new ItemStack(Items.SCULK_AWAKENER.get(), pContext.getItemInHand().getCount() - 1));
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return InteractionResult.FAIL;
    }
}
