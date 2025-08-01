package com.accbdd.reclamation_util.item;

import mekanism.tools.common.ToolsTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import vazkii.botania.api.item.SortableTool;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.item.equipment.CustomDamageItem;
import vazkii.botania.common.item.equipment.tool.ToolCommons;
import vazkii.botania.common.item.equipment.tool.manasteel.ManasteelAxeItem;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;
import java.util.function.Consumer;

public class ManasteelPaxelItem extends DiggerItem implements CustomDamageItem, SortableTool {
    private static final int MANA_PER_DAMAGE = 60;
    private static final ToolAction PAXEL_DIG = ToolAction.get("paxel_dig");
    private static final Set<ToolAction> PAXEL_ACTIONS = Collections.newSetFromMap(new IdentityHashMap<>());

    static {
        PAXEL_ACTIONS.add(PAXEL_DIG);
        PAXEL_ACTIONS.addAll(ToolActions.DEFAULT_PICKAXE_ACTIONS);
        PAXEL_ACTIONS.addAll(ToolActions.DEFAULT_SHOVEL_ACTIONS);
        PAXEL_ACTIONS.addAll(ToolActions.DEFAULT_AXE_ACTIONS);
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return super.getMaxDamage(stack) * 2;
    }

    public ManasteelPaxelItem(Tier mat, float attackDamage, float attackSpeed, Item.Properties props) {
        super(attackDamage, attackSpeed, mat, ToolsTags.Blocks.MINEABLE_WITH_PAXEL, props);
    }

    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
        return ToolCommons.damageItemIfPossible(stack, amount, entity, MANA_PER_DAMAGE);
    }

    public int getManaPerDamage() {
        return 60;
    }

    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
        if (!world.isClientSide && entity instanceof Player player) {
            if (stack.getDamageValue() > 0 && ManaItemHandler.instance().requestManaExactForTool(stack, player, this.getManaPerDamage() * 2, true)) {
                stack.setDamageValue(stack.getDamageValue() - 1);
            }
        }

    }

    public int getSortingPriority(ItemStack stack, BlockState state) {
        return ToolCommons.getToolPriority(stack);
    }
}
