package com.lilypuree.decorative_blocks.blocks;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.sound.BlockSoundGroup;

public class RockyDirtBlock extends Block {
    public RockyDirtBlock() {
        super(FabricBlockSettings.of(Material.EARTH, MaterialColor.STONE).strength(1.0F, 6.0F).sounds(BlockSoundGroup.GRASS).build());
    }


    // TODO: Register somewhere in PlantBlock's list, probably a mixin
    /*
    @Override
    public boolean canSustainPlant(BlockState state, IBlockReader world, BlockPos pos, Direction facing, IPlantable plantable) {
        return true;
    }
    */
}
