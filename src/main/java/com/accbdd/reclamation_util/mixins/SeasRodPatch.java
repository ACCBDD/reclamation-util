package com.accbdd.reclamation_util.mixins;

import dev.ghen.thirst.content.purity.WaterPurity;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.gameevent.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import vazkii.botania.common.item.rod.SeasRodItem;

@Mixin(SeasRodItem.class)
public class SeasRodPatch {
    @ModifyVariable(method = "use", at = @At("STORE"), remap = false)
    public CauldronInteraction modifyInteraction(CauldronInteraction value) {
        return (pBlockState, pLevel, pBlockPos, pPlayer, pHand, pStack) -> {
            if (!pLevel.isClientSide) {
                Item item = pStack.getItem();
                pPlayer.setItemInHand(pHand, ItemUtils.createFilledResult(pStack, pPlayer, new ItemStack(Items.BUCKET)));
                pPlayer.awardStat(Stats.FILL_CAULDRON);
                pPlayer.awardStat(Stats.ITEM_USED.get(item));
                pLevel.setBlockAndUpdate(pBlockPos, Blocks.WATER_CAULDRON.defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, 3).setValue(WaterPurity.BLOCK_PURITY, 2));
                pLevel.playSound(null, pBlockPos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
                pLevel.gameEvent(null, GameEvent.FLUID_PLACE, pBlockPos);
            }

            return InteractionResult.sidedSuccess(pLevel.isClientSide);
        };
    }
}
