package com.accbdd.reclamation_util.network;

import com.accbdd.reclamation_util.capability.GlobeCountCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ClientboundGlobeSyncPacket {
    private final byte count;

    public ClientboundGlobeSyncPacket(byte count) {
        this.count = count;
    }

    public static void toNetwork(ClientboundGlobeSyncPacket packet, FriendlyByteBuf buf) {
        buf.writeByte(packet.count);
    }

    public static ClientboundGlobeSyncPacket fromNetwork(FriendlyByteBuf buf) {
        return new ClientboundGlobeSyncPacket(buf.readByte());
    }

    public static void handle(ClientboundGlobeSyncPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> DistExecutor.unsafeCallWhenOn(Dist.CLIENT, () -> () -> {
            if (Minecraft.getInstance().player != null) {
                Minecraft.getInstance().player.getCapability(GlobeCountCapability.GLOBE_COUNT).ifPresent(cap -> cap.getData().count = packet.count);
            }
            return null;
        }));
        ctx.get().setPacketHandled(true);
    }
}
