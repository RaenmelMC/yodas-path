package dev.raenmel.yodaspath.world;

import dev.raenmel.yodaspath.YodaSPath;
import dev.raenmel.yodaspath.entity.ModEntities;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.world.biome.BiomeKeys;

public class ModEntitySpawns {

    public static void initialize() {

        YodaSPath.LOGGER.info(
                "Registering Yoda's Path entity spawns"
        );

        BiomeModifications.addSpawn(
                BiomeSelectors.includeByKey(
                        BiomeKeys.DESERT
                ),
                SpawnGroup.CREATURE,
                ModEntities.JAWA,
                8,
                2,
                4
        );
    }
}