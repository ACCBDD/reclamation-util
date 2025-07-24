package com.accbdd.reclamation_util.register;

import com.accbdd.complicated_bees.bees.BeeHousingModifier;
import com.accbdd.complicated_bees.config.CommonConfig;
import com.accbdd.complicated_bees.item.FrameItem;
import com.accbdd.reclamation_util.item.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

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
    public static final RegistryObject<Item> ARID_BIOME_BOTTLE = register("arid_biome_bottle", () -> new FoilItem(new Item.Properties()));
    public static final RegistryObject<Item> HELLISH_BIOME_BOTTLE = register("hellish_biome_bottle", () -> new FoilItem(new Item.Properties()));
    public static final RegistryObject<Item> ICY_BIOME_BOTTLE = register("icy_biome_bottle", () -> new FoilItem(new Item.Properties()));
    public static final RegistryObject<Item> LUSH_BIOME_BOTTLE = register("lush_biome_bottle", () -> new FoilItem(new Item.Properties()));
    public static final RegistryObject<Item> MYCELIC_BIOME_BOTTLE = register("mycelic_biome_bottle", () -> new FoilItem(new Item.Properties()));
    public static final RegistryObject<Item> WATERY_BIOME_BOTTLE = register("watery_biome_bottle", () -> new FoilItem(new Item.Properties()));
    public static final RegistryObject<Item> SCULK_AWAKENER = register("sculk_awakener", () -> new SculkAwakenerItem(new Item.Properties()));
    public static final RegistryObject<Item> FRAME_REMOVER = register("frame_remover", () -> new FrameRemoverItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> FLIMSY_DOOR = register("flimsy_door", () -> new FlimsyDoorItem(Blocks.FLIMSY_DOOR.get(), new Item.Properties()));
    public static final RegistryObject<FrameItem> POISON_FRAME = register("poison_frame", () -> new FrameItem(new Item.Properties().durability(60), new BeeHousingModifier.Builder().productivity(0.9f).lifespan(0.75f).build(), CommonConfig.COMMON_CONFIG.frame));

    public static final RegistryObject<CreativeModeTab> CREATIVE_TAB = CREATIVE_MODE_TAB.register(MODID, () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.reclamation_util"))
            .icon(() -> Items.FILLED_BIOME_BOTTLE.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
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
