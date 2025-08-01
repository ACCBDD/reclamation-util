package com.accbdd.reclamation_util.item;

import com.accbdd.reclamation_util.datagen.BiomeTagGenerator;
import com.accbdd.reclamation_util.datagen.DataGenerators;
import com.accbdd.reclamation_util.register.Items;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class EmptyBiomeBottle extends Item {
    public EmptyBiomeBottle() {
        super(new Properties()
                .stacksTo(64));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (!pLevel.getBiome(pPlayer.blockPosition()).is(BiomeTagGenerator.BOTTLE_BLACKLIST)) {
            pPlayer.startUsingItem(pUsedHand);
            return InteractionResultHolder.consume(pPlayer.getItemInHand(pUsedHand));
        } else {
            failFill(pPlayer, pLevel);
        }
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
            if (!pLevel.getBiome(pLivingEntity.blockPosition()).is(BiomeTagGenerator.BOTTLE_BLACKLIST)) {

                if (player.isCrouching()) {
                    player.addItem(new ItemStack(Items.FILLED_BIOME_BOTTLE.get(), pStack.getCount()));
                    pStack.setCount(0);
                } else {
                    pStack.shrink(1);
                    player.addItem(Items.FILLED_BIOME_BOTTLE.get().getDefaultInstance());
                }
                player.playSound(SoundEvents.BREWING_STAND_BREW);
            } else {
                failFill(player, pLevel);
            }
        }
        return super.finishUsingItem(pStack, pLevel, pLivingEntity);
    }

    private void failFill(Player player, Level level) {
        if (!level.isClientSide()) {
            player.playSound(SoundEvents.VEX_AMBIENT);
            player.getCooldowns().addCooldown(Items.EMPTY_BIOME_BOTTLE.get(), 40);
        }
        player.displayClientMessage(Component.literal("There isn't enough vital energy here to collect biome essence!"), true);
    }
}
