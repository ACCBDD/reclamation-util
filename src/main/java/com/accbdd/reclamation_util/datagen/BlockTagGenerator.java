package com.accbdd.reclamation_util.datagen;

import com.accbdd.reclamation_util.register.Blocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static com.accbdd.reclamation_util.ReclamationUtil.MODID;

public class BlockTagGenerator extends BlockTagsProvider {
    public static final TagKey<Block> DRIED_EARTH = BlockTags.create(ResourceLocation.fromNamespaceAndPath("reclamation_util", "dried_earth"));

    public BlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(BlockTags.MINEABLE_WITH_AXE).add(Blocks.FLIMSY_DOOR.get());
        tag(DRIED_EARTH).addOptional(ResourceLocation.parse("kubejs:dried_earth"));
    }
}
