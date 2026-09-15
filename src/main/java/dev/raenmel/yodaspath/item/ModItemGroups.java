package dev.raenmel.yodaspath.item;

import dev.raenmel.yodaspath.YodaSPath;
import dev.raenmel.yodaspath.block.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {

    public static final RegistryKey<ItemGroup> YODAS_PATH_GROUP_KEY =
            RegistryKey.of(
                    RegistryKeys.ITEM_GROUP,
                    Identifier.of(YodaSPath.MOD_ID, "yodas_path")
            );

    public static final RegistryKey<ItemGroup> YODAS_PATH_ORES_GROUP_KEY =
            RegistryKey.of(
                    RegistryKeys.ITEM_GROUP,
                    Identifier.of(YodaSPath.MOD_ID, "ores")
            );

    public static final RegistryKey<ItemGroup> YODAS_PATH_BLOCKS_GROUP_KEY =
            RegistryKey.of(
                    RegistryKeys.ITEM_GROUP,
                    Identifier.of(YodaSPath.MOD_ID, "blocks")
            );

    public static final ItemGroup YODAS_PATH_GROUP =
            Registry.register(
                    Registries.ITEM_GROUP,
                    YODAS_PATH_GROUP_KEY,
                    FabricItemGroup.builder()
                            .icon(() -> new ItemStack(ModItems.LIGHT_SABER))
                            .displayName(
                                    Text.translatable(
                                            "itemgroup.yodaspath.yodas_path"
                                    )
                            )
                            .build()
            );

    public static final ItemGroup YODAS_PATH_ORES_GROUP =
            Registry.register(
                    Registries.ITEM_GROUP,
                    YODAS_PATH_ORES_GROUP_KEY,
                    FabricItemGroup.builder()
                            .icon(() -> new ItemStack(ModBlocks.KYBER_ORE))
                            .displayName(
                                    Text.translatable(
                                            "itemgroup.yodaspath.ores"
                                    )
                            )
                            .build()
            );

    public static final ItemGroup YODAS_PATH_BLOCKS_GROUP =
            Registry.register(
                    Registries.ITEM_GROUP,
                    YODAS_PATH_BLOCKS_GROUP_KEY,
                    FabricItemGroup.builder()
                            .icon(() -> new ItemStack(ModBlocks.TATOOINE_SAND))
                            .displayName(
                                    Text.translatable(
                                            "itemgroup.yodaspath.blocks"
                                    )
                            )
                            .build()
            );


    public static void initialize() {

        ItemGroupEvents.modifyEntriesEvent(YODAS_PATH_GROUP_KEY)
                .register(entries -> {
                    entries.add(ModItems.LIGHT_SABER);

                    entries.add(ModItems.BLASTER);
                    entries.add(ModItems.BLASTER_CELL);

                    entries.add(ModItems.KYBER_CRYSTAL);

                    entries.add(ModItems.WHITE_KYBER_CRYSTAL);
                    entries.add(ModItems.ORANGE_KYBER_CRYSTAL);
                    entries.add(ModItems.MAGENTA_KYBER_CRYSTAL);
                    entries.add(ModItems.LIGHT_BLUE_KYBER_CRYSTAL);
                    entries.add(ModItems.YELLOW_KYBER_CRYSTAL);
                    entries.add(ModItems.LIME_KYBER_CRYSTAL);
                    entries.add(ModItems.PINK_KYBER_CRYSTAL);
                    entries.add(ModItems.GRAY_KYBER_CRYSTAL);
                    entries.add(ModItems.LIGHT_GRAY_KYBER_CRYSTAL);
                    entries.add(ModItems.CYAN_KYBER_CRYSTAL);
                    entries.add(ModItems.PURPLE_KYBER_CRYSTAL);
                    entries.add(ModItems.BLUE_KYBER_CRYSTAL);
                    entries.add(ModItems.BROWN_KYBER_CRYSTAL);
                    entries.add(ModItems.GREEN_KYBER_CRYSTAL);
                    entries.add(ModItems.RED_KYBER_CRYSTAL);
                    entries.add(ModItems.BLACK_KYBER_CRYSTAL);
                });

        ItemGroupEvents.modifyEntriesEvent(YODAS_PATH_ORES_GROUP_KEY)
                .register(entries -> {
                    entries.add(ModBlocks.KYBER_ORE);
                    entries.add(ModBlocks.DEEPSLATE_KYBER_ORE);
                });

        ItemGroupEvents.modifyEntriesEvent(YODAS_PATH_BLOCKS_GROUP_KEY)
                .register(entries -> {
                    entries.add(ModBlocks.TATOOINE_SAND);
                    entries.add(ModBlocks.TATOOINE_SANDSTONE);
                    entries.add(ModBlocks.TATOOINE_SANDSTONE_STAIRS);
                    entries.add(ModBlocks.TATOOINE_SANDSTONE_SLAB);
                    entries.add(ModBlocks.TATOOINE_CHISELED_SANDSTONE);
                    entries.add(ModBlocks.TATOOINE_CHISELED_SANDSTONE_STAIRS);
                    entries.add(ModBlocks.TATOOINE_CHISELED_SLAB);
                    entries.add(ModBlocks.TATOOINE_SANDSTONE_WALL);
                });


        YodaSPath.LOGGER.info(
                "Registering item groups for {}",
                YodaSPath.MOD_ID
        );
    }
}