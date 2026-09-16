package dev.raenmel.yodaspath.block;

import dev.raenmel.yodaspath.YodaSPath;
import dev.raenmel.yodaspath.block.custom.DryTatooineBushBlock;
import dev.raenmel.yodaspath.block.custom.MoistureVaporatorBlock;
import dev.raenmel.yodaspath.block.custom.TatooineCrateBlock;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.ColorCode;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;

public class ModBlocks {

    public static final Block KYBER_ORE = registerBlock(
            "kyber_ore",
            new ExperienceDroppingBlock(
                    UniformIntProvider.create(2, 5),
                    AbstractBlock.Settings.create()
                            .strength(3.0F, 3.0F)
                            .requiresTool()
                            .sounds(BlockSoundGroup.STONE)
            )
    );

    public static final Block DEEPSLATE_KYBER_ORE = registerBlock(
            "deepslate_kyber_ore",
            new ExperienceDroppingBlock(
                    UniformIntProvider.create(2, 5),
                    AbstractBlock.Settings.create()
                            .strength(4.5F, 3.0F)
                            .requiresTool()
                            .sounds(BlockSoundGroup.DEEPSLATE)
            )
    );

    public static final Block TATOOINE_SAND =
            registerBlock(
                    "tatooine_sand",
                    new ColoredFallingBlock(
                            new ColorCode(14406560),
                            AbstractBlock.Settings.copy(Blocks.SAND)
                                    .mapColor(MapColor.ORANGE)
                    )
            );

    public static final Block TATOOINE_SANDSTONE =
            registerBlock(
                    "tatooine_sandstone",
                    new Block(
                            AbstractBlock.Settings.copy(Blocks.SANDSTONE)
                    )
            );

    public static final Block TATOOINE_SANDSTONE_STAIRS =
            registerBlock(
                    "tatooine_sandstone_stairs",
                    new StairsBlock(
                            TATOOINE_SANDSTONE.getDefaultState(),
                            AbstractBlock.Settings.copy(Blocks.SANDSTONE_STAIRS)
                    )
            );

    public static final Block TATOOINE_SANDSTONE_SLAB =
            registerBlock(
                    "tatooine_sandstone_slab",
                    new SlabBlock(
                            AbstractBlock.Settings.copy(Blocks.SANDSTONE_SLAB)
                    )
            );

    public static final Block TATOOINE_CHISELED_SANDSTONE =
            registerBlock(
                    "tatooine_chiseled_sandstone",
                    new Block(
                            AbstractBlock.Settings.copy(Blocks.CHISELED_SANDSTONE)
                    )
            );

    public static final Block TATOOINE_CHISELED_SANDSTONE_STAIRS =
            registerBlock(
                    "tatooine_chiseled_sandstone_stairs",
                    new StairsBlock(
                            TATOOINE_CHISELED_SANDSTONE.getDefaultState(),
                            AbstractBlock.Settings.copy(Blocks.SANDSTONE_STAIRS)
                    )
            );

    public static final Block TATOOINE_CHISELED_SLAB =
            registerBlock(
                    "tatooine_chiseled_slab",
                    new SlabBlock(
                            AbstractBlock.Settings.copy(Blocks.SANDSTONE_SLAB)
                    )
            );

    public static final Block TATOOINE_SANDSTONE_WALL =
            registerBlock(
                    "tatooine_sandstone_wall",
                    new WallBlock(
                            AbstractBlock.Settings.copy(Blocks.SANDSTONE_WALL)
                    )
            );

    public static final Block MOISTURE_VAPORATOR =
            registerBlock(
                    "moisture_vaporator",
                    new MoistureVaporatorBlock(
                            AbstractBlock.Settings.copy(Blocks.IRON_BLOCK)
                                    .strength(3.0F)
                                    .nonOpaque()
                    )
            );

    public static final Block TATOOINE_CRATE =
            registerBlock(
                    "tatooine_crate",
                    new TatooineCrateBlock(
                            AbstractBlock.Settings.copy(Blocks.BARREL)
                                    .strength(2.5F)
                                    .nonOpaque()
                    )
            );

    public static final Block DRY_TATOOINE_BUSH =
            registerBlock(
                    "dry_tatooine_bush",
                    new DryTatooineBushBlock(
                            AbstractBlock.Settings.copy(Blocks.DEAD_BUSH)
                                    .noCollision()
                                    .breakInstantly()
                    )
            );

    public static final Block SCRAP_PILE =
            registerBlock(
                    "scrap_pile",
                    new Block(
                            AbstractBlock.Settings.copy(Blocks.IRON_BLOCK)
                                    .strength(2.0F)
                                    .requiresTool()
                    )
            );

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);

        return Registry.register(
                Registries.BLOCK,
                Identifier.of(YodaSPath.MOD_ID, name),
                block
        );
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(
                Registries.ITEM,
                Identifier.of(YodaSPath.MOD_ID, name),
                new BlockItem(block, new Item.Settings())
        );
    }

    public static void initialize() {
        YodaSPath.LOGGER.info("Registering blocks for {}", YodaSPath.MOD_ID);
    }
}