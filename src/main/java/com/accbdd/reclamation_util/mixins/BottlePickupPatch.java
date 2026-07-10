package com.accbdd.reclamation_util.mixins;

import dev.ghen.thirst.content.purity.WaterPurity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BottleItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(value = BottleItem.class, priority = 3000)
public abstract class BottlePickupPatch extends Item {

    public BottlePickupPatch(Properties properties) {
        super(properties);
    }

    @Shadow
    protected abstract ItemStack turnBottleIntoItem(ItemStack bottleStack, Player player, ItemStack filledBottleStack);

    @Inject(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;gameEvent(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/level/gameevent/GameEvent;Lnet/minecraft/core/BlockPos;)V", shift = At.Shift.BEFORE), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
    private void multiBottleWaterPickup(Level level, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir, List<AreaEffectCloud> list, ItemStack itemstack, BlockHitResult hitResult, BlockPos blockPos) {
        if (itemstack.getCount() < 4) {
            cir.setReturnValue(InteractionResultHolder.pass(itemstack));
            player.displayClientMessage(Component.literal("You need four bottles in hand to pick up water in the world!"), true);
            cir.cancel();
            return;
        }

        BlockState blockState = level.getBlockState(blockPos);
        int purity = WaterPurity.getBlockPurity(level, blockPos);
        if (!(blockState.getBlock() instanceof BucketPickup bucketPickup)) {
            cir.setReturnValue(InteractionResultHolder.fail(itemstack));
            cir.cancel();
            return;
        }

        ItemStack picked = bucketPickup.pickupBlock(level, blockPos, blockState);
        if (picked.isEmpty()) {
            cir.setReturnValue(InteractionResultHolder.fail(itemstack));
            cir.cancel();
            return;
        }

        ItemStack currentStack = itemstack;
        for (int i = 0; i < 4; i++) {
            ItemStack waterPotion = PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.WATER);
            waterPotion = WaterPurity.addPurity(waterPotion, purity);
            currentStack = this.turnBottleIntoItem(currentStack, player, waterPotion);
        }

        level.gameEvent(player, GameEvent.FLUID_PICKUP, blockPos);

        cir.setReturnValue(InteractionResultHolder.sidedSuccess(currentStack, level.isClientSide()));
        cir.cancel();
    }
}
