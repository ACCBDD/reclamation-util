package com.accbdd.reclamation_util;

import com.accbdd.reclamation_util.datagen.DataGenerators;
import com.accbdd.reclamation_util.event.AreaBreakItemUsage;
import com.accbdd.reclamation_util.naturesaura.ReclaimEffect;
import com.accbdd.reclamation_util.particle.ColoredDripParticle;
import com.accbdd.reclamation_util.particle.ColoredLeafParticle;
import com.accbdd.reclamation_util.plugin.ReclamationPlantModifiers;
import com.accbdd.reclamation_util.register.BeeRegistration;
import com.accbdd.reclamation_util.register.Blocks;
import com.accbdd.reclamation_util.register.Items;
import com.accbdd.reclamation_util.register.Particles;
import com.mojang.logging.LogUtils;
import de.ellpeck.naturesaura.api.NaturesAuraAPI;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(ReclamationUtil.MODID)
public class ReclamationUtil {
    public static final String MODID = "reclamation_util";
    public static final Logger LOGGER = LogUtils.getLogger();

    public ReclamationUtil(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.addListener(AreaBreakItemUsage::onAreaBreakItemUsage);

        modEventBus.addListener(ReclamationUtil::onCommonSetup);
        modEventBus.addListener(DataGenerators::generate);

        Particles.REGISTER.register(modEventBus);
        Items.REGISTER.register(modEventBus);
        Blocks.REGISTER.register(modEventBus);
        Items.CREATIVE_MODE_TAB.register(modEventBus);
        BeeRegistration.EFFECTS.register(modEventBus);
    }

    public static void onCommonSetup(FMLCommonSetupEvent event) {
        ReclamationPlantModifiers.register();
        NaturesAuraAPI.DRAIN_SPOT_EFFECTS.put(ReclaimEffect.NAME, ReclaimEffect::new);
        NaturesAuraAPI.EFFECT_POWDERS.put(ReclaimEffect.NAME, 0xFFCC00);
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
            event.registerSprite(Particles.COLORED_DRIP_HANG.get(),
                    new ColoredDripParticle.Provider());
            event.registerSprite(Particles.TWO_COLORED_DRIP_HANG.get(),
                    new ColoredDripParticle.TwoProvider());
            event.registerSprite(Particles.COLORED_DRIP_FALL.get(),
                    new ColoredDripParticle.Provider());
            event.registerSprite(Particles.COLORED_DRIP_LAND.get(),
                    new ColoredDripParticle.Provider());
            event.registerSpriteSet(Particles.COLORED_LEAF_TYPE.get(), set -> (options, pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed) ->
                    new ColoredLeafParticle(pLevel, pX, pY, pZ, set, options));
        }
    }
}
