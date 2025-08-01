package com.accbdd.reclamation_util.particle;

import com.accbdd.reclamation_util.register.Particles;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

public class ColoredDripParticle extends TextureSheetParticle {
    protected boolean isGlowing;

    public ColoredDripParticle(ClientLevel pLevel, double pX, double pY, double pZ, ColoredDripOptions options) {
        super(pLevel, pX, pY, pZ);
        this.setSize(0.01F, 0.01F);
        this.setColor(options.color.x, options.color.y, options.color.z);
        this.isGlowing = options.glowing;
        this.gravity = 0.06F;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public int getLightColor(float pPartialTick) {
        return this.isGlowing ? 240 : super.getLightColor(pPartialTick);
    }

    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        this.preMoveUpdate();
        if (!this.removed) {
            this.yd -= this.gravity;
            this.move(this.xd, this.yd, this.zd);
            this.postMoveUpdate();
            if (!this.removed) {
                this.xd *= 0.98F;
                this.yd *= 0.98F;
                this.zd *= 0.98F;
            }
        }
    }

    protected void preMoveUpdate() {
        if (this.lifetime-- <= 0) {
            this.remove();
        }
    }

    protected void postMoveUpdate() {
    }

    public static TextureSheetParticle createHangParticle(ClientLevel pLevel, double pX, double pY, double pZ, ColoredDripOptions options) {
        return new ColoredDripHangParticle(pLevel, pX, pY, pZ, options);
    }

    public static TextureSheetParticle createHangParticle(ClientLevel pLevel, double pX, double pY, double pZ, TwoColoredDripOptions options) {
        return new TwoColoredDripHangParticle(pLevel, pX, pY, pZ, options);
    }

    public static TextureSheetParticle createFallParticle(ClientLevel pLevel, double pX, double pY, double pZ, ColoredDripOptions options) {
        return new ColoredFallAndLandParticle(pLevel, pX, pY, pZ, options);
    }

    public static TextureSheetParticle createLandParticle(ClientLevel pLevel, double pX, double pY, double pZ, ColoredDripOptions options) {
        return new ColoredDripLandParticle(pLevel, pX, pY, pZ, options);
    }

    @OnlyIn(Dist.CLIENT)
    static class ColoredDripHangParticle extends ColoredDripParticle {
        protected ParticleOptions fallingParticle;

        ColoredDripHangParticle(ClientLevel pLevel, double pX, double pY, double pZ, ColoredDripOptions options) {
            super(pLevel, pX, pY, pZ, options);
            this.fallingParticle = new ColoredDripOptions(options, Type.FALL);
            this.gravity *= 0.02F;
            this.lifetime = 40;
        }

        protected void preMoveUpdate() {
            if (this.lifetime-- <= 0) {
                this.remove();
                this.level.addParticle(this.fallingParticle, this.x, this.y, this.z, this.xd, this.yd, this.zd);
            }
        }

        protected void postMoveUpdate() {
            this.xd *= 0.02D;
            this.yd *= 0.02D;
            this.zd *= 0.02D;
        }
    }

    @OnlyIn(Dist.CLIENT)
    static class TwoColoredDripHangParticle extends ColoredDripHangParticle {
        private final Vector3f color1;
        private final Vector3f color2;

        TwoColoredDripHangParticle(ClientLevel pLevel, double pX, double pY, double pZ, TwoColoredDripOptions options) {
            super(pLevel, pX, pY, pZ, new ColoredDripOptions(options.color1, options.glowing, Type.HANG));
            this.fallingParticle = new ColoredDripOptions(options.color2, options.glowing, Type.FALL);
            color1 = options.color1;
            color2 = options.color2;
        }

        protected void preMoveUpdate() {
            this.rCol = color1.x - (color1.x - color2.x) * ((40f - lifetime) / 40);
            this.gCol = color1.y - (color1.y - color2.y) * ((40f - lifetime) / 40);
            this.bCol = color1.z - (color1.z - color2.z) * ((40f - lifetime) / 40);
            super.preMoveUpdate();
        }
    }

    @OnlyIn(Dist.CLIENT)
    static class ColoredDripLandParticle extends ColoredDripParticle {
        ColoredDripLandParticle(ClientLevel pLevel, double pX, double pY, double pZ, ColoredDripOptions options) {
            super(pLevel, pX, pY, pZ, options);
            this.lifetime = (int) (16.0D / (Math.random() * 0.8D + 0.2D));
        }
    }

    @OnlyIn(Dist.CLIENT)
    static class ColoredFallAndLandParticle extends FallingParticle {
        protected final ParticleOptions landParticle;

        ColoredFallAndLandParticle(ClientLevel pLevel, double pX, double pY, double pZ, ColoredDripOptions options) {
            super(pLevel, pX, pY, pZ, options);
            this.landParticle = new ColoredDripOptions(options, Type.LAND);
        }

        protected void postMoveUpdate() {
            if (this.onGround) {
                this.remove();
                this.level.addParticle(this.landParticle, this.x, this.y, this.z, 0.0D, 0.0D, 0.0D);
            }

        }
    }

    @OnlyIn(Dist.CLIENT)
    static class FallingParticle extends ColoredDripParticle {
        FallingParticle(ClientLevel pLevel, double pX, double pY, double pZ, ColoredDripOptions options) {
            this(pLevel, pX, pY, pZ, options, (int) (64.0D / (Math.random() * 0.8D + 0.2D)));
        }

        FallingParticle(ClientLevel pLevel, double pX, double pY, double pZ, ColoredDripOptions options, int pLifetime) {
            super(pLevel, pX, pY, pZ, options);
            this.lifetime = pLifetime;
        }

        protected void postMoveUpdate() {
            if (this.onGround) {
                this.remove();
            }

        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider.Sprite<ColoredDripOptions> {
        @Nullable
        @Override
        public TextureSheetParticle createParticle(ColoredDripOptions pOptions, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            return switch (pOptions.type) {
                case FALL -> createFallParticle(pLevel, pX, pY, pZ, pOptions);
                case LAND -> createLandParticle(pLevel, pX, pY, pZ, pOptions);
                default -> createHangParticle(pLevel, pX, pY, pZ, pOptions);
            };
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class TwoProvider implements ParticleProvider.Sprite<TwoColoredDripOptions> {
        @Nullable
        @Override
        public TextureSheetParticle createParticle(TwoColoredDripOptions pOptions, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            return createHangParticle(pLevel, pX, pY, pZ, pOptions);
        }
    }

    public enum Type {
        TWO_HANG(Particles.TWO_COLORED_DRIP_HANG.get()),
        HANG(Particles.COLORED_DRIP_HANG.get()),
        FALL(Particles.COLORED_DRIP_FALL.get()),
        LAND(Particles.COLORED_DRIP_LAND.get());

        public final ParticleType<?> partType;

        Type(ParticleType<?> coloredDripType) {
            this.partType = coloredDripType;
        }
    }
}
