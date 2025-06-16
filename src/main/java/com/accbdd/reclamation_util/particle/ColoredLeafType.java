package com.accbdd.reclamation_util.particle;

import com.mojang.serialization.Codec;
import net.minecraft.core.particles.ParticleType;

public class ColoredLeafType extends ParticleType<ColoredLeafOptions> {
    public ColoredLeafType() {
        super(false, ColoredLeafOptions.DESERIALIZER);
    }

    @Override
    public Codec<ColoredLeafOptions> codec() {
        return ColoredLeafOptions.CODEC;
    }
}
