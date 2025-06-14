package com.accbdd.reclamation_util.plugin;

import com.agricraft.agricraft.api.crop.AgriCrop;
import com.agricraft.agricraft.api.plant.AgriPlantModifierFactoryRegistry;
import com.agricraft.agricraft.api.plant.IAgriPlantModifier;
import com.agricraft.agricraft.plugin.minecraft.MinecraftPlantModifiers;
import net.favouriteless.enchanted.common.Enchanted;
import net.favouriteless.enchanted.common.blocks.crops.MandrakeBlock;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class ReclamationPlantModifiers {

    public static void register() {
        AgriPlantModifierFactoryRegistry.register(SummonMandrakePlantModifier.ID, info -> Optional.of(new SummonMandrakePlantModifier()));

    }

    public static class SummonMandrakePlantModifier implements IAgriPlantModifier {
        public static final String ID = "reclamation:summon_mandrake";

        public SummonMandrakePlantModifier() {
        }

        public void onHarvest(AgriCrop crop, @Nullable LivingEntity entity) {
            if (entity instanceof Player player) {
                Level level = crop.getLevel();
                if (level instanceof ServerLevel && (Enchanted.RANDOM.nextInt(5) == 0 || level.isDay())) {
                    MandrakeBlock.spawnMandrake(level, crop.getBlockPos());
                }
            }

        }
    }
}
