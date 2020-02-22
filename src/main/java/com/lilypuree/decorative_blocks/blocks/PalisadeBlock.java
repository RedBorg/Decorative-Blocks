package com.lilypuree.decorative_blocks.blocks;

import net.minecraft.block.*;
import net.minecraft.entity.ai.pathing.PathNode;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.IWorld;

public class PalisadeBlock extends HorizontalConnectingBlock {

    public PalisadeBlock(Block.Settings settings) {
        super(3.0F, 3.0F, 16.0F, 16.0F, 24.0F, settings);
        this.setDefaultState(this.getStateManager().getDefaultState().with(NORTH, Boolean.FALSE).with(EAST, Boolean.FALSE).with(SOUTH, Boolean.FALSE).with(WEST, Boolean.FALSE).with(WATERLOGGED, Boolean.FALSE));
    }


    // TODO: implement this?
    public boolean allowsMovement(BlockState state, BlockView world, BlockPos pos, PathNode type) {
        return false;
    }

    public boolean canConnect(BlockState state, boolean flag0, Direction direction) {
        Block block = state.getBlock();
//        boolean flag = block.isIn(BlockTags.FENCES) && p_220111_1_.getMaterial() == this.material;
                                                   // TODO: check if identical to original's 'isParallel'  vvvvvvvvvvvvvv
        boolean flag = block instanceof PalisadeBlock || block instanceof FenceGateBlock && FenceGateBlock.canWallConnect(state, direction);
        return !cannotConnect(block) && flag0 || flag;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockView blockreader = ctx.getWorld();
        BlockPos blockpos = ctx.getBlockPos();
        FluidState ifluidstate = ctx.getWorld().getFluidState(ctx.getBlockPos());
        BlockPos blockpos1 = blockpos.north();
        BlockPos blockpos2 = blockpos.east();
        BlockPos blockpos3 = blockpos.south();
        BlockPos blockpos4 = blockpos.west();
        BlockState blockstate = blockreader.getBlockState(blockpos1);
        BlockState blockstate1 = blockreader.getBlockState(blockpos2);
        BlockState blockstate2 = blockreader.getBlockState(blockpos3);
        BlockState blockstate3 = blockreader.getBlockState(blockpos4);
        return super.getPlacementState(ctx)
                .with(NORTH, Boolean.valueOf(this.canConnect(blockstate, blockstate.isSideSolidFullSquare(blockreader, blockpos1, Direction.SOUTH), Direction.SOUTH)))
                .with(EAST, Boolean.valueOf(this.canConnect(blockstate1, blockstate1.isSideSolidFullSquare(blockreader, blockpos2, Direction.WEST), Direction.WEST)))
                .with(SOUTH, Boolean.valueOf(this.canConnect(blockstate2, blockstate2.isSideSolidFullSquare(blockreader, blockpos3, Direction.NORTH), Direction.NORTH)))
                .with(WEST, Boolean.valueOf(this.canConnect(blockstate3, blockstate3.isSideSolidFullSquare(blockreader, blockpos4, Direction.EAST), Direction.EAST)))
                .with(WATERLOGGED, Boolean.valueOf(ifluidstate.getFluid() == Fluids.WATER));
    }


    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction facing, BlockState neighborState, IWorld world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return facing.getAxis().isHorizontal() ? state.with(FACING_PROPERTIES.get(facing), Boolean.valueOf(this.canConnect(neighborState, neighborState.isSideSolidFullSquare(world, neighborPos, facing.getOpposite()), facing.getOpposite()))) : super.getStateForNeighborUpdate(state, facing, neighborState, world, pos, neighborPos);
    }


    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(NORTH, EAST, WEST, SOUTH, WATERLOGGED);
    }

    /* TODO: implement Fabric's FlammableBlockRegistry
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
