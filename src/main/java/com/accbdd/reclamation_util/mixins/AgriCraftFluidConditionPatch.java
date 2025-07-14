package com.accbdd.reclamation_util.mixins;

import com.agricraft.agricraft.api.codecs.AgriFluidCondition;
import com.agricraft.agricraft.api.requirement.AgriGrowthConditionRegistry;
import com.agricraft.agricraft.api.requirement.AgriGrowthResponse;
import com.agricraft.agricraft.common.util.Platform;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.block.state.StateHolder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mixin(AgriGrowthConditionRegistry.class)
public class AgriCraftFluidConditionPatch {
    @Shadow(remap = false)
    private final AgriGrowthConditionRegistry.BaseGrowthCondition<FluidState> fluid = new AgriGrowthConditionRegistry.BaseGrowthCondition<>("fluid", (plant, strength, fluid) -> {
        AgriFluidCondition fluidCondition = plant.getGrowthRequirements().fluidCondition();
        List<Fluid> requiredFluids = Platform.get().getFluidsFromLocation(fluidCondition.fluid());
        if (requiredFluids.isEmpty()) {
            if (fluid.is(Fluids.LAVA)) {
                return AgriGrowthResponse.KILL_IT_WITH_FIRE;
            } else {
                return fluid.is(Fluids.EMPTY) ? AgriGrowthResponse.FERTILE : AgriGrowthResponse.INFERTILE;
            }
        } else if (requiredFluids.contains(fluid.getType())) {
            if (fluidCondition.states().isEmpty()) {
                return AgriGrowthResponse.FERTILE;
            } else {
               Set<String> list = (Set) fluid.getValues().entrySet().stream().map(StateHolder.PROPERTY_ENTRY_TO_STRING_FUNCTION).collect(Collectors.toSet());
                return list.containsAll(fluidCondition.states()) ? AgriGrowthResponse.FERTILE : AgriGrowthResponse.INFERTILE;
            }
        } else {
            return fluid.is(Fluids.LAVA) ? AgriGrowthResponse.FERTILE : AgriGrowthResponse.INFERTILE;
        }
    }, Level::getFluidState);
}
