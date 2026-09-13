package dev.raenmel.yodaspath.component;

import com.mojang.serialization.Codec;
import dev.raenmel.yodaspath.YodaSPath;
import net.minecraft.component.ComponentType;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModDataComponents {

    public static final ComponentType<String> KYBER_COLOR =
            Registry.register(
                    Registries.DATA_COMPONENT_TYPE,
                    Identifier.of(YodaSPath.MOD_ID, "kyber_color"),
                    ComponentType.<String>builder()
                            .codec(Codec.STRING)
                            .packetCodec(PacketCodecs.STRING.cast())
                            .build()
            );

    public static final ComponentType<Boolean> ACTIVATED =
            Registry.register(
                    Registries.DATA_COMPONENT_TYPE,
                    Identifier.of(YodaSPath.MOD_ID, "activated"),
                    ComponentType.<Boolean>builder()
                            .codec(Codec.BOOL)
                            .packetCodec(PacketCodecs.BOOL)
                            .build()
            );

    public static void initialize() {
        YodaSPath.LOGGER.info("Registering data components for {}", YodaSPath.MOD_ID);
    }
}