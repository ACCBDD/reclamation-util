package com.accbdd.reclamation_util.register;

import com.accbdd.complicated_bees.bees.BeeHousingModifier;
import com.accbdd.complicated_bees.bees.gene.enums.EnumTolerance;
import com.accbdd.complicated_bees.config.CommonConfig;
import com.accbdd.complicated_bees.item.FrameItem;
import com.accbdd.reclamation_util.item.*;
import de.ellpeck.naturesaura.reg.ModItemTier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import vazkii.botania.api.BotaniaAPI;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static com.accbdd.reclamation_util.ReclamationUtil.MODID;

public class Items {
    public static final DeferredRegister<Item> REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final List<RegistryObject<? extends Item>> CREATIVE_TAB_ITEMS = new ArrayList<>();
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public static final RegistryObject<Item> EMPTY_BIOME_BOTTLE = register("empty_biome_bottle", EmptyBiomeBottle::new);
    public static final RegistryObject<Item> ATTUNED_BIOME_BOTTLE = register("attuned_biome_bottle", EmptyAttunedBiomeBottle::new);
    public static final RegistryObject<Item> FILLED_BIOME_BOTTLE = registerSimpleItem("filled_biome_bottle");
    public static final RegistryObject<Item> ARID_BIOME_BOTTLE = register("arid_biome_bottle", () -> new FoilItem(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> HELLISH_BIOME_BOTTLE = register("hellish_biome_bottle", () -> new FoilItem(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> ICY_BIOME_BOTTLE = register("icy_biome_bottle", () -> new FoilItem(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> LUSH_BIOME_BOTTLE = register("lush_biome_bottle", () -> new FoilItem(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> MYCELIC_BIOME_BOTTLE = register("mycelic_biome_bottle", () -> new FoilItem(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> WATERY_BIOME_BOTTLE = register("watery_biome_bottle", () -> new FoilItem(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> SCULK_AWAKENER = register("sculk_awakener", () -> new SculkAwakenerItem(new Item.Properties()));
    public static final RegistryObject<Item> FRAME_REMOVER = register("frame_remover", () -> new FrameRemoverItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> FLIMSY_DOOR = register("flimsy_door", () -> new FlimsyDoorItem(Blocks.FLIMSY_DOOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> EMPTY_BIOME_GLOBE = register("empty_biome_globe", EmptyBiomeGlobeItem::new);
    public static final RegistryObject<Item> PLAINS_BIOME_GLOBE = register("plains_biome_globe", () -> new BiomeGlobeItem(Biomes.PLAINS,
            net.minecraft.world.level.block.Blocks.DIRT.defaultBlockState(),
            net.minecraft.world.level.block.Blocks.GRASS_BLOCK.defaultBlockState(),
            false));
    public static final RegistryObject<Item> DESERT_BIOME_GLOBE = register("desert_biome_globe", () -> new BiomeGlobeItem(Biomes.DESERT,
            net.minecraft.world.level.block.Blocks.SAND.defaultBlockState(),
            net.minecraft.world.level.block.Blocks.SAND.defaultBlockState(),
            false));
    public static final RegistryObject<Item> FOREST_BIOME_GLOBE = register("forest_biome_globe", () -> new BiomeGlobeItem(Biomes.FOREST,
            net.minecraft.world.level.block.Blocks.DIRT.defaultBlockState(),
            net.minecraft.world.level.block.Blocks.GRASS_BLOCK.defaultBlockState(),
            false));
    public static final RegistryObject<Item> OCEAN_BIOME_GLOBE = register("ocean_biome_globe", () -> new BiomeGlobeItem(Biomes.OCEAN,
            net.minecraft.world.level.block.Blocks.SAND.defaultBlockState(),
            net.minecraft.world.level.block.Blocks.SAND.defaultBlockState(),
            true));

    public static final RegistryObject<Item> WOODEN_EXCAVATOR = register("wooden_excavator", () -> new ExcavatorItem(Tiers.WOOD, 1.5F, -3.0F, new Item.Properties()));
    public static final RegistryObject<Item> WOODEN_HAMMER = register("wooden_hammer", () -> new HammerItem(Tiers.WOOD, 1, -2.8F, new Item.Properties()));
    public static final RegistryObject<Item> WOODEN_BROADAXE = register("wooden_broadaxe", () -> new BroadaxeItem(Tiers.WOOD, 6.0F, -3.2F, new Item.Properties()));
    public static final RegistryObject<Item> STONE_EXCAVATOR = register("stone_excavator", () -> new ExcavatorItem(Tiers.STONE, 1.5F, -3.0F, new Item.Properties()));
    public static final RegistryObject<Item> STONE_HAMMER = register("stone_hammer", () -> new HammerItem(Tiers.STONE, 1, -2.8F, new Item.Properties()));
    public static final RegistryObject<Item> STONE_BROADAXE = register("stone_broadaxe", () -> new BroadaxeItem(Tiers.STONE, 7.0F, -3.2F, new Item.Properties()));
    public static final RegistryObject<Item> GOLDEN_EXCAVATOR = register("golden_excavator", () -> new ExcavatorItem(Tiers.GOLD, 1.5F, -3.0F, new Item.Properties()));
    public static final RegistryObject<Item> GOLDEN_HAMMER = register("golden_hammer", () -> new HammerItem(Tiers.GOLD, 1, -2.8F, new Item.Properties()));
    public static final RegistryObject<Item> GOLDEN_BROADAXE = register("golden_broadaxe", () -> new BroadaxeItem(Tiers.GOLD, 6.0F, -3.0F, new Item.Properties()));
    public static final RegistryObject<Item> IRON_EXCAVATOR = register("iron_excavator", () -> new ExcavatorItem(Tiers.IRON, 1.5F, -3.0F, new Item.Properties()));
    public static final RegistryObject<Item> IRON_HAMMER = register("iron_hammer", () -> new HammerItem(Tiers.IRON, 1, -2.8F, new Item.Properties()));
    public static final RegistryObject<Item> IRON_BROADAXE = register("iron_broadaxe", () -> new BroadaxeItem(Tiers.IRON, 6.0F, -3.1F, new Item.Properties()));
    public static final RegistryObject<Item> DIAMOND_EXCAVATOR = register("diamond_excavator", () -> new ExcavatorItem(Tiers.DIAMOND, 1.5F, -3.0F, new Item.Properties()));
    public static final RegistryObject<Item> DIAMOND_HAMMER = register("diamond_hammer", () -> new HammerItem(Tiers.DIAMOND, 1, -2.8F, new Item.Properties()));
    public static final RegistryObject<Item> DIAMOND_BROADAXE = register("diamond_broadaxe", () -> new BroadaxeItem(Tiers.DIAMOND, 5.0F, -3.0F, new Item.Properties()));
    public static final RegistryObject<Item> NETHERITE_EXCAVATOR = register("netherite_excavator", () -> new ExcavatorItem(Tiers.NETHERITE, 1.5F, -3.0F, (new Item.Properties()).fireResistant()));
    public static final RegistryObject<Item> NETHERITE_HAMMER = register("netherite_hammer", () -> new HammerItem(Tiers.NETHERITE, 1, -2.8F, (new Item.Properties()).fireResistant()));
    public static final RegistryObject<Item> NETHERITE_BROADAXE = register("netherite_broadaxe", () -> new BroadaxeItem(Tiers.NETHERITE, 5.0F, -3.0F, (new Item.Properties()).fireResistant()));
    public static final RegistryObject<Item> MANASTEEL_EXCAVATOR = register("manasteel_excavator", () -> new ManasteelExcavatorItem(new Item.Properties()));
    public static final RegistryObject<Item> MANASTEEL_HAMMER = register("manasteel_hammer", () -> new ManasteelHammerItem(new Item.Properties()));
    public static final RegistryObject<Item> MANASTEEL_BROADAXE = register("manasteel_broadaxe", () -> new ManasteelBroadaxeItem(new Item.Properties()));
    public static final RegistryObject<Item> BOTANIST_EXCAVATOR = register("botanist_excavator", () -> new BotanistExcavatorItem(ModItemTier.INFUSED, 1.5f, -3.0f, new Item.Properties()));
    public static final RegistryObject<Item> BOTANIST_HAMMER = register("botanist_hammer", () -> new BotanistHammerItem(ModItemTier.INFUSED, 1f, -2.8f, new Item.Properties()));
    public static final RegistryObject<Item> BOTANIST_BROADAXE = register("botanist_broadaxe", () -> new BotanistBroadaxeItem(ModItemTier.INFUSED, 6f, -3.2f, new Item.Properties()));
    public static final RegistryObject<Item> SKY_EXCAVATOR = register("sky_excavator", () -> new BotanistExcavatorItem(ModItemTier.SKY, 1.5f, -3.0f, new Item.Properties()));
    public static final RegistryObject<Item> SKY_HAMMER = register("sky_hammer", () -> new BotanistHammerItem(ModItemTier.SKY, 1f, -2.8f, new Item.Properties()));
    public static final RegistryObject<Item> SKY_BROADAXE = register("sky_broadaxe", () -> new BotanistBroadaxeItem(ModItemTier.SKY, 6f, -3.2f, new Item.Properties()));
    public static final RegistryObject<Item> MANASTEEL_PAXEL = register("manasteel_paxel", () -> new ManasteelPaxelItem(BotaniaAPI.instance().getManasteelItemTier(), 6.5f, -2.4f, new Item.Properties()));
    public static final RegistryObject<Item> BOTANIST_PAXEL = register("botanist_paxel", () -> new BotanistPaxelItem(6.5f, -2.4f, ModItemTier.INFUSED, new Item.Properties()) );
    public static final RegistryObject<Item> SKY_PAXEL = register("sky_paxel", () -> new BotanistPaxelItem(6.5f, -2.4f, ModItemTier.SKY, new Item.Properties()));

    public static final RegistryObject<FrameItem> POISON_FRAME = register("poison_frame", () -> new FrameItem(new Item.Properties().durability(60), new BeeHousingModifier.Builder().productivity(0.9f).lifespan(0.75f).build(), CommonConfig.COMMON_CONFIG.frame));
    public static final RegistryObject<FrameItem> PERMAFROST_FRAME = register("permafrost_frame", () -> new FrameItem(new Item.Properties().durability(120), new BeeHousingModifier.Builder().temperature(EnumTolerance.DOWN_2).lifespan(0.4f).build(), CommonConfig.COMMON_CONFIG.frame) {
        @Override
        public boolean isFoil(ItemStack pStack) {
            return true;
        }
    });

    public static final RegistryObject<CreativeModeTab> CREATIVE_TAB = CREATIVE_MODE_TAB.register(MODID, () -> CreativeModeTab.builder().title(Component.translatable("itemGroup.reclamation_util")).icon(() -> Items.FILLED_BIOME_BOTTLE.get().getDefaultInstance()).displayItems((parameters, output) -> {
        for (RegistryObject<?> item : Items.CREATIVE_TAB_ITEMS) {
            output.accept((Item) item.get());
        }
    }).build());

    private static <T extends Item> RegistryObject<T> register(String name, Supplier<T> itemSupplier) {
        var register = REGISTER.register(name, itemSupplier);
        CREATIVE_TAB_ITEMS.add(register);
        return register;
    }

    private static RegistryObject<Item> registerSimpleItem(String name) {
        return registerSimpleItem(name, new Item.Properties());
    }

    private static RegistryObject<Item> registerSimpleItem(String name, Item.Properties prop) {
        return register(name, () -> new Item(prop));
    }
}
