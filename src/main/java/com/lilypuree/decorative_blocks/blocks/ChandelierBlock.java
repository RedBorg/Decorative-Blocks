package com.lilypuree.decorative_blocks.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Random;

public class ChandelierBlock extends Block {
    private final VoxelShape CHANDELIER_SHAPE = Block.createCuboidShape(2D, 0.0D, 2D, 14D, 12D, 14D);

    public ChandelierBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, EntityContext context) {
        return CHANDELIER_SHAPE;
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        double d0 = (double) pos.getX() + 0.5D;
        double d1 = (double) pos.getY() + 0.7D;
        double d2 = (double) pos.getZ() + 0.5D;

        double off1 = 0.1875;
        double off2 = 0.3125;
        double off3 = 0.0625;
        world.addParticle(ParticleTypes.SMOKE, d0 - off1, d1, d2 - off2, 0.0D, 0.0D, 0.0D);
        world.addParticle(ParticleTypes.SMOKE, d0 - off2 - off3, d1, d2 + off1 - off3, 0.0D, 0.0D, 0.0D);
        world.addParticle(ParticleTypes.SMOKE, d0 + off1 - off3, d1, d2 + off2 + off3, 0.0D, 0.0D, 0.0D);
        world.addParticle(ParticleTypes.SMOKE, d0 + off2, d1, d2 - off1, 0.0D, 0.0D, 0.0D);
        world.addParticle(ParticleTypes.FLAME, d0 - off1, d1, d2 - off2, 0.0D, 0.0D, 0.0D);
        world.addParticle(ParticleTypes.FLAME, d0 - off2 - off3, d1, d2 + off1 - off3, 0.0D, 0.0D, 0.0D);
        world.addParticle(ParticleTypes.FLAME, d0 + off1 - off3, d1, d2 + off2 + off3, 0.0D, 0.0D, 0.0D);
        world.addParticle(ParticleTypes.FLAME, d0 + off2, d1, d2 - off1, 0.0D, 0.0D, 0.0D);
    }

    @Override
    public int getLuminance(BlockState state) {
        return 15;
    }

}
