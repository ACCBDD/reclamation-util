package com.accbdd.reclamation_util.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;

public class GlobesomeItem extends Item {
    public static final ResourceKey<DamageType> GLOBUS = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.tryBuild("reclamation", "globus"));

    public GlobesomeItem() {
        super(new Properties().rarity(Rarity.EPIC).stacksTo(1));
    }

    @Override
    public boolean isFoil(ItemStack pStack) {
        return true;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        DamageSource pSource = new DamageSource(pLevel.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(GLOBUS));
        if (!pLevel.isClientSide) {
            pLevel.explode(pPlayer, pSource, null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), 7, true, Level.ExplosionInteraction.MOB);
            pPlayer.hurt(pSource, Float.MAX_VALUE);
            pPlayer.getCooldowns().addCooldown(this, 1000);
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
