package com.lilypuree.decorative_blocks.setup;

import com.google.common.collect.ImmutableMap;
import com.lilypuree.decorative_blocks.blocks.*;
import com.lilypuree.decorative_blocks.datagen.types.WoodTypes;
import com.lilypuree.decorative_blocks.entity.DummyEntityForSitting;
import com.lilypuree.decorative_blocks.utils.DeferredRegister;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.entity.FabricEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.registry.Registry;

import static com.lilypuree.decorative_blocks.DecorativeBlocks.MODID;

public class Registration {
    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(Registry.ITEM, MODID);
    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(Registry.BLOCK, MODID);
    public static final DeferredRegister<EntityType<?>> ENTITIES = new DeferredRegister<>(Registry.ENTITY_TYPE, MODID);


    public static void register() {
        ITEMS.register();
        BLOCKS.register();
        ENTITIES.register();
    }

    public static final BarPanelBlock BAR_PANEL = BLOCKS.add("bar_panel", new BarPanelBlock(FabricBlockSettings.of(Material.METAL, MaterialColor.BLACK).nonOpaque().strength(5.0F, 5.0f).sounds(BlockSoundGroup.METAL).build()));
    public static final ChainBlock CHAIN = BLOCKS.add("chain", new ChainBlock(FabricBlockSettings.of(Material.METAL, MaterialColor.BLACK).strength(4.3F, 4.3f).sounds(BlockSoundGroup.METAL).build()));
    public static final ChandelierBlock CHANDELIER = BLOCKS.add("chandelier", new ChandelierBlock(FabricBlockSettings.of(Material.AIR).noCollision().strength(0.3F, 0.3f).sounds(BlockSoundGroup.WOOD).build()));
    public static final BrazierBlock BRAZIER = BLOCKS.add("brazier", new BrazierBlock(FabricBlockSettings.of(Material.METAL).strength(3.0F, 3.0f).sounds(BlockSoundGroup.METAL).build()));
    public static final PillarBlock STONE_PILLAR = BLOCKS.add("stone_pillar", new PillarBlock(FabricBlockSettings.of(Material.STONE).strength(1.5F, 6.5F).build()));
    public static final Block ROCKY_DIRT = BLOCKS.add("rocky_dirt", new RockyDirtBlock());
    public static final Item BAR_PANEL_ITEM = ITEMS.add("bar_panel", new BlockItem(BAR_PANEL, new Item.Settings().group(ItemGroup.DECORATIONS)));
    public static final Item CHAIN_ITEM = ITEMS.add("chain", new BlockItem(CHAIN, new Item.Settings().group(ItemGroup.DECORATIONS)));
    // TODO: burn time: 1600
    public static final Item CHANDELIER_ITEM = ITEMS.add("chandelier", new BlockItem(CHANDELIER, new Item.Settings().group(ItemGroup.DECORATIONS)));
    public static final Item BRAZIER_ITEM = ITEMS.add("brazier", new BlockItem(BRAZIER, new Item.Settings().group(ItemGroup.DECORATIONS)));
    public static final Item STONE_PILLAR_ITEM = ITEMS.add("stone_pillar", new BlockItem(STONE_PILLAR, new Item.Settings().group(ItemGroup.DECORATIONS)));
    public static final Item ROCKY_DIRT_ITEM = ITEMS.add("rocky_dirt", new BlockItem(ROCKY_DIRT, new Item.Settings().group(ItemGroup.DECORATIONS)));

    public static final EntityType<DummyEntityForSitting> DUMMY_ENTITY_TYPE = ENTITIES.add("dummy", FabricEntityTypeBuilder.<DummyEntityForSitting>create(EntityCategory.MISC, DummyEntityForSitting::new)
            .trackable(256, 20)
            .size(EntityDimensions.fixed(0.00001f, 0.00001f))
            .build());

    public static ImmutableMap<String, BeamBlock> BEAM_BLOCKS;
    public static ImmutableMap<String, PalisadeBlock> PALISADE_BLOCKS;
    public static ImmutableMap<String, SeatBlock> SEAT_BLOCKS;
    public static ImmutableMap<String, SupportBlock> SUPPORT_BLOCKS;
    public static ImmutableMap<String, Item> ITEMBLOCKS;

    static {
        ImmutableMap.Builder<String, BeamBlock> beamBlockBuilder = ImmutableMap.builder();
        ImmutableMap.Builder<String, PalisadeBlock> palisadeBlockBuilder = ImmutableMap.builder();
        ImmutableMap.Builder<String, SeatBlock> seatBlockBuilder = ImmutableMap.builder();
        ImmutableMap.Builder<String, SupportBlock> supportBlockBuilder = ImmutableMap.builder();
        ImmutableMap.Builder<String, Item> itemBuilder = ImmutableMap.builder();

        Block.Settings woodProperty = FabricBlockSettings.of(Material.WOOD, MaterialColor.WOOD).strength(1.2F, 1.2f).sounds(BlockSoundGroup.WOOD).build();
        Block.Settings palisadeProperty = FabricBlockSettings.of(Material.WOOD, MaterialColor.WOOD).strength(2.0F, 4.0F).sounds(BlockSoundGroup.WOOD).build();
        Item.Settings buildingBlockItemProperty = new Item.Settings().group(ItemGroup.DECORATIONS);

        for(WoodTypes wood : WoodTypes.values()){
            String beamName = wood + "_beam";
            String palisadeName = wood + "_palisade";
            String seatName = wood + "_seat";
            String supportName = wood + "_support";

            BeamBlock beamBlock = new BeamBlock(woodProperty);
            PalisadeBlock palisadeBlock = new PalisadeBlock(palisadeProperty);
            SeatBlock seatBlock = new SeatBlock(woodProperty);
            SupportBlock supportBlock = new SupportBlock(woodProperty);

            beamBlockBuilder.put(beamName, BLOCKS.add(beamName, beamBlock));
            palisadeBlockBuilder.put(palisadeName, BLOCKS.add(palisadeName, palisadeBlock));
            seatBlockBuilder.put(seatName, BLOCKS.add(seatName, seatBlock));
            supportBlockBuilder.put(supportName, BLOCKS.add(supportName, supportBlock));

            // TODO: replace BurnableBlockItem with Fabric's FuelRegistry, all 300 burn time
            itemBuilder.put(beamName, ITEMS.add(beamName, new BlockItem(beamBlock, buildingBlockItemProperty)));
            itemBuilder.put(palisadeName, ITEMS.add(palisadeName, new BlockItem(palisadeBlock, buildingBlockItemProperty)));
            itemBuilder.put(seatName, ITEMS.add(seatName, new BlockItem(seatBlock, buildingBlockItemProperty)));
            itemBuilder.put(supportName, ITEMS.add(supportName, new BlockItem(supportBlock, buildingBlockItemProperty)));

        }

        BEAM_BLOCKS = beamBlockBuilder.build();
        PALISADE_BLOCKS = palisadeBlockBuilder.build();
        SEAT_BLOCKS = seatBlockBuilder.build();
        SUPPORT_BLOCKS = supportBlockBuilder.build();
        ITEMBLOCKS = itemBuilder.build();
    }

    public static net.minecraft.block.PillarBlock getBeamBlock(WoodTypes wood){
        String name = wood + "_beam";
        return BEAM_BLOCKS.get(name);
    }

    public static Block getPalisadeBlock(WoodTypes wood){
        String name = wood + "_palisade";
        return PALISADE_BLOCKS.get(name);
    }

    public static SeatBlock getSeatBlock(WoodTypes wood){
        String name = wood + "_seat";
        return SEAT_BLOCKS.get(name);
    }

    public static SupportBlock getSupportBlock(WoodTypes wood){
        String name = wood + "_support";
        return SUPPORT_BLOCKS.get(name);
    }
}

