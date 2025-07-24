package com.accbdd.reclamation_util.register;

import com.accbdd.reclamation_util.block.FlimsyDoor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.accbdd.reclamation_util.ReclamationUtil.MODID;

public class Blocks {
    public static final DeferredRegister<Block> REGISTER = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);

    public static final RegistryObject<DoorBlock> FLIMSY_DOOR = REGISTER.register("flimsy_door", () -> door(BlockSetType.OAK, net.minecraft.world.level.block.Blocks.OAK_PLANKS));

    private static DoorBlock door(BlockSetType type, Block base) {
        return new FlimsyDoor(BlockBehaviour.Properties.copy(base).noOcclusion(), type);
    }
}
