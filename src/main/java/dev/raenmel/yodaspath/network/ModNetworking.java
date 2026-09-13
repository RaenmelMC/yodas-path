package dev.raenmel.yodaspath.network;

import dev.raenmel.yodaspath.event.ModPlayerEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class ModNetworking {

    public static void initialize() {

        PayloadTypeRegistry.playC2S().register(
                DeflectPayload.ID,
                DeflectPayload.CODEC
        );

        ServerPlayNetworking.registerGlobalReceiver(
                DeflectPayload.ID,
                (payload, context) -> {
                    context.server().execute(
                            () -> ModPlayerEvents.startDeflect(context.player())
                    );
                }
        );
    }
}