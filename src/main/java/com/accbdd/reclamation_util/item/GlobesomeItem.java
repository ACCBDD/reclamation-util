package com.accbdd.reclamation_util.item;

import com.accbdd.reclamation_util.capability.GlobeCountCapability;
import com.accbdd.reclamation_util.network.ReclamationPacketHandler;
import com.accbdd.reclamation_util.register.Items;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

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
            if (pLevel.getLevelData() != null && pLevel.getLevelData().isHardcore()) {
                pPlayer.setHealth(1);
                pPlayer.getCooldowns().addCooldown(this, 6000);
                pPlayer.getCooldowns().addCooldown(Items.PLAINS_BIOME_GLOBE.get(), 6000);
                pPlayer.getCooldowns().addCooldown(Items.DESERT_BIOME_GLOBE.get(), 6000);
                pPlayer.getCooldowns().addCooldown(Items.FOREST_BIOME_GLOBE.get(), 6000);
                pPlayer.getCooldowns().addCooldown(Items.OCEAN_BIOME_GLOBE.get(), 6000);
                pPlayer.getCooldowns().addCooldown(Items.WARM_OCEAN_BIOME_GLOBE.get(), 6000);
                pPlayer.getCooldowns().addCooldown(Items.TAIGA_BIOME_GLOBE.get(), 6000);
                pPlayer.getCooldowns().addCooldown(Items.CRIMSON_BIOME_GLOBE.get(), 6000);
                pPlayer.getCooldowns().addCooldown(Items.WARPED_BIOME_GLOBE.get(), 6000);
                pPlayer.getCooldowns().addCooldown(Items.LUSH_BIOME_GLOBE.get(), 6000);
                pPlayer.getCooldowns().addCooldown(Items.MYCELIC_BIOME_GLOBE.get(), 6000);
                pPlayer.getCooldowns().addCooldown(Items.SNOWY_BIOME_GLOBE.get(), 6000);
                pPlayer.getCooldowns().addCooldown(Items.COLD_OCEAN_BIOME_GLOBE.get(), 6000);
                pPlayer.getCooldowns().addCooldown(Items.STONY_PEAKS_BIOME_GLOBE.get(), 6000);
                pPlayer.getCooldowns().addCooldown(Items.WINDSWEPT_HILLS_BIOME_GLOBE.get(), 6000);
                pPlayer.getCooldowns().addCooldown(Items.BIRCH_FOREST_BIOME_GLOBE.get(), 6000);
                pPlayer.getCooldowns().addCooldown(Items.SWAMP_BIOME_GLOBE.get(), 6000);
                pPlayer.getCooldowns().addCooldown(Items.MANGROVE_SWAMP_BIOME_GLOBE.get(), 6000);
                pPlayer.getCooldowns().addCooldown(Items.SNOWY_PLAINS_BIOME_GLOBE.get(), 6000);
                pPlayer.getCooldowns().addCooldown(Items.SAVANNA_BIOME_GLOBE.get(), 6000);
                pPlayer.getCooldowns().addCooldown(Items.BADLANDS_BIOME_GLOBE.get(), 6000);
                pPlayer.getCooldowns().addCooldown(Items.RIVER_BIOME_GLOBE.get(), 6000);
                CompoundTag data = pPlayer.getPersistentData();
                data.putByte("globe_count", (byte) (data.getByte("globe_count") + 1));
                if (pPlayer.getServer() != null) {
                    pPlayer.getCapability(GlobeCountCapability.GLOBE_COUNT).ifPresent(cap -> {
                        cap.getData().count++;
                        pPlayer.getServer().getPlayerList().getPlayers().forEach(serverPlayer -> {
                            serverPlayer.playNotifySound(
                                    SoundEvents.LIGHTNING_BOLT_IMPACT,
                                    SoundSource.PLAYERS,
                                    1, 1);
                        });
                        pPlayer.getServer().getPlayerList().broadcastSystemMessage(Component.translatable("chat.reclamation_util.globesome", pPlayer.getScoreboardName(), cap.getData().count).withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.BOLD),false);
                        if (pPlayer instanceof ServerPlayer serverPlayer) {
                            ReclamationPacketHandler.sendToPlayer(cap.getData().count, serverPlayer);
                        }
                    });
                }
                pPlayer.getItemInHand(pUsedHand).shrink(1);
            } else {
                pLevel.explode(pPlayer, pSource, null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), 7, true, Level.ExplosionInteraction.MOB);
                pPlayer.hurt(pSource, Float.MAX_VALUE);
                pPlayer.getCooldowns().addCooldown(this, 6000);
            }
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        if (pLevel != null && pLevel.getLevelData() != null && pLevel.getLevelData().isHardcore()) {
            pTooltipComponents.add(Component.translatable("item.reclamation_util.globesome.tooltip").withStyle(ChatFormatting.RED));
        } else {
            pTooltipComponents.add(Component.translatable("item.reclamation_util.globesome.tooltip_disabled").withStyle(ChatFormatting.DARK_RED));
        }
    }
}
