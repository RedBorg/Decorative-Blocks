package com.lilypuree.decorative_blocks;

import com.lilypuree.decorative_blocks.setup.ModSetup;
import com.lilypuree.decorative_blocks.setup.Registration;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class DecorativeBlocks implements ModInitializer {
    public static final String MODID = "decorative_blocks";

    public static ModSetup setup = new ModSetup();
    public static DecorativeBlocks instance;
    public static Logger logger = LogManager.getLogger(MODID);


    public DecorativeBlocks() {
        instance = this;

        // TODO: Look into FMLJavaModLoadingContext
        //FMLJavaModLoadingContext.get().getModEventBus().addListener((FMLCommonSetupEvent e) -> setup.init(e));
        //FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientSetup::init);
    }

    @Override
    public void onInitialize() {
        Registration.register();
    }
}
