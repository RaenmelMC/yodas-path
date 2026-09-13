package dev.raenmel.yodaspath.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import dev.raenmel.yodaspath.component.ModDataComponents;
import dev.raenmel.yodaspath.item.ModItems;
import dev.raenmel.yodaspath.item.kyber.KyberColor;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class ModCommands {

    public static void initialize() {
        CommandRegistrationCallback.EVENT.register(
                (dispatcher, registryAccess, environment) -> dispatcher.register(
                        literal("yodaspath")
                                .then(
                                        literal("saber")
                                                .then(
                                                        argument("color", StringArgumentType.word())
                                                                .suggests((context, builder) -> {
                                                                    for (KyberColor color : KyberColor.values()) {
                                                                        builder.suggest(color.getId());
                                                                    }

                                                                    return builder.buildFuture();
                                                                })
                                                                .executes(context -> {
                                                                    ServerPlayerEntity player =
                                                                            context.getSource().getPlayerOrThrow();

                                                                    String colorId =
                                                                            StringArgumentType.getString(context, "color");

                                                                    KyberColor color =
                                                                            KyberColor.fromId(colorId);

                                                                    if (color == null) {
                                                                        context.getSource().sendError(
                                                                                Text.literal("Unknown Kyber color: " + colorId)
                                                                        );
                                                                        return 0;
                                                                    }

                                                                    if (!player.getMainHandStack().isOf(ModItems.LIGHT_SABER)) {
                                                                        context.getSource().sendError(
                                                                                Text.literal("You must hold a Light Saber.")
                                                                        );
                                                                        return 0;
                                                                    }

                                                                    player.getMainHandStack().set(
                                                                            ModDataComponents.KYBER_COLOR,
                                                                            color.getId()
                                                                    );

                                                                    context.getSource().sendFeedback(
                                                                            () -> Text.literal(
                                                                                    "Kyber color changed to " + color.getId()
                                                                            ),
                                                                            false
                                                                    );

                                                                    return 1;
                                                                })
                                                )
                                )
                )
        );
    }
}