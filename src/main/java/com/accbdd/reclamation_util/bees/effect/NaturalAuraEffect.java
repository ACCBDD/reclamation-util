package com.accbdd.reclamation_util.bees.effect;

import com.accbdd.complicated_bees.bees.effect.BeeEffect;
import de.ellpeck.naturesaura.api.aura.chunk.IAuraChunk;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class NaturalAuraEffect extends BeeEffect {
    @Override
    public void runEffect(BlockEntity apiary, ItemStack queen, int cycleProgress) {
        if (apiary.getLevel() == null) return;

        if (cycleProgress == 0) {
            int amount = 2000;
            BlockPos blockPos = apiary.getBlockPos();
            Level level = apiary.getLevel();
            int aura = IAuraChunk.getAuraInArea(level, blockPos, 35);
            level.playSound(null, blockPos, SoundEvents.AMETHYST_BLOCK_RESONATE, SoundSource.BLOCKS);
            if (level instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER, blockPos.getX(), blockPos.getY(), blockPos.getZ(), 10, 1, 1, 1, 0);
            }
            while (amount > 0 & aura + amount <= 2000000) {
                BlockPos spot = IAuraChunk.getLowestSpot(level, blockPos, 35, blockPos);
                amount -= IAuraChunk.getAuraChunk(level, spot).storeAura(spot, amount);
            }
        }
    }
}
