package com.accbdd.reclamation_util.datagen;

import com.accbdd.reclamation_util.register.Blocks;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import static com.accbdd.reclamation_util.ReclamationUtil.MODID;
import static com.accbdd.reclamation_util.datagen.DataGenerators.loc;

public class BlockStateGenerator extends BlockStateProvider {
    public BlockStateGenerator(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MODID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        doorBlockWithRenderType(Blocks.FLIMSY_DOOR.get(), loc("block/flimsy_door_bottom"), loc("block/flimsy_door_top"), "cutout");
    }
}
