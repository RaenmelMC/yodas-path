package dev.raenmel.yodaspath;

import dev.raenmel.yodaspath.block.ModBlocks;
import dev.raenmel.yodaspath.block.entity.ModBlockEntities;
import dev.raenmel.yodaspath.command.ModCommands;
import dev.raenmel.yodaspath.component.ModDataComponents;
import dev.raenmel.yodaspath.entity.ModEntities;
import dev.raenmel.yodaspath.entity.custom.JawaEntity;
import dev.raenmel.yodaspath.event.ModBlockEvents;
import dev.raenmel.yodaspath.event.ModPlayerEvents;
import dev.raenmel.yodaspath.item.ModItemGroups;
import dev.raenmel.yodaspath.item.ModItems;
import dev.raenmel.yodaspath.network.ModNetworking;
import dev.raenmel.yodaspath.screen.ModScreenHandlers;
import dev.raenmel.yodaspath.sound.ModSounds;
import dev.raenmel.yodaspath.world.ModEntitySpawns;
import dev.raenmel.yodaspath.world.ModWorldGeneration;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.SpawnLocationTypes;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.world.Heightmap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class YodaSPath implements ModInitializer {

	public static final String MOD_ID = "yodaspath";

	public static final Logger LOGGER =
			LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		LOGGER.info("Initializing Yoda's Path");

		ModItems.initialize();
		ModDataComponents.initialize();
		ModCommands.initialize();
		ModSounds.initialize();
		ModPlayerEvents.initialize();
		ModNetworking.initialize();

		ModBlocks.initialize();
		ModEntities.initialize();
		ModBlockEntities.initialize();

		SpawnRestriction.register(
				ModEntities.JAWA,
				SpawnLocationTypes.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				JawaEntity::canSpawn
		);

		FabricDefaultAttributeRegistry.register(
				ModEntities.JAWA,
				JawaEntity.createJawaAttributes()
		);

		ModWorldGeneration.initialize();
		ModEntitySpawns.initialize();

		ModItemGroups.initialize();
		ModBlockEvents.initialize();
		ModScreenHandlers.initialize();
	}
}