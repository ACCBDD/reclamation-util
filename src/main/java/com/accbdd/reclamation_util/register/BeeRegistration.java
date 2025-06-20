package com.accbdd.reclamation_util.register;

import com.accbdd.complicated_bees.bees.effect.IBeeEffect;
import com.accbdd.complicated_bees.registry.BeeEffectRegistration;
import com.accbdd.reclamation_util.bees.effect.NaturalAuraEffect;
import net.minecraftforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static com.accbdd.reclamation_util.ReclamationUtil.MODID;

public class BeeRegistration {
    public static final DeferredRegister<IBeeEffect> EFFECTS = DeferredRegister.create(BeeEffectRegistration.BEE_REGISTRY_KEY, MODID);

    public static final Supplier<NaturalAuraEffect> NATURAL_AURA = EFFECTS.register("natural_aura", NaturalAuraEffect::new);
}
