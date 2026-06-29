package com.accbdd.reclamation_util.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.ByteTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.accbdd.reclamation_util.ReclamationUtil.MODID;

public class GlobeCountCapability implements ICapabilitySerializable<ByteTag> {
    public static final Capability<GlobeCountCapability> GLOBE_COUNT = CapabilityManager.get(new CapabilityToken<>() {});
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(MODID, "globe_count");

    private Data data;

    public Data getData() {
        if (this.data == null) {
            this.data = new Data((byte) 0);
        }
        return this.data;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == GLOBE_COUNT) {
            return LazyOptional.of(() -> this).cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public ByteTag serializeNBT() {
        return ByteTag.valueOf(getData().count);
    }

    @Override
    public void deserializeNBT(ByteTag nbt) {
        this.data = new Data(nbt.getAsByte());
    }

    public static class Data {
        public byte count;

        public Data(byte value) {
            this.count = value;
        }
    }
}
