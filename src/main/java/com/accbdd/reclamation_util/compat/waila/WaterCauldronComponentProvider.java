package com.accbdd.reclamation_util.compat.waila;

import dev.ghen.thirst.content.purity.WaterPurity;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.contents.LiteralContents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

import static com.accbdd.reclamation_util.ReclamationUtil.MODID;

public enum WaterCauldronComponentProvider implements IBlockComponentProvider {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
        BlockState state = blockAccessor.getBlockState();
        if (blockAccessor.getBlock() == Blocks.WATER_CAULDRON && state.hasProperty(WaterPurity.BLOCK_PURITY) && state.getValue(WaterPurity.BLOCK_PURITY) > 0) {
            int purity = state.getValue(WaterPurity.BLOCK_PURITY) - 1;
            String purityText = WaterPurity.getPurityText(purity);
            int purityColor = WaterPurity.getPurityColor(purity);
            iTooltip.add(MutableComponent.create(new LiteralContents(purityText)).setStyle(Style.EMPTY.withColor(purityColor)));
        }
    }

    @Override
    public ResourceLocation getUid() {
        return ResourceLocation.fromNamespaceAndPath(MODID, "cauldron_purity");
    }
}
