package com.lilypuree.decorative_blocks.blocks;

import jdk.internal.jline.internal.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
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
import net.minecraft.world.BlockView;

public class ChainBlock extends PillarBlock implements Waterloggable {
    private static final double d0 = 4D;
    private static final double d1 = 12D;
    private static final VoxelShape CHAIN_SHAPE_X = Block.createCuboidShape(0, d0, d0, 16, d1, d1);
    private static final VoxelShape CHAIN_SHAPE_Y = Block.createCuboidShape(d0, 0, d0, d1, 16, d1);
    private static final VoxelShape CHAIN_SHAPE_Z = Block.createCuboidShape(d0, d0, 0, d1, d1, 16);

    private static final VoxelShape CHAIN_COLLISION_SHAPE = Block.createCuboidShape(6D, 0D, 6D, 10D, 16, 10D);

    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    public ChainBlock(Block.Settings settings) {
        super(settings);
        this.setDefaultState(this.getStateManager().getDefaultState().with(WATERLOGGED, Boolean.valueOf(false)));
    }

    // TODO: Figure why the hell this is deprecated, probably an error
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView worldIn, BlockPos pos, EntityContext context) {
        Direction.Axis axis = state.get(AXIS);
        switch (axis) {
            case X:
                return CHAIN_SHAPE_X;
            case Z:
                return CHAIN_SHAPE_Z;
            case Y:
            default:
                return CHAIN_SHAPE_Y;
        }
    }

    // TODO: Figure why the hell this is deprecated, probably an error
    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, EntityContext context) {
        if (state.get(AXIS) == Direction.Axis.Y) {
            return CHAIN_COLLISION_SHAPE;
        }
        return super.getCollisionShape(state, world, pos, context);
    }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockstate = ctx.getWorld().getBlockState(ctx.getBlockPos());
        FluidState fluidstate = ctx.getWorld().getFluidState(ctx.getBlockPos());
        boolean flag = fluidstate.matches(FluidTags.WATER) && fluidstate.getLevel() == 8;
        return super.getPlacementState(ctx).with(WATERLOGGED, Boolean.valueOf(flag));
    }

    /* TODO: Implement ladder
    @Override
    public boolean isLadder(BlockState state, IWorldReader world, BlockPos pos, LivingEntity entity) {
        return state.get(AXIS) == Direction.Axis.Y;
    }
     */

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
