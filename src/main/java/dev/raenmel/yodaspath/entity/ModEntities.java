package dev.raenmel.yodaspath.entity;

import dev.raenmel.yodaspath.YodaSPath;
import dev.raenmel.yodaspath.entity.custom.BlasterBoltEntity;
import dev.raenmel.yodaspath.entity.custom.JawaEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {

    public static final EntityType<BlasterBoltEntity> BLASTER_BOLT =
            Registry.register(
                    Registries.ENTITY_TYPE,
                    Identifier.of(
                            YodaSPath.MOD_ID,
                            "blaster_bolt"
                    ),
                    FabricEntityTypeBuilder
                            .<BlasterBoltEntity>create(
                                    SpawnGroup.MISC,
                                    BlasterBoltEntity::new
                            )
                            .dimensions(
                                    EntityDimensions.fixed(
                                            0.15F,
                                            0.15F
                                    )
                            )
                            .trackRangeBlocks(64)
                            .trackedUpdateRate(10)
                            .build()
            );

    public static final EntityType<JawaEntity> JAWA =
            Registry.register(
                    Registries.ENTITY_TYPE,
                    Identifier.of(
                            YodaSPath.MOD_ID,
                            "jawa"
                    ),
                    EntityType.Builder.create(
                                    JawaEntity::new,
                                    SpawnGroup.CREATURE
                            )
                            .dimensions(
                                    0.6F,
                                    1.25F
                            )
                            .build()
            );

    public static void initialize() {
        YodaSPath.LOGGER.info(
                "Registering entities for {}",
                YodaSPath.MOD_ID
        );
    }
}