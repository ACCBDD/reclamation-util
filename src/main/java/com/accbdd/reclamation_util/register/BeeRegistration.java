package com.accbdd.reclamation_util.register;

import com.accbdd.complicated_bees.bees.effect.IBeeEffect;
import com.accbdd.complicated_bees.registry.BeeEffectRegistration;
import com.accbdd.reclamation_util.bees.effect.ConversionEffect;
import com.accbdd.reclamation_util.bees.effect.NaturalAuraEffect;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static com.accbdd.reclamation_util.ReclamationUtil.MODID;

public class BeeRegistration {
    public static final DeferredRegister<IBeeEffect> EFFECTS = DeferredRegister.create(BeeEffectRegistration.BEE_REGISTRY_KEY, MODID);

    public static final Supplier<NaturalAuraEffect> NATURAL_AURA = EFFECTS.register("natural_aura", NaturalAuraEffect::new);
    public static final Supplier<ConversionEffect> CRIMSINE = EFFECTS.register("crimsine", () -> new ConversionEffect(Blocks.STONE.defaultBlockState(), BuiltInRegistries.BLOCK.get(ResourceLocation.parse("create:crimsite")).defaultBlockState()));
    public static final Supplier<ConversionEffect> OCHURATE = EFFECTS.register("ochurate", () -> new ConversionEffect(Blocks.STONE.defaultBlockState(), BuiltInRegistries.BLOCK.get(ResourceLocation.parse("create:ochrum")).defaultBlockState()));
    public static final Supplier<ConversionEffect> ASURIC = EFFECTS.register("asuric", () -> new ConversionEffect(Blocks.STONE.defaultBlockState(), BuiltInRegistries.BLOCK.get(ResourceLocation.parse("create:asurine")).defaultBlockState()));
    public static final Supplier<ConversionEffect> VERIDINE = EFFECTS.register("veridine", () -> new ConversionEffect(Blocks.STONE.defaultBlockState(), BuiltInRegistries.BLOCK.get(ResourceLocation.parse("create:veridium")).defaultBlockState()));
}
