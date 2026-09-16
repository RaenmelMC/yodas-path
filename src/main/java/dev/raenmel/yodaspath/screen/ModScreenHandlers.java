package dev.raenmel.yodaspath.screen;

import dev.raenmel.yodaspath.YodaSPath;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreenHandlers {

    public static final ScreenHandlerType<TatooineCrateScreenHandler>
            TATOOINE_CRATE = Registry.register(
            Registries.SCREEN_HANDLER,
            Identifier.of(
                    YodaSPath.MOD_ID,
                    "tatooine_crate"
            ),
            new ScreenHandlerType<>(
                    TatooineCrateScreenHandler::new,
                    null
            )
    );

    public static void initialize() {
        YodaSPath.LOGGER.info(
                "Registering Yoda's Path screen handlers"
        );
    }
}