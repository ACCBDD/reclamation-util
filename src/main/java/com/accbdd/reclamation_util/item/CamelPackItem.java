package com.accbdd.reclamation_util.item;

import dev.ghen.thirst.content.purity.WaterPurity;
import dev.ghen.thirst.content.registry.ItemInit;
import dev.ghen.thirst.foundation.common.capability.IThirst;
import dev.ghen.thirst.foundation.common.capability.ModCapabilities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.contents.LiteralContents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.*;
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
        super(pProperties.stacksTo(1));
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
                            if (!player.level().isClientSide) {
                                thirst.drink(player, (int) (SIP_THIRST * CamelPackItem.this.thirstMod), (int) (SIP_QUENCH * CamelPackItem.this.quenchMod));
                                player.getCooldowns().addCooldown(CamelPackItem.this, 20);
                            }
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

    @Override
    public boolean isFoil(ItemStack pStack) {
        return infinite;
    }

    @Override
    public Rarity getRarity(ItemStack pStack) {
        if (infinite) {
            return Rarity.RARE;
        }
        return Rarity.COMMON;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (pUsedHand != InteractionHand.MAIN_HAND)
            return InteractionResultHolder.pass(pPlayer.getItemInHand(pUsedHand));

        ItemStack offhand = pPlayer.getItemInHand(InteractionHand.OFF_HAND);
        ItemStack mainhand = pPlayer.getItemInHand(InteractionHand.MAIN_HAND);

        if (!WaterPurity.isWaterFilledContainer(offhand))
            return InteractionResultHolder.pass(mainhand);

        LazyOptional<IFluidHandlerItem> packFluid = mainhand.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM);
        if (!packFluid.isPresent() || packFluid.resolve().isEmpty())
            return InteractionResultHolder.pass(mainhand);

        IFluidHandlerItem handler = packFluid.resolve().get();
        FluidStack packContents = handler.getFluidInTank(0);

        int offhandPurity = WaterPurity.getPurity(offhand);

        if (!packContents.isEmpty()) {
            int packPurity = WaterPurity.getPurity(packContents);
            if (packPurity != offhandPurity)
                return InteractionResultHolder.fail(mainhand);
        }

        int amount = offhand.is(Items.WATER_BUCKET) ? 1000 : 250;
        FluidStack toFill = WaterPurity.addPurity(new FluidStack(Fluids.WATER, amount), offhandPurity);

        int filled = handler.fill(toFill, IFluidHandler.FluidAction.SIMULATE);
        if (filled == 0)
            return InteractionResultHolder.fail(mainhand);

        if (!pLevel.isClientSide) {
            handler.fill(toFill, IFluidHandler.FluidAction.EXECUTE);

            ItemStack empty = ItemStack.EMPTY;
            if (offhand.is(Items.WATER_BUCKET)) {
                empty = Items.BUCKET.getDefaultInstance();
            } else if (offhand.is(Items.POTION)) {
                empty = Items.GLASS_BOTTLE.getDefaultInstance();
            } else if (offhand.is(ItemInit.TERRACOTTA_WATER_BOWL.get())) {
                empty = ItemInit.TERRACOTTA_BOWL.get().getDefaultInstance();
            }
            offhand.shrink(1);
            pPlayer.addItem(empty);
        }
        pLevel.playSound(pPlayer, pPlayer.blockPosition(), SoundEvents.BUCKET_EMPTY, SoundSource.PLAYERS, 1f, 1f);
        return InteractionResultHolder.success(mainhand);
    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack stack, ItemStack other, Slot slot, ClickAction action, Player player, SlotAccess access) {
        if (action != ClickAction.SECONDARY)
            return false;

        if (!WaterPurity.isWaterFilledContainer(other))
            return false;

        LazyOptional<IFluidHandlerItem> packFluid = stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM);
        if (packFluid.resolve().isEmpty())
            return false;

        IFluidHandlerItem handler = packFluid.resolve().get();
        FluidStack packContents = handler.getFluidInTank(0);

        int offhandPurity = WaterPurity.getPurity(other);

        if (!packContents.isEmpty() && WaterPurity.getPurity(packContents) != offhandPurity)
            return false;

        int amount = other.is(Items.WATER_BUCKET) ? 1000 : 250;
        FluidStack toFill = WaterPurity.addPurity(new FluidStack(Fluids.WATER, amount), offhandPurity);

        if (handler.fill(toFill, IFluidHandler.FluidAction.SIMULATE) == 0)
            return false;

        handler.fill(toFill, IFluidHandler.FluidAction.EXECUTE);
        ItemStack empty = ItemStack.EMPTY;
        if (other.is(Items.WATER_BUCKET)) {
            empty = Items.BUCKET.getDefaultInstance();
        } else if (other.is(Items.POTION)) {
            empty = Items.GLASS_BOTTLE.getDefaultInstance();
        } else if (other.is(ItemInit.TERRACOTTA_WATER_BOWL.get())) {
            empty = ItemInit.TERRACOTTA_BOWL.get().getDefaultInstance();
        }
        other.shrink(1);
        if (other.isEmpty() || other.getCount() == 0) {
            access.set(empty);
        } else {
            player.addItem(empty);
        }

        slot.set(stack);

        player.level().playSound(player, player.blockPosition(), SoundEvents.BUCKET_EMPTY, SoundSource.PLAYERS, 1f, 1f);
        return true;
    }
}
