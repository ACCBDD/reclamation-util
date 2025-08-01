package com.accbdd.reclamation_util.particle;

import com.mojang.serialization.Codec;
import net.minecraft.core.particles.ParticleType;

public class ColoredDripType extends ParticleType<ColoredDripOptions> {
    public ColoredDripType() {
        super(false, ColoredDripOptions.DESERIALIZER);
    }

    @Override
    public Codec<ColoredDripOptions> codec() {
        return ColoredDripOptions.CODEC;
    }
}
