package com.lilypuree.decorative_blocks.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.PillarBlock;

public class BeamBlock extends PillarBlock {

    public BeamBlock(Block.Settings settings){
        super(settings);
    }

    /* TODO: use Fabric's FlammableBlockRegistry
    @Override
    public boolean isFlammable(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return true;
    }

    @Override
    public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return 20;
    }
     */
}
