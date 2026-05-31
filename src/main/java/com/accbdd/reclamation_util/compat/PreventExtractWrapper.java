package com.accbdd.reclamation_util.compat;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.jetbrains.annotations.NotNull;

public class PreventExtractWrapper implements IItemHandlerModifiable {
    protected final IItemHandlerModifiable compose;

    public PreventExtractWrapper(IItemHandlerModifiable compose) {
        this.compose = compose;
    }

    public int getSlots() {
        return this.compose.getSlots();
    }

    public @NotNull ItemStack getStackInSlot(int slot) {
        return this.compose.getStackInSlot(slot);
    }

    @Override
    public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
        return this.compose.insertItem(slot, stack, simulate);
    }

    public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
        return ItemStack.EMPTY;
    }

    public void setStackInSlot(int slot, @NotNull ItemStack stack) {
        this.compose.setStackInSlot(slot, stack);
    }

    public int getSlotLimit(int slot) {
        return this.compose.getSlotLimit(slot);
    }

    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        return this.compose.isItemValid(slot, stack);
    }
}