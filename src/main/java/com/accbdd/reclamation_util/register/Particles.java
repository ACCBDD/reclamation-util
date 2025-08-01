package com.accbdd.reclamation_util.register;

import com.accbdd.reclamation_util.particle.ColoredDripType;
import com.accbdd.reclamation_util.particle.ColoredLeafType;
import com.accbdd.reclamation_util.particle.TwoColoredDripType;
import net.minecraft.core.particles.ParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

import static com.accbdd.reclamation_util.ReclamationUtil.MODID;

public class Particles {
    public static final DeferredRegister<ParticleType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, MODID);

    public static final Supplier<ColoredDripType> COLORED_DRIP_HANG = REGISTER.register("colored_drip_hang", ColoredDripType::new);
    public static final Supplier<TwoColoredDripType> TWO_COLORED_DRIP_HANG = REGISTER.register("two_colored_drip_hang", TwoColoredDripType::new);
    public static final Supplier<ColoredDripType> COLORED_DRIP_FALL = REGISTER.register("colored_drip_fall", ColoredDripType::new);
    public static final Supplier<ColoredDripType> COLORED_DRIP_LAND = REGISTER.register("colored_drip_land", ColoredDripType::new);
    public static final Supplier<ColoredLeafType> COLORED_LEAF_TYPE = REGISTER.register("colored_leaf", ColoredLeafType::new);
}
