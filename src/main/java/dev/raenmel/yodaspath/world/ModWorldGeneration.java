package dev.raenmel.yodaspath.world;

import dev.raenmel.yodaspath.YodaSPath;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.PlacedFeature;

public class ModWorldGeneration {

    public static final RegistryKey<PlacedFeature> KYBER_ORE_PLACED =
            RegistryKey.of(
                    RegistryKeys.PLACED_FEATURE,
                    Identifier.of(
                            YodaSPath.MOD_ID,
                            "kyber_ore"
                    )
            );

    public static final RegistryKey<PlacedFeature> DEEPSLATE_KYBER_ORE_PLACED =
            RegistryKey.of(
                    RegistryKeys.PLACED_FEATURE,
                    Identifier.of(
                            YodaSPath.MOD_ID,
                            "deepslate_kyber_ore"
                    )
            );

    public static final RegistryKey<PlacedFeature> SCRAP_PILE_PLACED =
            RegistryKey.of(
                    RegistryKeys.PLACED_FEATURE,
                    Identifier.of(
                            YodaSPath.MOD_ID,
                            "scrap_pile"
                    )
            );

    public static final RegistryKey<PlacedFeature> DRY_TATOOINE_BUSH_PLACED =
            RegistryKey.of(
                    RegistryKeys.PLACED_FEATURE,
                    Identifier.of(
                            YodaSPath.MOD_ID,
                            "dry_tatooine_bush"
                    )
            );

    public static void initialize() {

        YodaSPath.LOGGER.info(
                "Registering Yoda's Path world generation"
        );

        BiomeModifications.addFeature(
                BiomeSelectors.foundInOverworld(),
                GenerationStep.Feature.UNDERGROUND_ORES,
                KYBER_ORE_PLACED
        );

        BiomeModifications.addFeature(
                BiomeSelectors.foundInOverworld(),
                GenerationStep.Feature.UNDERGROUND_ORES,
                DEEPSLATE_KYBER_ORE_PLACED
        );

        BiomeModifications.addFeature(
                BiomeSelectors.includeByKey(
                        BiomeKeys.DESERT
                ),
                GenerationStep.Feature.VEGETAL_DECORATION,
                SCRAP_PILE_PLACED
        );

        BiomeModifications.addFeature(
                BiomeSelectors.includeByKey(
                        BiomeKeys.DESERT
                ),
                GenerationStep.Feature.VEGETAL_DECORATION,
                DRY_TATOOINE_BUSH_PLACED
        );

    }
}