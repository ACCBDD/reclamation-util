package com.accbdd.reclamation_util.particle;

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

public class ColoredDripOptions implements ParticleOptions {
    protected final Vector3f color;
    protected final ColoredDripParticle.Type type;
    protected final boolean glowing;

    public static final Codec<ColoredDripOptions> CODEC = RecordCodecBuilder.create((instance) ->
            instance.group(
                    ExtraCodecs.VECTOR3F.fieldOf("color").forGetter(drip -> drip.color),
                    Codec.BOOL.fieldOf("glowing").forGetter(drip -> drip.glowing),
                    Codec.STRING.xmap(ColoredDripParticle.Type::valueOf, Enum::name).fieldOf("type").forGetter(drip -> drip.type)
            ).apply(instance, ColoredDripOptions::new));

    public ColoredDripOptions(Vector3f color, boolean glowing, ColoredDripParticle.Type type) {
        this.color = color;
        this.glowing = glowing;
        this.type = type;
    }

    public ColoredDripOptions(ColoredDripOptions options, ColoredDripParticle.Type type) {
        this(options.color, options.glowing, type);
    }

    @Override
    public ParticleType<?> getType() {
        return type.partType;
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
        buf.writeFloat(this.color.x());
        buf.writeFloat(this.color.y());
        buf.writeFloat(this.color.z());
        buf.writeBoolean(this.glowing);
        buf.writeEnum(this.type);
    }

    @Override
    public String writeToString() {
        return String.format(Locale.ROOT, "%s %.2f %.2f %.2f %s", BuiltInRegistries.PARTICLE_TYPE.getKey(this.getType()), this.color.x(), this.color.y(), this.color.z(), this.type.name());
    }

    public static final ParticleOptions.Deserializer<ColoredDripOptions> DESERIALIZER = new ParticleOptions.Deserializer<>() {
        public ColoredDripOptions fromCommand(ParticleType<ColoredDripOptions> type, StringReader reader) throws CommandSyntaxException {
            Vector3f vector3f = readVector3f(reader);
            reader.expect(' ');
            boolean glowing = reader.readBoolean();
            return new ColoredDripOptions(vector3f, glowing, ColoredDripParticle.Type.HANG);
        }

        public ColoredDripOptions fromNetwork(ParticleType<ColoredDripOptions> type, FriendlyByteBuf buf) {
            return new ColoredDripOptions(readVector3f(buf), buf.readBoolean(), buf.readEnum(ColoredDripParticle.Type.class));
        }
    };
}
