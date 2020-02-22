package com.lilypuree.decorative_blocks.setup;

import com.lilypuree.decorative_blocks.entity.DummyEntityForSitting;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.util.Identifier;

//@Mod.EventBusSubscriber(modid = DecorativeBlocks.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)

public class ClientSetup implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.INSTANCE.register(Registration.DUMMY_ENTITY_TYPE, EmptyRenderer::new);

        BlockRenderLayerMap.INSTANCE.putBlocks(
                RenderLayer.getCutout(),
                Registration.BAR_PANEL,
                Registration.BRAZIER,
                Registration.CHAIN,
                Registration.CHANDELIER);
    }


    private static class EmptyRenderer extends EntityRenderer<DummyEntityForSitting> {

        public EmptyRenderer(EntityRenderDispatcher entityRenderDispatcher, EntityRendererRegistry.Context context) {
            super(entityRenderDispatcher);
        }

        @Override
        public boolean shouldRender(DummyEntityForSitting mobEntity, Frustum frustum, double d, double e, double f) {
            return false;
        }

        @Override
        public Identifier getTexture(DummyEntityForSitting entity) {
            return null;
        }
    }

}
