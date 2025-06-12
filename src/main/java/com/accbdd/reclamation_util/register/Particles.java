package com.accbdd.reclamation_util.register;

import com.accbdd.reclamation_util.particle.ColoredDripType;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

import static com.accbdd.reclamation_util.ReclamationUtil.MODID;

public class Particles {
    public static final DeferredRegister<ParticleType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, MODID);

    public static final Supplier<ColoredDripType> COLORED_DRIP_HANG = REGISTER.register("colored_drip_hang", ColoredDripType::new);
    public static final Supplier<ColoredDripType> COLORED_DRIP_FALL = REGISTER.register("colored_drip_fall", ColoredDripType::new);
    public static final Supplier<ColoredDripType> COLORED_DRIP_LAND = REGISTER.register("colored_drip_land", ColoredDripType::new);
}
