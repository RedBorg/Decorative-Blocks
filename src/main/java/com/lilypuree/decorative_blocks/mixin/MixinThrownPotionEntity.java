package com.lilypuree.decorative_blocks.mixin;

import com.lilypuree.decorative_blocks.DecorativeBlocks;
import com.lilypuree.decorative_blocks.blocks.BrazierBlock;
import com.lilypuree.decorative_blocks.setup.Registration;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.thrown.ThrownPotionEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ThrownPotionEntity.class)
public abstract class MixinThrownPotionEntity extends Entity {

    public MixinThrownPotionEntity(EntityType<?> type, World world) {
        super(type, world);
    }
    //TODO: Again, this should probably be an event
    /** Will extinguish a brazier should you throw water on it.
     */
    @Inject(method = "extinguishFire", at = @At("RETURN"))
    private void extinguishBrazier(BlockPos pos, Direction direction, CallbackInfo ci) {

        DecorativeBlocks.logger.info("entered");
        BlockState state = this.world.getBlockState(pos);
        if (state.getBlock() == Registration.BRAZIER && state.get(BrazierBlock.LIT)) {
            DecorativeBlocks.logger.info("extinguish");
            ((ServerWorld)this.world).setBlockState(pos, state.with(BrazierBlock.LIT, false));
            this.world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, SoundCategory.BLOCKS, .6f, 1f);
        }
    }
}
