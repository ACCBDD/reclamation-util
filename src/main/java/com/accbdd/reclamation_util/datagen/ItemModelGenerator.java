package com.accbdd.reclamation_util.datagen;

import com.accbdd.reclamation_util.register.Items;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import static com.accbdd.reclamation_util.ReclamationUtil.MODID;

public class ItemModelGenerator extends ItemModelProvider {
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
        basicItem(Items.PERMAFROST_FRAME.get());
        basicItem(Items.EMPTY_BIOME_GLOBE.get());
        basicItem(Items.PLAINS_BIOME_GLOBE.get());
        basicItem(Items.DESERT_BIOME_GLOBE.get());
        basicItem(Items.FOREST_BIOME_GLOBE.get());
        basicItem(Items.OCEAN_BIOME_GLOBE.get());
        basicItem(Items.WARM_OCEAN_BIOME_GLOBE.get());
        basicItem(Items.TAIGA_BIOME_GLOBE.get());
        basicItem(Items.CRIMSON_BIOME_GLOBE.get());
        basicItem(Items.WARPED_BIOME_GLOBE.get());
        basicItem(Items.LUSH_BIOME_GLOBE.get());
        basicItem(Items.MYCELIC_BIOME_GLOBE.get());
        basicItem(Items.SNOWY_BIOME_GLOBE.get());
        basicItem(Items.COLD_OCEAN_BIOME_GLOBE.get());
        basicItem(Items.STONY_PEAKS_BIOME_GLOBE.get());
        basicItem(Items.WINDSWEPT_HILLS_BIOME_GLOBE.get());
        basicItem(Items.BIRCH_FOREST_BIOME_GLOBE.get());
        basicItem(Items.SWAMP_BIOME_GLOBE.get());
        basicItem(Items.MANGROVE_SWAMP_BIOME_GLOBE.get());
        basicItem(Items.SNOWY_PLAINS_BIOME_GLOBE.get());
        basicItem(Items.SAVANNA_BIOME_GLOBE.get());
        basicItem(Items.BADLANDS_BIOME_GLOBE.get());
        basicItem(Items.RIVER_BIOME_GLOBE.get());
        basicItem(Items.GLOBESOME.get());
        basicItem(Items.WATERING_CAN.get());
        basicItem(Items.REINFORCED_WATERING_CAN.get());
        toolItem(Items.WOODEN_EXCAVATOR.get());
        toolItem(Items.WOODEN_HAMMER.get());
        toolItem(Items.WOODEN_BROADAXE.get());
        toolItem(Items.STONE_EXCAVATOR.get());
        toolItem(Items.STONE_HAMMER.get());
        toolItem(Items.STONE_BROADAXE.get());
        toolItem(Items.GOLDEN_EXCAVATOR.get());
        toolItem(Items.GOLDEN_HAMMER.get());
        toolItem(Items.GOLDEN_BROADAXE.get());
        toolItem(Items.IRON_EXCAVATOR.get());
        toolItem(Items.IRON_HAMMER.get());
        toolItem(Items.IRON_BROADAXE.get());
        toolItem(Items.DIAMOND_EXCAVATOR.get());
        toolItem(Items.DIAMOND_HAMMER.get());
        toolItem(Items.DIAMOND_BROADAXE.get());
        toolItem(Items.NETHERITE_EXCAVATOR.get());
        toolItem(Items.NETHERITE_HAMMER.get());
        toolItem(Items.NETHERITE_BROADAXE.get());
        toolItem(Items.MANASTEEL_EXCAVATOR.get());
        toolItem(Items.MANASTEEL_HAMMER.get());
        toolItem(Items.MANASTEEL_BROADAXE.get());
        toolItem(Items.BOTANIST_EXCAVATOR.get());
        toolItem(Items.BOTANIST_HAMMER.get());
        toolItem(Items.BOTANIST_BROADAXE.get());
        toolItem(Items.SKY_EXCAVATOR.get());
        toolItem(Items.SKY_HAMMER.get());
        toolItem(Items.SKY_BROADAXE.get());
        toolItem(Items.MANASTEEL_PAXEL.get());
        toolItem(Items.BOTANIST_PAXEL.get());
        toolItem(Items.SKY_PAXEL.get());
        camelPackItem(Items.CAMEL_PACK_BASIC.get());
        camelPackItem(Items.CAMEL_PACK_ADVANCED.get());
        basicItem(Items.CAMEL_PACK_INFINITE.get());
    }

    private void toolItem(Item item) {
        withExistingParent(ForgeRegistries.ITEMS.getKey(item).getPath(), "minecraft:item/handheld")
                .texture("layer0", MODID + ":item/" + ForgeRegistries.ITEMS.getKey(item).getPath());
    }

    private ModelFile layeredModel(String modelName, String baseTex, String overlayTex) {
        return withExistingParent(modelName, "minecraft:item/generated")
                .texture("layer0", MODID + ":item/" + baseTex)
                .texture("layer1", MODID + ":item/" + overlayTex);
    }

    private void camelPackItem(Item item) {
        String name = ForgeRegistries.ITEMS.getKey(item).getPath();
        ResourceLocation fillPredicate = ResourceLocation.fromNamespaceAndPath(MODID, "fill_level");
        ModelFile low = layeredModel(name + "_low", name, "camel_pack_overlay_1");
        ModelFile half = layeredModel(name + "_half", name, "camel_pack_overlay_2");
        ModelFile high = layeredModel(name + "_high", name, "camel_pack_overlay_3");
        ModelFile full = layeredModel(name + "_empty", name, "camel_pack_overlay_4");

        withExistingParent(name, "minecraft:item/generated")
                .texture("layer0", MODID + ":item/" + name)
                .override().predicate(fillPredicate, 0.25f).model(low).end()
                .override().predicate(fillPredicate, 0.50f).model(half).end()
                .override().predicate(fillPredicate, 0.75f).model(high).end()
                .override().predicate(fillPredicate, 1.00f).model(full).end();
    }

}
