package com.accbdd.reclamation_util.item;

import com.accbdd.complicated_bees.bees.GeneticHelper;
import net.minecraft.ChatFormatting;
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
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BiomeGlobeItem extends Item {
    private static int RADIUS = 12;
    private static TagKey<Block> CONVERT_UNDER = TagKey.create(BuiltInRegistries.BLOCK.key(), ResourceLocation.parse("reclamation:convert_under"));
    private static TagKey<Block> CONVERTIBLE = TagKey.create(BuiltInRegistries.BLOCK.key(), ResourceLocation.parse("reclamation:convertible"));
    private static TagKey<Block> OCEAN_CONVERTIBLE = TagKey.create(BuiltInRegistries.BLOCK.key(), ResourceLocation.parse("reclamation:ocean_convertible"));

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
            List<ChunkAccess> chunks = new ArrayList<>();
            getSphere(pPlayer.getOnPos(), RADIUS).forEach(pos -> {
                fill(pos, serverLevel, biome, chunks);
                if (pLevel.getBlockState(pos).is(isOcean ? OCEAN_CONVERTIBLE : CONVERTIBLE)) {
                    if (pLevel.getBlockState(pos.above()).is(CONVERT_UNDER)) {
                        pLevel.setBlockAndUpdate(pos, surface);
                    } else  {
                        pLevel.setBlockAndUpdate(pos, dirt);
                    }
                }
            });
            stack.hurtAndBreak(1, pPlayer, player -> player.broadcastBreakEvent(pUsedHand));
            pPlayer.getCooldowns().addCooldown(stack.getItem(), 30);
            serverLevel.getChunkSource().chunkMap.resendBiomesForChunks(chunks);
        }

        return InteractionResultHolder.sidedSuccess(stack, pLevel.isClientSide);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        pTooltipComponents.add(Component.literal("The bottom reads 'error 404 page not found'").withStyle(ChatFormatting.DARK_GRAY));
    }

    private static List<BlockPos> getSphere(BlockPos center, int radius) {
        List<BlockPos> blocks = new ArrayList<>();

        int cx = center.getX();
        int cy = center.getY();
        int cz = center.getZ();

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    double distanceSq = x * x + y * y + z * z;

                    if (distanceSq <= radius * radius) {
                        BlockPos pos = new BlockPos(cx + x, cy + y, cz + z);
                        blocks.add(pos);
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

    private static void fill(BlockPos pos, ServerLevel level, ResourceKey<Biome> biome, List<ChunkAccess> chunks) {
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
}
