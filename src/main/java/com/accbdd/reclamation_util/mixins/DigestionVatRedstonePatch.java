package com.accbdd.reclamation_util.mixins;

import com.klikli_dev.theurgy.content.apparatus.digestionvat.DigestionVatBlock;
import com.klikli_dev.theurgy.content.apparatus.digestionvat.DigestionVatBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemHandlerHelper;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(DigestionVatBlock.class)
public abstract class DigestionVatRedstonePatch extends Block {

    public DigestionVatRedstonePatch(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState pState, Level pLevel, BlockPos pPos) {
        BlockEntity be = pLevel.getBlockEntity(pPos);
        if (be instanceof DigestionVatBlockEntity vat) {
            return vat.craftingBehaviour.canCraft(vat.craftingBehaviour.getRecipe().orElse(null)) ? ItemHandlerHelper.calcRedstoneFromInventory(vat.storageBehaviour.inputInventory) : 0;
        }
        return 0;
    }
}
