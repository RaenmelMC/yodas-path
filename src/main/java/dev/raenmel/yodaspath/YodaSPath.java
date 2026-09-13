package dev.raenmel.yodaspath;

import dev.raenmel.yodaspath.block.ModBlocks;
import dev.raenmel.yodaspath.command.ModCommands;
import dev.raenmel.yodaspath.component.ModDataComponents;
import dev.raenmel.yodaspath.event.ModPlayerEvents;
import dev.raenmel.yodaspath.item.ModItemGroups;
import dev.raenmel.yodaspath.item.ModItems;
import dev.raenmel.yodaspath.network.ModNetworking;
import dev.raenmel.yodaspath.sound.ModSounds;
import dev.raenmel.yodaspath.world.ModWorldGeneration;
import net.fabricmc.api.ModInitializer;
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
		ModWorldGeneration.initialize();
		ModItemGroups.initialize();
	}
}