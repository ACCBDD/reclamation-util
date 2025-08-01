package com.accbdd.reclamation_util.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static com.accbdd.reclamation_util.ReclamationUtil.MODID;
import static com.accbdd.reclamation_util.datagen.DataGenerators.loc;

public class BiomeTagGenerator extends BiomeTagsProvider {
    public static final TagKey<Biome> BOTTLE_BLACKLIST = new TagKey<>(ForgeRegistries.BIOMES.getRegistryKey(), loc("bottle_blacklist"));

    public BiomeTagGenerator(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pProvider, MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(BOTTLE_BLACKLIST).addOptionalTag(ResourceLocation.parse("reclamation:dead"));
        tag(BOTTLE_BLACKLIST).addTag(BiomeTags.IS_NETHER);
    }
}
