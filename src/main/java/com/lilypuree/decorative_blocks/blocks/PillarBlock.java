package com.lilypuree.decorative_blocks.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Waterloggable;
import net.minecraft.entity.EntityContext;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class PillarBlock extends Block implements Waterloggable {
    private final VoxelShape PILLAR_SHAPE = Block.createCuboidShape(2D, 0.0D, 2D, 14D, 16.0D, 14D);

    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;


    public PillarBlock(Settings properties) {
        super(properties);
        this.setDefaultState(this.getStateManager().getDefaultState().with(WATERLOGGED, Boolean.valueOf(false)));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, EntityContext context) {
        return PILLAR_SHAPE;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockstate = ctx.getWorld().getBlockState(ctx.getBlockPos());
        FluidState ifluidstate = ctx.getWorld().getFluidState(ctx.getBlockPos());
        boolean flag = ifluidstate.matches(FluidTags.WATER) && ifluidstate.getLevel() == 8;

        return this.getDefaultState().with(WATERLOGGED, Boolean.valueOf(flag));
    }

    // TODO: implement this (propagatesSkylightDown)
    public boolean propagatesSkylightDown(BlockState state, BlockView reader, BlockPos pos) {
        return !state.get(WATERLOGGED);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(WATERLOGGED);
    }
}
