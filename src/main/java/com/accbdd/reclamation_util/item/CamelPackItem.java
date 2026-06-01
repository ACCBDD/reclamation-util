package com.accbdd.reclamation_util.item;

import dev.ghen.thirst.content.purity.WaterPurity;
import dev.ghen.thirst.foundation.common.capability.IThirst;
import dev.ghen.thirst.foundation.common.capability.ModCapabilities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.contents.LiteralContents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CamelPackItem extends Item {
    private static final int SIP_SIZE = 100;
    private static final float SIP_THIRST = 5f * ((float) SIP_SIZE / 250);
    private static final float SIP_QUENCH = 8f * ((float) SIP_SIZE / 250);


    public final int capacity;
    public final float thirstMod;
    public final float quenchMod;
    public final boolean infinite;

    public CamelPackItem(Properties pProperties, int capacity, float thirstMod, float quenchMod, boolean infinite) {
        super(pProperties);
        this.capacity = capacity;
        this.thirstMod = thirstMod;
        this.quenchMod = quenchMod;
        this.infinite = infinite;
    }

    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return CuriosApi.createCurioProvider(new ICurio() {

            @Override
            public ItemStack getStack() {
                return stack;
            }

            @Override
            public void curioTick(SlotContext slotContext) {
                if (!(slotContext.entity() instanceof Player player))
                    return;

                if (player.getCooldowns().isOnCooldown(CamelPackItem.this)) {
                    return;
                }

                LazyOptional<IThirst> thirstCap = player.getCapability(ModCapabilities.PLAYER_THIRST);
                LazyOptional<IFluidHandlerItem> fluidCap = getStack().getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM);

                if (thirstCap.resolve().isPresent() && (fluidCap.resolve().isPresent() || CamelPackItem.this.infinite)) {
                    IThirst thirst = thirstCap.resolve().get();
                    if (CamelPackItem.this.infinite) {
                        if (thirst.getThirst() < 18) {
                            if (!player.level().isClientSide)
                                thirst.drink(player, (int) (SIP_THIRST * CamelPackItem.this.thirstMod), (int) (SIP_QUENCH * CamelPackItem.this.quenchMod));
                            player.playSound(SoundEvents.GENERIC_DRINK, 0.5f, 1);
                        }
                    } else {
                        if (thirst.getThirst() < 18) {
                            FluidStack fluid = fluidCap.resolve().get().drain(SIP_SIZE, IFluidHandler.FluidAction.SIMULATE);
                            if (fluid.getAmount() == 0)
                                return;
                            player.playSound(SoundEvents.GENERIC_DRINK, 0.5f, 1);
                            float drinkAmount = Math.min(SIP_SIZE, fluid.getAmount());
                            float drinkRatio = drinkAmount / SIP_SIZE;
                            if (!player.level().isClientSide) {
                                if (fluid.getFluid().isSame(Fluids.WATER)) {
                                    WaterPurity.givePurityEffects(player, Math.min(3, WaterPurity.getPurity(fluid) + 1));
                                    thirst.drink(player, (int) (SIP_THIRST * drinkRatio * CamelPackItem.this.thirstMod), (int) (SIP_QUENCH * drinkRatio * CamelPackItem.this.quenchMod));
                                    player.getCooldowns().addCooldown(CamelPackItem.this, 20);
                                    fluidCap.resolve().get().drain(SIP_SIZE, IFluidHandler.FluidAction.EXECUTE);
                                }
                            }

                        }
                    }
                }
            }
        });
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pStack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).ifPresent(fluidCap -> {
            FluidStack fluid = fluidCap.getFluidInTank(0);
            if (!fluid.isEmpty())
                pTooltipComponents.add(fluid.getDisplayName());
            pTooltipComponents.add(Component.literal(fluid.getAmount() + " / " + this.capacity));
            if (fluid.getFluid().isSame(Fluids.WATER)) {
                int purity = WaterPurity.getPurity(fluid);
                String purityText = WaterPurity.getPurityText(purity);
                int purityColor = WaterPurity.getPurityColor(purity);
                pTooltipComponents
                        .add(MutableComponent
                                .create(new LiteralContents(purityText))
                                .setStyle(Style.EMPTY.withColor(purityColor)));
            }
        });
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    @Override
    public boolean isBarVisible(ItemStack pStack) {
        return !this.infinite;
    }

    @Override
    public int getBarWidth(ItemStack pStack) {
        AtomicInteger width = new AtomicInteger();
        pStack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).resolve().ifPresent(handler -> width.set(Math.round((float) handler.getFluidInTank(0).getAmount() / this.capacity * 13)));
        return width.get();
    }

    @Override
    public int getBarColor(ItemStack pStack) {
        return 0x3F76E4;
    }
}
