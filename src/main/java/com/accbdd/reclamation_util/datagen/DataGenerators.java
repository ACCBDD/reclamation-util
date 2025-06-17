package com.accbdd.reclamation_util.datagen;

import com.accbdd.reclamation_util.register.Items;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static com.accbdd.reclamation_util.ReclamationUtil.MODID;

public class DataGenerators {
    public static void generate(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        generator.addProvider(event.includeServer(), new BiomeTagGenerator(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeClient(), new ItemModelGenerator(packOutput, existingFileHelper));
    }

    public static ResourceLocation loc(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }

    public static class BiomeTagGenerator extends BiomeTagsProvider {
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

    public static class ItemModelGenerator extends ItemModelProvider {

        public ItemModelGenerator(PackOutput output, ExistingFileHelper existingFileHelper) {
            super(output, MODID, existingFileHelper);
        }

        @Override
        protected void registerModels() {
            basicItem(Items.EMPTY_BIOME_BOTTLE.get());
            basicItem(Items.FILLED_BIOME_BOTTLE.get());
        }
    }
}
