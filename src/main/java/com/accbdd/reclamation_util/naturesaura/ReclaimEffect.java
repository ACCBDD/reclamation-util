package com.accbdd.reclamation_util.naturesaura;

import com.accbdd.reclamation_util.datagen.BlockTagGenerator;
import de.ellpeck.naturesaura.api.NaturesAuraAPI;
import de.ellpeck.naturesaura.api.aura.chunk.IAuraChunk;
import de.ellpeck.naturesaura.api.aura.chunk.IDrainSpotEffect;
import de.ellpeck.naturesaura.api.aura.type.IAuraType;
import de.ellpeck.naturesaura.chunk.AuraChunk;
import de.ellpeck.naturesaura.packet.PacketHandler;
import de.ellpeck.naturesaura.packet.PacketParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import org.apache.commons.lang3.tuple.Pair;

public class ReclaimEffect implements IDrainSpotEffect {
    public static final ResourceLocation NAME = ResourceLocation.fromNamespaceAndPath("reclamation_util", "reclaim_effect");
    private int amount; //amount of aura spots (drains/producers) in a chunk per 100k aura in a chunk, capped at 20; used to check if the effect should happen
    private int dist; //aura divided by 100k, clamped to 5-35; used to determine distance from aura spots to do effect

    private boolean calcValues(Level level, BlockPos pos, Integer spot) {
        if (spot <= 0) {
            return false;
        } else {
            Pair<Integer, Integer> auraAndSpots = IAuraChunk.getAuraAndSpotAmountInArea(level, pos, 30);
            int aura = auraAndSpots.getLeft();
            if (aura < 1500000) {
                return false;
            } else {
                this.amount = Math.min(20, Mth.ceil((float)Math.abs(aura) / 100000.0F / (float) auraAndSpots.getRight()));
                if (this.amount <= 1) {
                    return false;
                } else {
                    this.dist = Mth.clamp(Math.abs(aura) / 100000, 5, 35);
                    return true;
                }
            }
        }
    }

    @Override //spot is every aura using or producing block in a chunk, including invisible aura balance blocks
    public void update(Level level, LevelChunk levelChunk, IAuraChunk iAuraChunk, BlockPos pos, Integer spot, AuraChunk.DrainSpot drainSpot) {
        if (level.getGameTime() % 20L == 0L) {
            if (this.calcValues(level, pos, spot)) {
                for(int i = this.amount / 2 + level.random.nextInt(this.amount / 2); i >= 0; --i) {
                    int x = Mth.floor((double)pos.getX() + level.random.nextGaussian() * (double)this.dist);
                    int y = Mth.floor((double)pos.getY() + level.random.nextGaussian() * (double)this.dist);
                    int z = Mth.floor((double)pos.getZ() + level.random.nextGaussian() * (double)this.dist);

                    for(int yOff = -5; yOff <= 5; ++yOff) {
                        BlockPos goalPos = new BlockPos(x, y + yOff, z);
                        if (goalPos.distSqr(pos) <= (double)(this.dist * this.dist) && level.isLoaded(goalPos) && !NaturesAuraAPI.instance().isEffectPowderActive(level, goalPos, NAME)) {
                            BlockState state = level.getBlockState(goalPos);
                            if (state.is(BlockTagGenerator.DRIED_EARTH)) {
                                level.setBlockAndUpdate(goalPos, Blocks.DIRT.defaultBlockState());
                                BlockPos closestSpot = IAuraChunk.getHighestSpot(level, goalPos, 25, pos);
                                IAuraChunk.getAuraChunk(level, closestSpot).drainAura(closestSpot, 500);
                                PacketHandler.sendToAllAround(level, goalPos, 32, new PacketParticles((float)goalPos.getX(), (float)goalPos.getY() + 0.5F, (float)goalPos.getZ(), PacketParticles.Type.PLANT_BOOST, new int[0]));
                                break;
                            }
                        }
                    }
                }

            }
        }
    }

    @Override
    public boolean appliesHere(LevelChunk levelChunk, IAuraChunk iAuraChunk, IAuraType iAuraType) {
        return iAuraType.isSimilar(NaturesAuraAPI.TYPE_OVERWORLD);
    }

    @Override
    public ResourceLocation getName() {
        return NAME;
    }

    @Override
    public ActiveType isActiveHere(Player player, LevelChunk chunk, IAuraChunk auraChunk, BlockPos pos, Integer spot) {
        if (!this.calcValues(player.level(), pos, spot)) {
            return ActiveType.INACTIVE;
        } else if (player.distanceToSqr(pos.getX(), pos.getY(), pos.getZ()) > (double)(this.dist * this.dist)) {
            return ActiveType.INACTIVE;
        } else {
            return NaturesAuraAPI.instance().isEffectPowderActive(player.level(), player.blockPosition(), NAME) ? ActiveType.INHIBITED : ActiveType.ACTIVE;
        }
    }

    @Override
    public ItemStack getDisplayIcon() {
        return Items.DIRT.getDefaultInstance();
    }
}
