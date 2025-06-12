package com.accbdd.narenderfix.mixins;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import de.ellpeck.naturesaura.NaturesAura;
import de.ellpeck.naturesaura.particles.ParticleHandler;
import de.ellpeck.naturesaura.particles.ParticleMagic;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Locale;

@Mixin(ParticleHandler.class)
public class MixinParticleHandler {
    @Shadow
    public static final ParticleRenderType MAGIC = new ParticleRenderType() {
        @Override
        public void begin(BufferBuilder buffer, TextureManager textureManager) {
            MixinParticleHandler.setupRendering();
            RenderSystem.enableDepthTest();
            buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
        }

        @Override
        public void end(Tesselator tessellator) {
            tessellator.end();
            RenderSystem.enableCull();
        }

        @Override
        public String toString() {
            return NaturesAura.MOD_ID.toUpperCase(Locale.ROOT) + "_MAGIC";
        }
    };

    @Shadow
    public static final ParticleRenderType MAGIC_NO_DEPTH = new ParticleRenderType() {
        @Override
        public void begin(BufferBuilder buffer, TextureManager textureManager) {
            MixinParticleHandler.setupRendering();
            RenderSystem.disableDepthTest();
            buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
        }

        @Override
        public void end(Tesselator tessellator) {
            tessellator.end();
            RenderSystem.enableCull();
        }

        @Override
        public String toString() {
            return NaturesAura.MOD_ID.toUpperCase(Locale.ROOT) + "_MAGIC_NO_DEPTH";
        }
    };

    private static void setupRendering() {
        RenderSystem.disableCull();
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
        RenderSystem.depthMask(false);
        RenderSystem.setShader(GameRenderer::getParticleShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, ParticleMagic.TEXTURE);
    }
}
