package com.accbdd.reclamation_util.compat.waila;

import net.minecraft.world.level.block.LayeredCauldronBlock;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class ReclamationUtilPlugin implements IWailaPlugin {
    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(WaterCauldronComponentProvider.INSTANCE, LayeredCauldronBlock.class);
    }
}
