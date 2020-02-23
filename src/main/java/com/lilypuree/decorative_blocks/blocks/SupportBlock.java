package com.lilypuree.decorative_blocks.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
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
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

import static net.minecraft.state.property.Properties.HORIZONTAL_FACING;

public class SupportBlock extends HorizontalFacingBlock implements Waterloggable {
    private static final double d0 = 3D;
    private static final double d1 = 13D;
    private static final double d2 = 4D;
    private static final double d3 = 12D;
    public static final VoxelShape TOP = Block.createCuboidShape(0, d1, 0, 16D, 16D, 16D);
    public static final VoxelShape NORTH_PART = Block.createCuboidShape(d2, 0, d1, d3, d1, 16D);
    public static final VoxelShape SOUTH_PART = Block.createCuboidShape(d2, 0, 0, d3, d1, d0);
    public static final VoxelShape EAST_PART = Block.createCuboidShape(0, 0, d2, d0, d1, d3);
    public static final VoxelShape WEST_PART = Block.createCuboidShape(d1, 0, d2, 16D, d1, d3);
    public static final VoxelShape NORTH_SHAPE = VoxelShapes.union(TOP, NORTH_PART);
    public static final VoxelShape SOUTH_SHAPE = VoxelShapes.union(TOP, SOUTH_PART);
    public static final VoxelShape EAST_SHAPE = VoxelShapes.union(TOP, EAST_PART);
    public static final VoxelShape WEST_SHAPE = VoxelShapes.union(TOP, WEST_PART);
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    public SupportBlock(Block.Settings properties) {
        super(properties);
        this.setDefaultState(this.getStateManager().getDefaultState().with(FACING, Direction.NORTH).with(WATERLOGGED, Boolean.FALSE));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, EntityContext context) {
        Direction facing = state.get(FACING);
        switch (facing) {
            case NORTH:
                return NORTH_SHAPE;
            case SOUTH:
                return SOUTH_SHAPE;
            case EAST:
                return EAST_SHAPE;
            case WEST:
                return WEST_SHAPE;
        }
        return NORTH_SHAPE;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockstate = ctx.getWorld().getBlockState(ctx.getBlockPos());
        FluidState fluidstate = ctx.getWorld().getFluidState(ctx.getBlockPos());
        boolean flag = fluidstate.matches(FluidTags.WATER) && fluidstate.getLevel() == 8;

        return this.getDefaultState().with(HORIZONTAL_FACING, ctx.getPlayerFacing().getOpposite()).with(WATERLOGGED, flag);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(HORIZONTAL_FACING, WATERLOGGED);
    }

    // TODO: implement this (propagatesSkylightDown)
    public boolean propagatesSkylightDown(BlockState state, BlockView reader, BlockPos pos) {
        return !state.get(WATERLOGGED);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    /* TODO: Implement Fabric's FlammableBlockRegistry
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
