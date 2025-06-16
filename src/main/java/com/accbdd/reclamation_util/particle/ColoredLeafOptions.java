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

public class ColoredLeafOptions implements ParticleOptions {
    public final Vector3f color;
    public final boolean glowing;

    public static final Codec<ColoredLeafOptions> CODEC = RecordCodecBuilder.create((instance) ->
            instance.group(
                    ExtraCodecs.VECTOR3F.fieldOf("color").forGetter(part -> part.color),
                    Codec.BOOL.fieldOf("glowing").forGetter(part -> part.glowing)
            ).apply(instance, ColoredLeafOptions::new));

    public ColoredLeafOptions(Vector3f color, boolean glowing) {
        this.color = color;
        this.glowing = glowing;
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
    public ParticleType<?> getType() {
        return Particles.COLORED_LEAF_TYPE.get();
    }

    @Override
    public void writeToNetwork(FriendlyByteBuf pBuffer) {
        pBuffer.writeFloat(this.color.x());
        pBuffer.writeFloat(this.color.y());
        pBuffer.writeFloat(this.color.z());
        pBuffer.writeBoolean(glowing);
    }

    @Override
    public String writeToString() {
        return String.format(Locale.ROOT, "%s %.2f %.2f %.2f %b", BuiltInRegistries.PARTICLE_TYPE.getKey(this.getType()), this.color.x(), this.color.y(), this.color.z(), this.glowing);
    }

    public static final ParticleOptions.Deserializer<ColoredLeafOptions> DESERIALIZER = new ParticleOptions.Deserializer<>() {
        public ColoredLeafOptions fromCommand(ParticleType<ColoredLeafOptions> type, StringReader reader) throws CommandSyntaxException {
            Vector3f vector3f = readVector3f(reader);
            reader.expect(' ');
            boolean glowing = reader.readBoolean();
            return new ColoredLeafOptions(vector3f, glowing);
        }

        public ColoredLeafOptions fromNetwork(ParticleType<ColoredLeafOptions> type, FriendlyByteBuf buf) {
            return new ColoredLeafOptions(readVector3f(buf), buf.readBoolean());
        }
    };
}
