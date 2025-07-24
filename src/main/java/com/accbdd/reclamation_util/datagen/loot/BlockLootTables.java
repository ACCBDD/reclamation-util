package com.accbdd.reclamation_util.datagen.loot;

import com.accbdd.reclamation_util.register.Blocks;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.Collections;

public class BlockLootTables extends BlockLootSubProvider {
    public BlockLootTables() {
        super(Collections.emptySet(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        this.add(Blocks.FLIMSY_DOOR.get(), createDoorTable(Blocks.FLIMSY_DOOR.get()));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return Blocks.REGISTER.getEntries()
                .stream()
                .map(RegistryObject::get)
                .toList();
    }
}
