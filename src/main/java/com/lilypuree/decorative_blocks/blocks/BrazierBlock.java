package com.lilypuree.decorative_blocks.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Waterloggable;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import java.util.Random;

public class BrazierBlock extends Block implements Waterloggable {
    private static final VoxelShape BRAZIER_SHAPE = Block.createCuboidShape(2D, 0.0D, 2D, 14D, 14D, 14D);
    public static final BooleanProperty LIT = Properties.LIT;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;


    public BrazierBlock(Settings properties) {
        super(properties);
        this.setDefaultState(this.getStateManager().getDefaultState().with(LIT, Boolean.valueOf(true)).with(WATERLOGGED, Boolean.valueOf(false)));
    }

    @Override
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        if (!entityIn.isFireImmune() && state.get(LIT) && entityIn instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity) entityIn)) {
            entityIn.damage(DamageSource.IN_FIRE, 1.0F);
        }
        super.onEntityCollision(state, worldIn, pos, entityIn);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        IWorld iworld = ctx.getWorld();
        BlockPos blockpos = ctx.getBlockPos();
        boolean flag = iworld.getFluidState(blockpos).getFluid() == Fluids.WATER;
        return this.getDefaultState().with(WATERLOGGED, Boolean.valueOf(flag)).with(LIT, Boolean.valueOf(!flag));
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction facing, BlockState neighborState, IWorld world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return super.getStateForNeighborUpdate(state, facing, neighborState, world, pos, neighborPos);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack heldItem = player.getStackInHand(hand);
        if (state.get(LIT)) {
            // TODO: replace by actual shovel detection
            if (heldItem.getItem().isEffectiveOn(Blocks.SNOW.getDefaultState())) {

                world.playSound((PlayerEntity) null, pos, SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, SoundCategory.BLOCKS, 0.8F, 1.0F);

                world.setBlockState(pos, state.with(LIT, Boolean.FALSE));
                return ActionResult.SUCCESS;
            }
        } else if (!state.get(WATERLOGGED)) {
            if (hit.getSide() == Direction.UP && heldItem.getItem() == Items.FLINT_AND_STEEL || heldItem.getItem() == Items.FIRE_CHARGE) {

                SoundEvent sound = (heldItem.getItem() == Items.FIRE_CHARGE) ? SoundEvents.ITEM_FIRECHARGE_USE : SoundEvents.ITEM_FLINTANDSTEEL_USE;
                world.playSound((PlayerEntity) null, pos, sound, SoundCategory.BLOCKS, 1.0F, world.random.nextFloat() * 0.4F + 0.8F);

                world.setBlockState(pos, state.with(LIT, Boolean.TRUE));
//                if (player != null) {
//                    heldItem.damageItem(1, player, (p_219998_1_) -> {
//                        p_219998_1_.sendBreakAnimation(handIn);
//                    });
//                }
                return ActionResult.CONSUME;

            }
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, EntityContext context) {
        return BRAZIER_SHAPE;
    }

    @Override
    public int getLuminance(BlockState state) {
        return state.get(LIT) ? 15 : 0;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (state.get(LIT)) {
            if (random.nextInt(10) == 0) {
                world.playSound((double) ((float) pos.getX() + 0.5F), (double) ((float) pos.getY() + 0.5F), (double) ((float) pos.getZ() + 0.5F), SoundEvents.BLOCK_CAMPFIRE_CRACKLE, SoundCategory.BLOCKS, 0.5F + random.nextFloat(), random.nextFloat() * 0.7F + 0.6F, false);
            }

            if (random.nextInt(5) == 0) {
                for (int i = 0; i < random.nextInt(1) + 1; ++i) {
                    world.addParticle(ParticleTypes.LAVA, (double) ((float) pos.getX() + 0.5F), (double) ((float) pos.getY() + 0.8F), (double) ((float) pos.getZ() + 0.5F), (double) (random.nextFloat() / 2.0F), 5.0E-5D, (double) (random.nextFloat() / 2.0F));
                }
            }

        }
    }

    @Override
    public void onProjectileHit(World world, BlockState state, BlockHitResult hitResult, Entity entity) {
        if (world.isClient) {
            return;
        }
        BlockPos blockpos = hitResult.getBlockPos();
        if (entity instanceof ArrowEntity) {
            ArrowEntity arrowentity = (ArrowEntity) entity;
            if (arrowentity.isOnFire() && !state.get(LIT) && !state.get(WATERLOGGED)) {
                world.setBlockState(blockpos, state.with(Properties.LIT, Boolean.valueOf(true)), 11);
            }
        }
    }

    @Override
    public boolean tryFillWithFluid(IWorld world, BlockPos pos, BlockState state, FluidState fluidState) {
        if (!state.get(Properties.WATERLOGGED) && fluidState.getFluid() == Fluids.WATER) {
            boolean flag = state.get(LIT);
            if (flag) {
                world.playSound((PlayerEntity) null, pos, SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, SoundCategory.BLOCKS, 1.0F, 1.0F);
            }
            world.setBlockState(pos, state.with(WATERLOGGED, Boolean.valueOf(true)).with(LIT, Boolean.valueOf(false)), 3);
            world.getFluidTickScheduler().schedule(pos, fluidState.getFluid(), fluidState.getFluid().getTickRate(world));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(LIT, WATERLOGGED);
    }


    /* TODO: check if this actually unnecessary because of block settings
    public boolean allowsMovement(BlockState state, BlockView worldIn, BlockPos pos, PathNodeType type) {
        return false;
    }

     */

}
