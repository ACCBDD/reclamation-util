package com.accbdd.reclamation_util.particle;

import com.accbdd.reclamation_util.register.Particles;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.ExtraCodecs;
import org.joml.Vector3f;

import java.util.Locale;

public class TwoColoredDripOptions implements ParticleOptions {
    protected final Vector3f color1;
    protected final Vector3f color2;
    protected final boolean glowing;

    public static final Codec<TwoColoredDripOptions> CODEC = RecordCodecBuilder.create((instance) ->
            instance.group(
                    ExtraCodecs.VECTOR3F.fieldOf("color").forGetter(drip -> drip.color1),
                    ExtraCodecs.VECTOR3F.fieldOf("color2").forGetter(drip -> drip.color2),
                    Codec.BOOL.fieldOf("glowing").forGetter(drip -> drip.glowing)
            ).apply(instance, TwoColoredDripOptions::new));

    public TwoColoredDripOptions(Vector3f color1, Vector3f color2, boolean glowing) {
        this.color1 = color1;
        this.color2 = color2;
        this.glowing = glowing;
    }

    @Override
    public ParticleType<?> getType() {
        return Particles.TWO_COLORED_DRIP_HANG.get();
    }

    public static Vector3f readVector3f(StringReader reader) throws CommandSyntaxException {
        reader.expect(' ');
        float f = reader.readFloat();
        reader.expect(' ');
        float f1 = reader.readFloat();
        reader.expect(' ');
        float f2 = reader.readFloat();
        return new Vector3f(f, f1, f2);
    }

    public static Vector3f readVector3f(FriendlyByteBuf buf) {
        return new Vector3f(buf.readFloat(), buf.readFloat(), buf.readFloat());
    }

    @Override
    public void writeToNetwork(FriendlyByteBuf buf) {
        buf.writeFloat(this.color1.x());
        buf.writeFloat(this.color1.y());
        buf.writeFloat(this.color1.z());
        buf.writeFloat(this.color2.x());
        buf.writeFloat(this.color2.y());
        buf.writeFloat(this.color2.z());
        buf.writeBoolean(this.glowing);
    }

    @Override
    public String writeToString() {
        return String.format(Locale.ROOT, "%s %.2f %.2f %.2f %.2f %.2f %.2f", BuiltInRegistries.PARTICLE_TYPE.getKey(this.getType()), this.color1.x(), this.color1.y(), this.color1.z(), this.color2.x(), this.color2.y(), this.color2.z()
        );
    }

    public static final Deserializer<TwoColoredDripOptions> DESERIALIZER = new Deserializer<>() {
        public TwoColoredDripOptions fromCommand(ParticleType<TwoColoredDripOptions> type, StringReader reader) throws CommandSyntaxException {
            Vector3f color1 = readVector3f(reader);
            Vector3f color2 = readVector3f(reader);
            reader.expect(' ');
            boolean glowing = reader.readBoolean();
            return new TwoColoredDripOptions(color1, color2, glowing);
        }

        public TwoColoredDripOptions fromNetwork(ParticleType<TwoColoredDripOptions> type, FriendlyByteBuf buf) {
            return new TwoColoredDripOptions(readVector3f(buf), readVector3f(buf), buf.readBoolean());
        }
    };
}
