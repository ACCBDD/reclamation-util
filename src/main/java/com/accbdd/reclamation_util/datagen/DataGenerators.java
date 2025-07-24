package com.accbdd.reclamation_util.datagen;

import com.accbdd.reclamation_util.datagen.loot.BlockLootTables;
import com.accbdd.reclamation_util.register.Blocks;
import com.accbdd.reclamation_util.register.Items;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.accbdd.reclamation_util.ReclamationUtil.MODID;

public class DataGenerators {
    public static void generate(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        generator.addProvider(event.includeServer(), new BiomeTagGenerator(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), (DataProvider.Factory<LootTableGenerator>) LootTableGenerator::new);

        generator.addProvider(event.includeClient(), new BlockStateGenerator(packOutput, existingFileHelper));
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
            basicItem(Items.ATTUNED_BIOME_BOTTLE.get());
            basicItem(Items.ARID_BIOME_BOTTLE.get());
            basicItem(Items.HELLISH_BIOME_BOTTLE.get());
            basicItem(Items.ICY_BIOME_BOTTLE.get());
            basicItem(Items.LUSH_BIOME_BOTTLE.get());
            basicItem(Items.MYCELIC_BIOME_BOTTLE.get());
            basicItem(Items.WATERY_BIOME_BOTTLE.get());
            basicItem(Items.SCULK_AWAKENER.get());
            basicItem(Items.FRAME_REMOVER.get());
            basicItem(Items.FLIMSY_DOOR.get());
            basicItem(Items.POISON_FRAME.get());
        }
    }

    public static class BlockStateGenerator extends BlockStateProvider {
        public BlockStateGenerator(PackOutput output, ExistingFileHelper existingFileHelper) {
            super(output, MODID, existingFileHelper);
        }

        @Override
        protected void registerStatesAndModels() {
            doorBlockWithRenderType(Blocks.FLIMSY_DOOR.get(), loc("block/flimsy_door_bottom"), loc("block/flimsy_door_top"), "cutout");
        }
    }

    public static class LootTableGenerator extends LootTableProvider {
        private static final List<SubProviderEntry> entries = List.of(
                new LootTableProvider.SubProviderEntry(
                        BlockLootTables::new,
                        LootContextParamSets.BLOCK
                )
        );

        public LootTableGenerator(PackOutput pOutput) {
            super(pOutput, Collections.emptySet(), entries);
        }
    }
}
