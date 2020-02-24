package com.lilypuree.decorative_blocks.setup;

import com.google.common.collect.ImmutableMap;
import com.lilypuree.decorative_blocks.DecorativeBlocks;
import com.lilypuree.decorative_blocks.blocks.*;
import com.lilypuree.decorative_blocks.datagen.types.WoodTypes;
import com.lilypuree.decorative_blocks.entity.DummyEntityForSitting;
import com.lilypuree.decorative_blocks.utils.DeferredRegister;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.api.tag.FabricTagBuilder;
import net.fabricmc.fabric.mixin.tag.extension.MixinTagBuilder;
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
import net.minecraft.tag.Tag;
import net.minecraft.tag.TagContainer;
import net.minecraft.util.Identifier;
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

        FlammableBlockRegistry.getDefaultInstance().add(BEAM_BLOCK_TAG, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(PALISADE_BLOCK_TAG, 10, 20);
        FlammableBlockRegistry.getDefaultInstance().add(SEAT_BLOCK_TAG, 10, 20);
        FlammableBlockRegistry.getDefaultInstance().add(SUPPORT_BLOCK_TAG, 8, 20);

        FuelRegistry.INSTANCE.add(BEAM_BLOCK_ITEM_TAG, 300);
        FuelRegistry.INSTANCE.add(PALISADE_BLOCK_ITEM_TAG, 300);
        FuelRegistry.INSTANCE.add(SEAT_BLOCK_ITEM_TAG, 300);
        FuelRegistry.INSTANCE.add(SUPPORT_BLOCK_ITEM_TAG, 300);

        FuelRegistry.INSTANCE.add(CHANDELIER_ITEM, 1600);


    }

    public static final Identifier SPAWN_DUMMY_SEAT_ID = new Identifier(MODID, "spawn_dummy");

    public static final BarPanelBlock BAR_PANEL = BLOCKS.add("bar_panel", new BarPanelBlock(FabricBlockSettings.of(Material.METAL, MaterialColor.BLACK).nonOpaque().strength(5.0F, 5.0f).sounds(BlockSoundGroup.METAL).build()));
    public static final ChainBlock CHAIN = BLOCKS.add("chain", new ChainBlock(FabricBlockSettings.of(Material.METAL, MaterialColor.BLACK).strength(4.3F, 4.3f).sounds(BlockSoundGroup.METAL).build()));
    public static final ChandelierBlock CHANDELIER = BLOCKS.add("chandelier", new ChandelierBlock(FabricBlockSettings.of(Material.AIR).noCollision().strength(0.3F, 0.3f).sounds(BlockSoundGroup.WOOD).build()));
    public static final BrazierBlock BRAZIER = BLOCKS.add("brazier", new BrazierBlock(FabricBlockSettings.of(Material.METAL).strength(3.0F, 3.0f).sounds(BlockSoundGroup.METAL).build()));
    public static final PillarBlock STONE_PILLAR = BLOCKS.add("stone_pillar", new PillarBlock(FabricBlockSettings.of(Material.STONE).strength(1.5F, 6.5F).build()));
    public static final Block ROCKY_DIRT = BLOCKS.add("rocky_dirt", new RockyDirtBlock());
    public static final Item BAR_PANEL_ITEM = ITEMS.add("bar_panel", new BlockItem(BAR_PANEL, new Item.Settings().group(ItemGroup.DECORATIONS)));
    public static final Item CHAIN_ITEM = ITEMS.add("chain", new BlockItem(CHAIN, new Item.Settings().group(ItemGroup.DECORATIONS)));
    public static final Item CHANDELIER_ITEM = ITEMS.add("chandelier", new BlockItem(CHANDELIER, new Item.Settings().group(ItemGroup.DECORATIONS)));
    public static final Item BRAZIER_ITEM = ITEMS.add("brazier", new BlockItem(BRAZIER, new Item.Settings().group(ItemGroup.DECORATIONS)));
    public static final Item STONE_PILLAR_ITEM = ITEMS.add("stone_pillar", new BlockItem(STONE_PILLAR, new Item.Settings().group(ItemGroup.DECORATIONS)));
    public static final Item ROCKY_DIRT_ITEM = ITEMS.add("rocky_dirt", new BlockItem(ROCKY_DIRT, new Item.Settings().group(ItemGroup.DECORATIONS)));

    public static final EntityType<DummyEntityForSitting> DUMMY_ENTITY_TYPE = ENTITIES.add("dummy", FabricEntityTypeBuilder.<DummyEntityForSitting>create(EntityCategory.MISC, (entityType, world) -> new DummyEntityForSitting(entityType, world))
            .trackable(256, 20)
            .size(EntityDimensions.fixed(0.001f, 0.001f))
            .build());

    public static ImmutableMap<String, BeamBlock> BEAM_BLOCKS;
    public static Tag<Block> BEAM_BLOCK_TAG;
    public static Tag<Item> BEAM_BLOCK_ITEM_TAG;

    public static ImmutableMap<String, PalisadeBlock> PALISADE_BLOCKS;
    public static Tag<Block> PALISADE_BLOCK_TAG;
    public static Tag<Item> PALISADE_BLOCK_ITEM_TAG;

    public static ImmutableMap<String, SeatBlock> SEAT_BLOCKS;
    public static Tag<Block> SEAT_BLOCK_TAG;
    public static Tag<Item> SEAT_BLOCK_ITEM_TAG;

    public static ImmutableMap<String, SupportBlock> SUPPORT_BLOCKS;
    public static Tag<Block> SUPPORT_BLOCK_TAG;
    public static Tag<Item> SUPPORT_BLOCK_ITEM_TAG;

    public static ImmutableMap<String, Item> ITEMBLOCKS;

    static {
        ImmutableMap.Builder<String, BeamBlock> beamBlockBuilder = ImmutableMap.builder();
        Tag.Builder<Block> beamBlockTagBuilder = new Tag.Builder<>();
        Tag.Builder<Item> beamBlockItemTagBuilder = new Tag.Builder<>();

        ImmutableMap.Builder<String, PalisadeBlock> palisadeBlockBuilder = ImmutableMap.builder();
        Tag.Builder<Block> palisadeBlockTagBuilder = new Tag.Builder<>();
        Tag.Builder<Item> palisadeBlockItemTagBuilder = new Tag.Builder<>();

        ImmutableMap.Builder<String, SeatBlock> seatBlockBuilder = ImmutableMap.builder();
        Tag.Builder<Block> seatBlockTagBuilder = new Tag.Builder<>();
        Tag.Builder<Item> seatBlockItemTagBuilder = new Tag.Builder<>();

        ImmutableMap.Builder<String, SupportBlock> supportBlockBuilder = ImmutableMap.builder();
        Tag.Builder<Block> supportBlockTagBuilder = new Tag.Builder<>();
        Tag.Builder<Item> supportBlockItemTagBuilder = new Tag.Builder<>();

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

            BlockItem beamBlockItem = new BlockItem(beamBlock, buildingBlockItemProperty);
            BlockItem palisadeBlockItem = new BlockItem(palisadeBlock, buildingBlockItemProperty);
            BlockItem seatBlockItem = new BlockItem(seatBlock, buildingBlockItemProperty);
            BlockItem supportBlockItem = new BlockItem(supportBlock, buildingBlockItemProperty);

            beamBlockBuilder.put(beamName, BLOCKS.add(beamName, beamBlock));
            beamBlockTagBuilder.add(beamBlock);
            itemBuilder.put(beamName, ITEMS.add(beamName, beamBlockItem));
            beamBlockItemTagBuilder.add(beamBlockItem);

            palisadeBlockBuilder.put(palisadeName, BLOCKS.add(palisadeName, palisadeBlock));
            palisadeBlockTagBuilder.add(palisadeBlock);
            itemBuilder.put(palisadeName, ITEMS.add(palisadeName, palisadeBlockItem));
            beamBlockItemTagBuilder.add(palisadeBlockItem);

            seatBlockBuilder.put(seatName, BLOCKS.add(seatName, seatBlock));
            seatBlockTagBuilder.add(seatBlock);
            itemBuilder.put(seatName, ITEMS.add(seatName, seatBlockItem));
            beamBlockItemTagBuilder.add(seatBlockItem);

            supportBlockBuilder.put(supportName, BLOCKS.add(supportName, supportBlock));
            supportBlockTagBuilder.add(supportBlock);
            itemBuilder.put(supportName, ITEMS.add(supportName, supportBlockItem));
            beamBlockItemTagBuilder.add(supportBlockItem);

        }

        BEAM_BLOCKS = beamBlockBuilder.build();
        BEAM_BLOCK_TAG = beamBlockTagBuilder.build(new Identifier(MODID, "beam_block"));
        BEAM_BLOCK_ITEM_TAG = beamBlockItemTagBuilder.build(new Identifier(MODID, "beam_block"));

        PALISADE_BLOCKS = palisadeBlockBuilder.build();
        PALISADE_BLOCK_TAG = palisadeBlockTagBuilder.build(new Identifier(MODID, "palisade_block"));
        PALISADE_BLOCK_ITEM_TAG = palisadeBlockItemTagBuilder.build(new Identifier(MODID, "palisade_block"));

        SEAT_BLOCKS = seatBlockBuilder.build();
        SEAT_BLOCK_TAG = seatBlockTagBuilder.build(new Identifier(MODID, "seat_block"));
        SEAT_BLOCK_ITEM_TAG = seatBlockItemTagBuilder.build(new Identifier(MODID, "seat_block"));

        SUPPORT_BLOCKS = supportBlockBuilder.build();
        SUPPORT_BLOCK_TAG = supportBlockTagBuilder.build(new Identifier(MODID, "support_block"));
        SUPPORT_BLOCK_ITEM_TAG = supportBlockItemTagBuilder.build(new Identifier(MODID, "support_block"));

        ITEMBLOCKS = itemBuilder.build();
    }

    ;


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

