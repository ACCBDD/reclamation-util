package com.accbdd.reclamation_util.plugin;

import com.agricraft.agricraft.api.crop.AgriCrop;
import com.agricraft.agricraft.api.crop.AgriGrowthStage;
import com.agricraft.agricraft.api.plant.AgriPlantModifierFactoryRegistry;
import com.agricraft.agricraft.api.plant.IAgriPlantModifier;
import net.favouriteless.enchanted.common.Enchanted;
import net.favouriteless.enchanted.common.entities.Mandrake;
import net.favouriteless.enchanted.common.init.registry.EEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
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

        public Optional<InteractionResult> onRightClickPre(AgriCrop crop, ItemStack stack, @Nullable Entity breaker) {
            if (crop.isFullyGrown()) {
                if (breaker instanceof Player player) {
                    Level level = crop.getLevel();
                    if (level instanceof ServerLevel && (Enchanted.RANDOM.nextInt(5) == 0 || level.isDay())) {
                        Mandrake entity = (Mandrake) ((EntityType) EEntityTypes.MANDRAKE.get()).create(level);
                        BlockPos pos = crop.getBlockPos();
                        entity.moveTo((double) pos.getX() + 0.5, (double) pos.getY(), (double) pos.getZ() + 0.5, 0.0F, 0.0F);
                        level.addFreshEntity(entity);
                        crop.setGrowthStage(new AgriGrowthStage(1, crop.getGrowthStage().total()));
                        return Optional.of(InteractionResult.CONSUME);
                    }
                }
            }
            return Optional.empty();
        }
    }
}
