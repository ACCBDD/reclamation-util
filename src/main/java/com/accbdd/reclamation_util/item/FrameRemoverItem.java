package com.accbdd.reclamation_util.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class FrameRemoverItem extends Item {
    public FrameRemoverItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public boolean isFoil(ItemStack pStack) {
        return true;
    }

    @Override
    public Rarity getRarity(ItemStack pStack) {
        return Rarity.RARE;
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();
        BlockPos clickedPos = pContext.getClickedPos();
        BlockState state = level.getBlockState(clickedPos);
        if (state.is(Blocks.END_PORTAL_FRAME) || state.is(Blocks.END_PORTAL)) {
            if (!level.isClientSide() && level instanceof ServerLevel serverLevel) {
                serverLevel.setBlock(clickedPos, Blocks.AIR.defaultBlockState(), 3);
                serverLevel.playSound(null, clickedPos, SoundEvents.GLASS_BREAK, SoundSource.BLOCKS);
                Vec3 center = clickedPos.getCenter();
                serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, state), center.x, center.y, center.z, 30, 0.25, 0.25, 0.25, 0);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return InteractionResult.FAIL;
    }
}
