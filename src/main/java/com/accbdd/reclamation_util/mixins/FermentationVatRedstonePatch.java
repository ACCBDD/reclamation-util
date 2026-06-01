package com.accbdd.reclamation_util.mixins;

import com.klikli_dev.theurgy.content.apparatus.fermentationvat.FermentationVatBlock;
import com.klikli_dev.theurgy.content.apparatus.fermentationvat.FermentationVatBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemHandlerHelper;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(FermentationVatBlock.class)
public abstract class FermentationVatRedstonePatch extends Block {

    public FermentationVatRedstonePatch(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState pState, Level pLevel, BlockPos pPos) {
        BlockEntity be = pLevel.getBlockEntity(pPos);
        if (be instanceof FermentationVatBlockEntity vat) {
            return vat.craftingBehaviour.canCraft(vat.craftingBehaviour.getRecipe().orElse(null)) ? ItemHandlerHelper.calcRedstoneFromInventory(vat.storageBehaviour.inputInventory) : 0;
        }
        return 0;
    }
}
