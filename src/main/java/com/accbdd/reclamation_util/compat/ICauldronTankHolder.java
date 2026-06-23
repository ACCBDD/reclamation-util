package com.accbdd.reclamation_util.compat;

import com.blakebr0.cucumber.iface.IFluidHolder;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public interface ICauldronTankHolder {
    FluidTank reclamation_util$getTank();

    LazyOptional<IFluidHandler> reclamation_util$getFluidHandler();
}
