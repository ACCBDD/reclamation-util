package com.accbdd.reclamation_util.mixins;

import com.klikli_dev.theurgy.integration.jei.recipes.AccumulationCategory;
import dev.ghen.thirst.content.purity.WaterPurity;
import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.gui.ingredient.IRecipeSlotTooltipCallback;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.contents.LiteralContents;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.List;
import java.util.Optional;

@Mixin(AccumulationCategory.class)
public class AccumulationRecipePatch {
    /**
     * @author ACCBDD
     * @reason add purity to fluid input
     */
    @Overwrite(remap = false)
    public static IRecipeSlotTooltipCallback addFluidTooltip(int overrideAmount) {
        return (view, tooltip) -> {
            Optional<FluidStack> displayed = view.getDisplayedIngredient(ForgeTypes.FLUID_STACK);
            if (displayed.isPresent()) {
                FluidStack fluidStack = displayed.get();
                int amount = overrideAmount == -1 ? fluidStack.getAmount() : overrideAmount;
                MutableComponent text = Component.translatable("theurgy.misc.unit.millibuckets", new Object[]{amount}).withStyle(ChatFormatting.GOLD);
                if (tooltip.isEmpty()) {
                    tooltip.add(0, text);
                } else {
                    List<Component> siblings = tooltip.get(0).getSiblings();
                    siblings.add(Component.literal(" "));
                    siblings.add(text);
                }
                if (fluidStack.getFluid().isSame(Fluids.WATER)) {
                    int purity = WaterPurity.getPurity(fluidStack);
                    String purityText = WaterPurity.getPurityText(purity);
                    int purityColor = WaterPurity.getPurityColor(purity);
                    tooltip.add(MutableComponent.create(new LiteralContents(purityText)).setStyle(Style.EMPTY.withColor(purityColor)));
                }
            }
        };
    }
}
