package com.accbdd.reclamation_util.datagen;

import com.accbdd.reclamation_util.register.Items;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

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
        basicItem(Items.WOODEN_EXCAVATOR.get());
        basicItem(Items.WOODEN_HAMMER.get());
        basicItem(Items.WOODEN_BROADAXE.get());
        basicItem(Items.STONE_EXCAVATOR.get());
        basicItem(Items.STONE_HAMMER.get());
        basicItem(Items.STONE_BROADAXE.get());
        basicItem(Items.GOLDEN_EXCAVATOR.get());
        basicItem(Items.GOLDEN_HAMMER.get());
        basicItem(Items.GOLDEN_BROADAXE.get());
        basicItem(Items.IRON_EXCAVATOR.get());
        basicItem(Items.IRON_HAMMER.get());
        basicItem(Items.IRON_BROADAXE.get());
        basicItem(Items.DIAMOND_EXCAVATOR.get());
        basicItem(Items.DIAMOND_HAMMER.get());
        basicItem(Items.DIAMOND_BROADAXE.get());
        basicItem(Items.NETHERITE_EXCAVATOR.get());
        basicItem(Items.NETHERITE_HAMMER.get());
        basicItem(Items.NETHERITE_BROADAXE.get());
        basicItem(Items.MANASTEEL_EXCAVATOR.get());
        basicItem(Items.MANASTEEL_HAMMER.get());
        basicItem(Items.MANASTEEL_BROADAXE.get());
        basicItem(Items.BOTANIST_EXCAVATOR.get());
        basicItem(Items.BOTANIST_HAMMER.get());
        basicItem(Items.BOTANIST_BROADAXE.get());
        basicItem(Items.SKY_EXCAVATOR.get());
        basicItem(Items.SKY_HAMMER.get());
        basicItem(Items.SKY_BROADAXE.get());
    }
}
