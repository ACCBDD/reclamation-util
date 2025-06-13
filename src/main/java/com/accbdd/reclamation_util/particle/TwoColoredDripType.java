package com.accbdd.reclamation_util.particle;

import com.mojang.serialization.Codec;
import net.minecraft.core.particles.ParticleType;

public class TwoColoredDripType extends ParticleType<TwoColoredDripOptions> {
    public TwoColoredDripType() {
        super(false, TwoColoredDripOptions.DESERIALIZER);
    }

    @Override
    public Codec<TwoColoredDripOptions> codec() {
        return TwoColoredDripOptions.CODEC;
    }
}
