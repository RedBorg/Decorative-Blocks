package com.lilypuree.decorative_blocks.mixin;

import com.lilypuree.decorative_blocks.setup.Registration;
import net.minecraft.block.BlockState;
import net.minecraft.block.PlantBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlantBlock.class)
public class MixinPlantBlock {
    // TODO: this should probably an API with a register, maybe a tag
    /**
     * Injects into canPlantOnTop method of {@link PlantBlock}, will return true if block is ROCKY_DIRT.
     *
     */
    @Inject(at = @At("RETURN"), method = "canPlantOnTop", cancellable = true)
    private void addPlantableBlock(BlockState floor, BlockView world, BlockPos pos, CallbackInfoReturnable<Boolean> ci) {
        if (floor.getBlock() == Registration.ROCKY_DIRT)
            ci.setReturnValue(true);
    }
}
