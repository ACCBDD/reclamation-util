package com.accbdd.reclamation_util.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import static com.accbdd.reclamation_util.ReclamationUtil.MODID;

public class ReclamationPacketHandler {
    public static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            ResourceLocation.fromNamespaceAndPath(MODID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    private static int id = 0;

    public static void register() {
        INSTANCE.registerMessage(id++,
                ClientboundGlobeSyncPacket.class,
                ClientboundGlobeSyncPacket::toNetwork,
                ClientboundGlobeSyncPacket::fromNetwork,
                ClientboundGlobeSyncPacket::handle);
    }

    public static void sendToPlayer(byte count, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new ClientboundGlobeSyncPacket(count));
    }
}
