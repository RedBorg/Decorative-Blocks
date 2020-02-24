package com.lilypuree.decorative_blocks.mixin;

import com.lilypuree.decorative_blocks.DecorativeBlocks;
import com.lilypuree.decorative_blocks.setup.Registration;
import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ServerPlayerEntity.class)
public abstract class MixinServerPlayerEntity {

    /**
     * Will kill a {@link com.lilypuree.decorative_blocks.entity.DummyEntityForSitting} when a player stops riding it.
     */
    @Inject(at = @At("RETURN"), method = "stopRiding", locals = LocalCapture.CAPTURE_FAILSOFT)
    private void killDummy(CallbackInfo info, Entity entity) {

        if (entity.getType() == Registration.DUMMY_ENTITY_TYPE) {
            entity.kill();
            DecorativeBlocks.logger.debug("decorative_blocks: dummy killed by mixin");
        }
    }
}
