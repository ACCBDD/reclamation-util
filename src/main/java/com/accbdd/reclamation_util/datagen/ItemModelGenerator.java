package com.accbdd.reclamation_util.datagen;

import com.accbdd.reclamation_util.register.Items;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
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
    }

    private void toolItem(Item item) {
        withExistingParent(ForgeRegistries.ITEMS.getKey(item).getPath(), "minecraft:item/handheld")
                .texture("layer0", ForgeRegistries.ITEMS.getKey(item).getNamespace() + ":item/" + ForgeRegistries.ITEMS.getKey(item).getPath());
    }
}
