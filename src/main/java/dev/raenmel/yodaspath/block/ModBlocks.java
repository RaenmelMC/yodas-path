package dev.raenmel.yodaspath.block;

import dev.raenmel.yodaspath.YodaSPath;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.ExperienceDroppingBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
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