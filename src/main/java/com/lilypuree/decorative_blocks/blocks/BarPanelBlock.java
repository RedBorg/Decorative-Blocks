package com.lilypuree.decorative_blocks.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.entity.EntityContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class BarPanelBlock extends TrapdoorBlock {

    private static final double d0 = 3D;
    private static final double d1 = 16D - d0;
    protected static final VoxelShape EAST_OPEN_AABB = Block.createCuboidShape(0.0D, 0.0D, 0.0D, d0, 16.0D, 16.0D);
    protected static final VoxelShape WEST_OPEN_AABB = Block.createCuboidShape(d1, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape SOUTH_OPEN_AABB = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, d0);
    protected static final VoxelShape NORTH_OPEN_AABB = Block.createCuboidShape(0.0D, 0.0D, d1, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape BOTTOM_AABB = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, d0, 16.0D);
    protected static final VoxelShape TOP_AABB = Block.createCuboidShape(0.0D, d1, 0.0D, 16.0D, 16.0D, 16.0D);

    public BarPanelBlock(Block.Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView worldIn, BlockPos pos, EntityContext context) {
        if (!state.get(OPEN)) {
            return state.get(HALF) == BlockHalf.TOP ? TOP_AABB : BOTTOM_AABB;
        } else {
            switch ((Direction) state.get(FACING)) {
                case NORTH:
                default:
                    return NORTH_OPEN_AABB;
                case SOUTH:
                    return SOUTH_OPEN_AABB;
                case WEST:
                    return WEST_OPEN_AABB;
                case EAST:
                    return EAST_OPEN_AABB;
            }
        }
    }


    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        state = state.cycle(OPEN);
        world.setBlockState(pos, state, 2);
        if (state.get(WATERLOGGED)) {
            world.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        this.playToggleSound(player, world, pos, state.get(OPEN));
        return ActionResult.SUCCESS;
    }

}
