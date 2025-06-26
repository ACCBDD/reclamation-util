package com.accbdd.reclamation_util.item;

import com.accbdd.reclamation_util.register.Items;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

public class EmptyAttunedBiomeBottle extends FoilItem {
    public EmptyAttunedBiomeBottle() {
        super(new Properties()
                .stacksTo(64));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        pPlayer.startUsingItem(pUsedHand);
        return InteractionResultHolder.consume(pPlayer.getItemInHand(pUsedHand));
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.BOW;
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 40;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        if (pLivingEntity instanceof Player player) {
            Holder<Biome> biome = pLevel.getBiome(pLivingEntity.blockPosition());
            if (biome.is(Biomes.DESERT))
                collectBottle(pStack, player, Items.ARID_BIOME_BOTTLE.get());
            else if (biome.is(BiomeTags.IS_NETHER))
                collectBottle(pStack, player, Items.HELLISH_BIOME_BOTTLE.get());
            else if (biome.is(Biomes.SNOWY_SLOPES))
                collectBottle(pStack, player, Items.ICY_BIOME_BOTTLE.get());
            else if (biome.is(Biomes.BAMBOO_JUNGLE))
                collectBottle(pStack, player, Items.LUSH_BIOME_BOTTLE.get());
            else if (biome.is(Biomes.MUSHROOM_FIELDS))
                collectBottle(pStack, player, Items.MYCELIC_BIOME_BOTTLE.get());
            else if (biome.is(Biomes.WARM_OCEAN))
                collectBottle(pStack, player, Items.WATERY_BIOME_BOTTLE.get());
            else
                failFill(player, pLevel);
        }
        return super.finishUsingItem(pStack, pLevel, pLivingEntity);
    }

    private static void collectBottle(ItemStack pStack, Player player, Item item) {
        if (player.isCrouching()) {
            player.addItem(new ItemStack(item, pStack.getCount()));
            pStack.setCount(0);
        } else {
            pStack.shrink(1);
            player.addItem(item.getDefaultInstance());
        }
        player.playSound(SoundEvents.BREWING_STAND_BREW);
    }

    private static void failFill(Player player, Level level) {
        if (!level.isClientSide()) {
            player.playSound(SoundEvents.VEX_AMBIENT);
            player.getCooldowns().addCooldown(Items.EMPTY_BIOME_BOTTLE.get(), 40);
        }
        player.displayClientMessage(Component.literal("There isn't enough attuned energy here to collect attuned biome essence!"), true);
    }
}
