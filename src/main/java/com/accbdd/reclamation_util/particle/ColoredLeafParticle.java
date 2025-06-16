package com.accbdd.reclamation_util.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.CherryParticle;
import net.minecraft.client.particle.SpriteSet;
import org.joml.Vector3f;

public class ColoredLeafParticle extends CherryParticle {
    private final boolean glowing;
    public ColoredLeafParticle(ClientLevel pLevel, double pX, double pY, double pZ, SpriteSet pSpriteSet, ColoredLeafOptions options) {
        super(pLevel, pX, pY, pZ, pSpriteSet);
        this.setColor(options.color.x, options.color.y, options.color.z);
        this.glowing = options.glowing;
    }

    public int getLightColor(float pPartialTick) {
        return this.glowing ? 240 : super.getLightColor(pPartialTick);
    }
}
