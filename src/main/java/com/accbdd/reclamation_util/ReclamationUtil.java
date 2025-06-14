package com.accbdd.reclamation_util;

import com.accbdd.reclamation_util.particle.ColoredDripParticle;
import com.accbdd.reclamation_util.plugin.ReclamationPlantModifiers;
import com.accbdd.reclamation_util.register.Particles;
import com.agricraft.agricraft.forge.AgriCraftForge;
import com.mojang.logging.LogUtils;
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
public class ReclamationUtil
{
    public static final String MODID = "reclamation_util";
    private static final Logger LOGGER = LogUtils.getLogger();

    public ReclamationUtil(FMLJavaModLoadingContext context)
    {
        IEventBus modEventBus = context.getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);
        Particles.REGISTER.register(modEventBus);
        modEventBus.addListener(ReclamationUtil::onCommonSetup);
    }

    public static void onCommonSetup(FMLCommonSetupEvent event) {
        ReclamationPlantModifiers.register();
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
        }
    }
}
