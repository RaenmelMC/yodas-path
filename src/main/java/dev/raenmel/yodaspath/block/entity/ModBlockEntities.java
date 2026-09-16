package dev.raenmel.yodaspath.block.entity;

import dev.raenmel.yodaspath.YodaSPath;
import dev.raenmel.yodaspath.block.ModBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {

    public static final BlockEntityType<MoistureVaporatorBlockEntity>
            MOISTURE_VAPORATOR = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            Identifier.of(
                    YodaSPath.MOD_ID,
                    "moisture_vaporator"
            ),
            FabricBlockEntityTypeBuilder.create(
                    MoistureVaporatorBlockEntity::new,
                    ModBlocks.MOISTURE_VAPORATOR
            ).build()
    );

    public static final BlockEntityType<TatooineCrateBlockEntity>
            TATOOINE_CRATE = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            Identifier.of(
                    YodaSPath.MOD_ID,
                    "tatooine_crate"
            ),
            FabricBlockEntityTypeBuilder.create(
                    TatooineCrateBlockEntity::new,
                    ModBlocks.TATOOINE_CRATE
            ).build()
    );

    public static void initialize() {
        YodaSPath.LOGGER.info(
                "Registering Yoda's Path block entities"
        );
    }
}