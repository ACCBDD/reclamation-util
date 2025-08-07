package com.accbdd.reclamation_util.datagen;

import com.accbdd.reclamation_util.register.Items;
import mekanism.tools.common.ToolsTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static com.accbdd.reclamation_util.ReclamationUtil.MODID;

public class ItemTagGenerator extends ItemTagsProvider {
    public static final TagKey<Item> FRAME = ItemTags.create(ResourceLocation.fromNamespaceAndPath("complicated_bees", "frame"));

    public ItemTagGenerator(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, CompletableFuture<TagLookup<Block>> pBlockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pLookupProvider, pBlockTags, MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(FRAME).add(Items.POISON_FRAME.get(), Items.PERMAFROST_FRAME.get());

        tag(ToolsTags.Items.TOOLS_PAXELS).add(Items.BOTANIST_PAXEL.get(), Items.MANASTEEL_PAXEL.get(), Items.SKY_PAXEL.get());

        tag(ItemTags.AXES).add(
            Items.BOTANIST_BROADAXE.get(),
            Items.WOODEN_BROADAXE.get(),
            Items.STONE_BROADAXE.get(),
            Items.GOLDEN_BROADAXE.get(),
            Items.IRON_BROADAXE.get(),
            Items.DIAMOND_BROADAXE.get(),
            Items.NETHERITE_BROADAXE.get(),
            Items.MANASTEEL_BROADAXE.get(),
            Items.BOTANIST_BROADAXE.get(),
            Items.SKY_BROADAXE.get()
        );

        tag(ItemTags.SHOVELS).add(
            Items.WOODEN_EXCAVATOR.get(),
            Items.STONE_EXCAVATOR.get(),
            Items.GOLDEN_EXCAVATOR.get(),
            Items.IRON_EXCAVATOR.get(),
            Items.DIAMOND_EXCAVATOR.get(),
            Items.NETHERITE_EXCAVATOR.get(),
            Items.MANASTEEL_EXCAVATOR.get(),
            Items.BOTANIST_EXCAVATOR.get(),
            Items.SKY_EXCAVATOR.get()
        );

        tag(ItemTags.PICKAXES).add(
            Items.WOODEN_HAMMER.get(),
            Items.STONE_HAMMER.get(),
            Items.GOLDEN_HAMMER.get(),
            Items.IRON_HAMMER.get(),
            Items.DIAMOND_HAMMER.get(),
            Items.NETHERITE_HAMMER.get(),
            Items.MANASTEEL_HAMMER.get(),
            Items.BOTANIST_HAMMER.get(),
            Items.SKY_HAMMER.get()
        );
    }
}