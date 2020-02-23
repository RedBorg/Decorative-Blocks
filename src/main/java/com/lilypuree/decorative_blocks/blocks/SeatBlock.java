package com.lilypuree.decorative_blocks.blocks;

import com.lilypuree.decorative_blocks.entity.DummyEntityForSitting;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.Waterloggable;
import net.minecraft.entity.EntityContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.List;

// TODO: improve seat dismount
public class SeatBlock extends HorizontalFacingBlock implements Waterloggable {
    private static final VoxelShape SEAT_SHAPE_NS = Block.createCuboidShape(0, 0.0D, 4D, 16D, 7D, 12D);
    private static final VoxelShape SEAT_SHAPE_EW = Block.createCuboidShape(4D, 0.0D, 0, 12D, 7D, 16D);

    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final BooleanProperty OCCUPIED = Properties.OCCUPIED;

    public SeatBlock(Block.Settings properties) {
        super(properties);
        setDefaultState(this.getStateManager().getDefaultState().with(FACING, Direction.NORTH).with(OCCUPIED, false).with(WATERLOGGED, false));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, EntityContext context) {
        Direction facing = state.get(FACING);
        switch (facing) {
            case NORTH:
            case SOUTH:
                return SEAT_SHAPE_NS;
            case EAST:
            case WEST:
                return SEAT_SHAPE_EW;
        }
        return SEAT_SHAPE_NS;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        //BlockState blockstate = ctx.getWorld().getBlockState(ctx.getBlockPos());
        FluidState ifluidstate = ctx.getWorld().getFluidState(ctx.getBlockPos());
        boolean flag = ifluidstate.matches(FluidTags.WATER) && ifluidstate.getLevel() == 8;

        return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite()).with(WATERLOGGED, flag).with(OCCUPIED, Boolean.FALSE);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(FACING, WATERLOGGED, OCCUPIED);
    }

    // TODO: Figure out if needed
    public boolean propagatesSkylightDown(BlockState state, BlockView reader, BlockPos pos) {
        return !state.get(WATERLOGGED);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack heldItem = player.getStackInHand(hand);
        BlockState upperBlock = world.getBlockState(pos.up());
        boolean canSit = hit.getSide() == Direction.UP && !state.get(OCCUPIED) && heldItem.isEmpty() && upperBlock.isAir() && isPlayerInRange(player, pos);
        if (!world.isClient && canSit) {
            DummyEntityForSitting seat = new DummyEntityForSitting(world, pos);
            if (world.spawnEntity(seat)) {

                if (!player.startRiding(seat, true)) {
                    LOGGER.warn("Decorative Blocks: Could not ride a seat!");
                }
                return ActionResult.SUCCESS;
            } else {
                LOGGER.warn("Decorative Blocks: Could not create a dummy entity for a seat!");
                return ActionResult.FAIL;
            }
        }

        return super.onUse(state, world, pos, player, hand, hit);
    }

    private static boolean isPlayerInRange(PlayerEntity player, BlockPos pos) {
        BlockPos playerPos = player.getBlockPos();
        int blockReachDistance = 3;

        if (blockReachDistance == 0) //player has to stand on top of the block
            return playerPos.getY() - pos.getY() <= 1 && playerPos.getX() - pos.getX() == 0 && playerPos.getZ() - pos.getZ() == 0;

        pos = pos.add(0.5D, 0.5D, 0.5D);


        Box range = new Box(pos.getX() + blockReachDistance, pos.getY() + blockReachDistance, pos.getZ() + blockReachDistance, pos.getX() - blockReachDistance, pos.getY() - blockReachDistance, pos.getZ() - blockReachDistance);

        playerPos = playerPos.add(0.5D, 0.5D, 0.5D);
        return range.getMin(Direction.Axis.X) <= playerPos.getX() && range.getMin(Direction.Axis.Y) <= playerPos.getY() && range.getMin(Direction.Axis.Z) <= playerPos.getZ() && range.getMax(Direction.Axis.X) >= playerPos.getX() && range.getMax(Direction.Axis.Y) >= playerPos.getY() && range.getMax(Direction.Axis.Z) >= playerPos.getZ();
    }

    @Override
    public void onBlockRemoved(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        double x = pos.getX();
        double y = pos.getY();
        double z = pos.getZ();
        List<DummyEntityForSitting> entities = world.getEntities(DummyEntityForSitting.class, new Box(x, y, z, x, y, z), null);
        for (DummyEntityForSitting entity : entities) {
            entity.remove();
        }
        super.onBlockRemoved(state, world, pos, newState, moved);
    }
}
