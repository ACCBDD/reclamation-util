package com.accbdd.reclamation_util.item;

import com.accbdd.complicated_bees.bees.GeneticHelper;
import com.accbdd.reclamation_util.capability.GlobeCountCapability;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.QuartPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeResolver;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class BiomeGlobeItem extends Item {
    private static final int RADIUS = 12;
    private static final TagKey<Block> CONVERT_UNDER = TagKey.create(BuiltInRegistries.BLOCK.key(), ResourceLocation.parse("reclamation:convert_under"));
    private static final TagKey<Block> CONVERTIBLE = TagKey.create(BuiltInRegistries.BLOCK.key(), ResourceLocation.parse("reclamation:convertible"));
    private static final TagKey<Block> OCEAN_CONVERTIBLE = TagKey.create(BuiltInRegistries.BLOCK.key(), ResourceLocation.parse("reclamation:ocean_convertible"));

    public static final List<ConversionTask> ACTIVE_TASKS = new ArrayList<>();
    public static final int BLOCKS_PER_TICK = 1000;

    private final ResourceKey<Biome> biome;
    private final BlockState dirt;
    private final BlockState surface;
    private final boolean isOcean;

    public BiomeGlobeItem(ResourceKey<Biome> biome, BlockState dirt, BlockState surface, boolean isOcean) {
        super(new Properties().rarity(Rarity.RARE).defaultDurability(16));
        this.biome = biome;
        this.dirt = dirt;
        this.surface = surface;
        this.isOcean = isOcean;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack stack = pPlayer.getItemInHand(pUsedHand);
        if (!pLevel.isClientSide() && pLevel instanceof ServerLevel serverLevel) {
            int radius = pPlayer.isCrouching() ? RADIUS : RADIUS * ((pPlayer.getCapability(GlobeCountCapability.GLOBE_COUNT).orElse(new GlobeCountCapability()).getData().count) + 1);

            List<BlockPos> conversionArea = getEllipsoid(pPlayer.getOnPos(), radius, Math.max(RADIUS, radius / 4));
            ACTIVE_TASKS.add(new ConversionTask(serverLevel, conversionArea, biome, dirt, surface, isOcean));

            stack.hurtAndBreak(1, pPlayer, player -> player.broadcastBreakEvent(pUsedHand));
            pPlayer.getCooldowns().addCooldown(stack.getItem(), 30);
        }

        return InteractionResultHolder.sidedSuccess(stack, pLevel.isClientSide);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        if (Minecraft.getInstance().player != null) {
            LazyOptional<GlobeCountCapability> capability = Minecraft.getInstance().player.getCapability(GlobeCountCapability.GLOBE_COUNT);
            capability.ifPresent(cap -> {
                pTooltipComponents.add(Component.translatable("item.reclamation_util.globe.tooltip.range", RADIUS * (cap.getData().count + 1)).withStyle(ChatFormatting.GOLD));
                if (cap.getData().count > 0) {
                    pTooltipComponents.add(Component.translatable("item.reclamation_util.globe.tooltip.range_sneak", RADIUS).withStyle(ChatFormatting.GOLD));
                }
            });
        }
        pTooltipComponents.add(Component.literal("The bottom reads 'error 404 page not found'").withStyle(ChatFormatting.DARK_GRAY));
    }

    private static List<BlockPos> getEllipsoid(BlockPos center, int radiusH, int radiusV) {
        List<BlockPos> blocks = new ArrayList<>();
        int cx = center.getX(), cy = center.getY(), cz = center.getZ();

        long rHsq = (long) radiusH * radiusH;
        long rVsq = (long) radiusV * radiusV;

        for (int x = -radiusH; x <= radiusH; x++) {
            long xSq = (long) x * x;
            for (int z = -radiusH; z <= radiusH; z++) {
                long zSq = (long) z * z;

                if ((xSq + zSq) > rHsq) continue;

                for (int y = -radiusV; y <= radiusV; y++) {
                    long ySq = (long) y * y;

                    if ((xSq * rVsq) + (ySq * rHsq) + (zSq * rVsq) <= (rHsq * rVsq)) {
                        blocks.add(new BlockPos(cx + x, cy + y, cz + z));
                    }
                }
            }
        }
        return blocks;
    }

    private static BiomeResolver makeResolver(ChunkAccess pChunk, BoundingBox pTargetRegion, Holder<Biome> pReplacementBiome) {
        return (x, y, z, sampler) -> {
            int i = QuartPos.toBlock(x);
            int j = QuartPos.toBlock(y);
            int k = QuartPos.toBlock(z);
            Holder<Biome> holder = pChunk.getNoiseBiome(x, y, z);
            if (pTargetRegion.isInside(i, j, k)) {
                return pReplacementBiome;
            } else {
                return holder;
            }
        };
    }

    private static int quantize(int pValue) {
        return QuartPos.toBlock(QuartPos.fromBlock(pValue));
    }

    private static BlockPos quantize(BlockPos pPos) {
        return new BlockPos(quantize(pPos.getX()), quantize(pPos.getY()), quantize(pPos.getZ()));
    }

    private static void fill(BlockPos pos, ServerLevel level, ResourceKey<Biome> biome, Collection<ChunkAccess> chunks) {
        ChunkAccess chunkaccess = level.getChunk(pos);
        if (chunkaccess != null) {
            BoundingBox boundingBox = BoundingBox.fromCorners(quantize(pos.offset(-2, -2, -2)), quantize(pos.offset(2, 2, 2)));
            chunkaccess.fillBiomesFromNoise(makeResolver(chunkaccess,
                    boundingBox,
                    GeneticHelper.getRegistryAccess().registry(Registries.BIOME).get().getHolder(biome).get()), level.getChunkSource().randomState().sampler());
            chunkaccess.setUnsaved(true);
            if (level.getBiome(quantize(pos)).is(biome)) {
                chunks.add(chunkaccess);
            }
        }
    }

    public static void serverTick() {
        if (ACTIVE_TASKS.isEmpty()) return;
        ACTIVE_TASKS.removeIf(ConversionTask::tick);
    }

    private static class ConversionTask {
        private final ServerLevel level;
        private final List<BlockPos> positions;
        private final ResourceKey<Biome> biome;
        private final BlockState dirt;
        private final BlockState surface;
        private final boolean isOcean;
        private int index = 0;

        public ConversionTask(ServerLevel level, List<BlockPos> positions, ResourceKey<Biome> biome, BlockState dirt, BlockState surface, boolean isOcean) {
            this.level = level;
            this.positions = positions;
            this.biome = biome;
            this.dirt = dirt;
            this.surface = surface;
            this.isOcean = isOcean;
        }

        public boolean tick() {
            Set<ChunkAccess> updatedChunks = new HashSet<>();
            int processedThisTick = 0;

            while (index < positions.size() && processedThisTick < BLOCKS_PER_TICK) {
                BlockPos pos = positions.get(index);

                fill(pos, level, biome, updatedChunks);

                BlockState state = level.getBlockState(pos);
                if (state.is(isOcean ? OCEAN_CONVERTIBLE : CONVERTIBLE)) {
                    if (level.getBlockState(pos.above()).is(CONVERT_UNDER)) {
                        level.setBlockAndUpdate(pos, surface);
                    } else {
                        level.setBlockAndUpdate(pos, dirt);
                    }
                }

                index++;
                processedThisTick++;
            }

            if (!updatedChunks.isEmpty()) {
                level.getChunkSource().chunkMap.resendBiomesForChunks(new ArrayList<>(updatedChunks));
            }

            return index >= positions.size();
        }
    }
}
