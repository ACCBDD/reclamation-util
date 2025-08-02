package com.accbdd.reclamation_util.datagen;

import com.accbdd.reclamation_util.register.Items;
import de.ellpeck.naturesaura.items.ModItems;
import mekanism.tools.common.recipe.PaxelRecipe;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.Tags;
import vazkii.botania.common.item.BotaniaItems;

import java.util.function.Consumer;

public class RecipeGenerator extends RecipeProvider {
    public RecipeGenerator(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> output) {
        excavatorRecipe(output, Items.WOODEN_EXCAVATOR.get(), Ingredient.of(ItemTags.PLANKS), Ingredient.of(Tags.Items.RODS_WOODEN));
        hammerRecipe(output, Items.WOODEN_HAMMER.get(), Ingredient.of(ItemTags.PLANKS), Ingredient.of(Tags.Items.RODS_WOODEN));
        broadaxeRecipe(output, Items.WOODEN_BROADAXE.get(), Ingredient.of(ItemTags.PLANKS), Ingredient.of(Tags.Items.RODS_WOODEN));
        excavatorRecipe(output, Items.STONE_EXCAVATOR.get(), Ingredient.of(Tags.Items.COBBLESTONE), Ingredient.of(Tags.Items.RODS_WOODEN));
        hammerRecipe(output, Items.STONE_HAMMER.get(), Ingredient.of(Tags.Items.COBBLESTONE), Ingredient.of(Tags.Items.RODS_WOODEN));
        broadaxeRecipe(output, Items.STONE_BROADAXE.get(), Ingredient.of(Tags.Items.COBBLESTONE), Ingredient.of(Tags.Items.RODS_WOODEN));
        excavatorRecipe(output, Items.GOLDEN_EXCAVATOR.get(), Ingredient.of(net.minecraft.world.item.Items.GOLD_INGOT), Ingredient.of(Tags.Items.RODS_WOODEN));
        hammerRecipe(output, Items.GOLDEN_HAMMER.get(), Ingredient.of(net.minecraft.world.item.Items.GOLD_INGOT), Ingredient.of(Tags.Items.RODS_WOODEN));
        broadaxeRecipe(output, Items.GOLDEN_BROADAXE.get(), Ingredient.of(net.minecraft.world.item.Items.GOLD_INGOT), Ingredient.of(Tags.Items.RODS_WOODEN));
        excavatorRecipe(output, Items.IRON_EXCAVATOR.get(), Ingredient.of(net.minecraft.world.item.Items.IRON_INGOT), Ingredient.of(Tags.Items.RODS_WOODEN));
        hammerRecipe(output, Items.IRON_HAMMER.get(), Ingredient.of(net.minecraft.world.item.Items.IRON_INGOT), Ingredient.of(Tags.Items.RODS_WOODEN));
        broadaxeRecipe(output, Items.IRON_BROADAXE.get(), Ingredient.of(net.minecraft.world.item.Items.IRON_INGOT), Ingredient.of(Tags.Items.RODS_WOODEN));
        excavatorRecipe(output, Items.DIAMOND_EXCAVATOR.get(), Ingredient.of(net.minecraft.world.item.Items.DIAMOND), Ingredient.of(Tags.Items.RODS_WOODEN));
        hammerRecipe(output, Items.DIAMOND_HAMMER.get(), Ingredient.of(net.minecraft.world.item.Items.DIAMOND), Ingredient.of(Tags.Items.RODS_WOODEN));
        broadaxeRecipe(output, Items.DIAMOND_BROADAXE.get(), Ingredient.of(net.minecraft.world.item.Items.DIAMOND), Ingredient.of(Tags.Items.RODS_WOODEN));
        netheriteSmithing(output, Items.DIAMOND_EXCAVATOR.get(), RecipeCategory.TOOLS, Items.NETHERITE_EXCAVATOR.get());
        netheriteSmithing(output, Items.DIAMOND_HAMMER.get(), RecipeCategory.TOOLS, Items.NETHERITE_HAMMER.get());
        netheriteSmithing(output, Items.DIAMOND_BROADAXE.get(), RecipeCategory.TOOLS, Items.NETHERITE_BROADAXE.get());
        excavatorRecipe(output, Items.MANASTEEL_EXCAVATOR.get(), Ingredient.of(BotaniaItems.manaSteel), Ingredient.of(BotaniaItems.livingwoodTwig));
        hammerRecipe(output, Items.MANASTEEL_HAMMER.get(), Ingredient.of(BotaniaItems.manaSteel), Ingredient.of(BotaniaItems.livingwoodTwig));
        broadaxeRecipe(output, Items.MANASTEEL_BROADAXE.get(), Ingredient.of(BotaniaItems.manaSteel), Ingredient.of(BotaniaItems.livingwoodTwig));
        excavatorRecipe(output, Items.BOTANIST_EXCAVATOR.get(), Ingredient.of(ModItems.INFUSED_IRON), Ingredient.of(ModItems.ANCIENT_STICK));
        hammerRecipe(output, Items.BOTANIST_HAMMER.get(), Ingredient.of(ModItems.INFUSED_IRON), Ingredient.of(ModItems.ANCIENT_STICK));
        broadaxeRecipe(output, Items.BOTANIST_BROADAXE.get(), Ingredient.of(ModItems.INFUSED_IRON), Ingredient.of(ModItems.ANCIENT_STICK));
        excavatorRecipe(output, Items.SKY_EXCAVATOR.get(), Ingredient.of(ModItems.SKY_INGOT), Ingredient.of(ModItems.ANCIENT_STICK));
        hammerRecipe(output, Items.SKY_HAMMER.get(), Ingredient.of(ModItems.SKY_INGOT), Ingredient.of(ModItems.ANCIENT_STICK));
        broadaxeRecipe(output, Items.SKY_BROADAXE.get(), Ingredient.of(ModItems.SKY_INGOT), Ingredient.of(ModItems.ANCIENT_STICK));
        paxelRecipe(output, Items.BOTANIST_PAXEL.get(), Ingredient.of(ModItems.INFUSED_IRON_AXE), Ingredient.of(ModItems.INFUSED_IRON_PICKAXE), Ingredient.of(ModItems.INFUSED_IRON_SHOVEL), Ingredient.of(ModItems.ANCIENT_STICK));
        paxelRecipe(output, Items.SKY_PAXEL.get(), Ingredient.of(ModItems.SKY_AXE), Ingredient.of(ModItems.SKY_PICKAXE), Ingredient.of(ModItems.SKY_SHOVEL), Ingredient.of(ModItems.ANCIENT_STICK));
        paxelRecipe(output, Items.MANASTEEL_PAXEL.get(), Ingredient.of(BotaniaItems.manasteelAxe), Ingredient.of(BotaniaItems.manasteelPick), Ingredient.of(BotaniaItems.manasteelShovel), Ingredient.of(BotaniaItems.livingwoodTwig));
    }

    private static void paxelRecipe(Consumer<FinishedRecipe> output, ItemLike result, Ingredient axe, Ingredient pick, Ingredient shovel, Ingredient stick) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, result)
                .pattern("APS")
                .pattern(" R ")
                .pattern(" R ")
                .define('A', axe)
                .define('P', pick)
                .define('S', shovel)
                .define('R', stick)
                .unlockedBy("has_result", has(result))
                .save(output);
    }

    private static void hammerRecipe(Consumer<FinishedRecipe> output, ItemLike result, Ingredient head, Ingredient stick) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, result)
                .pattern("AAA")
                .pattern("ASA")
                .pattern(" S ")
                .define('A', head)
                .define('S', stick)
                .unlockedBy("has_head", has(head.getItems()[0].getItem()))
                .save(output);
    }

    private static void excavatorRecipe(Consumer<FinishedRecipe> output, ItemLike result, Ingredient head, Ingredient stick) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, result)
                .pattern("AAA")
                .pattern(" SA")
                .pattern("S A")
                .define('A', head)
                .define('S', stick)
                .unlockedBy("has_head", has(head.getItems()[0].getItem()))
                .save(output);
    }

    private static void broadaxeRecipe(Consumer<FinishedRecipe> output, ItemLike result, Ingredient head, Ingredient stick) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, result)
                .pattern("AAA")
                .pattern("AS ")
                .pattern(" S ")
                .define('A', head)
                .define('S', stick)
                .unlockedBy("has_head", has(head.getItems()[0].getItem()))
                .save(output);
    }
}
