package dev.raenmel.yodaspath.network;

import dev.raenmel.yodaspath.YodaSPath;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record DeflectPayload() implements CustomPayload {

    public static final Id<DeflectPayload> ID =
            new Id<>(Identifier.of(YodaSPath.MOD_ID, "deflect"));

    public static final PacketCodec<PacketByteBuf, DeflectPayload> CODEC =
            PacketCodec.unit(new DeflectPayload());

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}