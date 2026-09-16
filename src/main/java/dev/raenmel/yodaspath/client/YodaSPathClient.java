package dev.raenmel.yodaspath.client;

import dev.raenmel.yodaspath.YodaSPath;
import dev.raenmel.yodaspath.block.ModBlocks;
import dev.raenmel.yodaspath.client.model.EmissiveLightSaberModel;
import dev.raenmel.yodaspath.client.model.JawaModel;
import dev.raenmel.yodaspath.client.model.ModEntityModelLayers;
import dev.raenmel.yodaspath.client.renderer.JawaRenderer;
import dev.raenmel.yodaspath.client.screen.TatooineCrateScreen;
import dev.raenmel.yodaspath.component.ModDataComponents;
import dev.raenmel.yodaspath.entity.ModEntities;
import dev.raenmel.yodaspath.item.ModItems;
import dev.raenmel.yodaspath.item.custom.LightSaberItem;
import dev.raenmel.yodaspath.item.kyber.KyberColor;
import dev.raenmel.yodaspath.network.DeflectPayload;
import dev.raenmel.yodaspath.screen.ModScreenHandlers;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.event.client.player.ClientPreAttackCallback;

import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.util.EnumMap;
import java.util.Map;

public class YodaSPathClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        EntityModelLayerRegistry.registerModelLayer(
                ModEntityModelLayers.JAWA,
                JawaModel::getTexturedModelData
        );

        EntityRendererRegistry.register(
                ModEntities.JAWA,
                JawaRenderer::new
        );

        EntityRendererRegistry.register(
                ModEntities.BLASTER_BOLT,
                FlyingItemEntityRenderer::new
        );

        BlockRenderLayerMap.INSTANCE.putBlock(
                ModBlocks.DRY_TATOOINE_BUSH,
                RenderLayer.getCutout()
        );

        HandledScreens.register(
                ModScreenHandlers.TATOOINE_CRATE,
                TatooineCrateScreen::new
        );

        Map<KyberColor, Identifier> emissiveIds =
                new EnumMap<>(KyberColor.class);

        for (KyberColor color : KyberColor.values()) {
            emissiveIds.put(
                    color,
                    Identifier.of(
                            YodaSPath.MOD_ID,
                            "item/light_saber_emissive_" + color.getId()
                    )
            );
        }

        ModelIdentifier lightSaberInventory =
                new ModelIdentifier(
                        Identifier.of(
                                YodaSPath.MOD_ID,
                                "light_saber"
                        ),
                        "inventory"
                );

        ModelLoadingPlugin.register(context -> {

            for (Identifier emissiveId : emissiveIds.values()) {
                context.addModels(emissiveId);
            }

            context.modifyModelAfterBake().register(
                    (model, modelContext) -> {

                        ModelIdentifier topLevelId =
                                modelContext.topLevelId();

                        if (topLevelId == null
                                || !topLevelId.equals(lightSaberInventory)) {
                            return model;
                        }

                        if (model == null) {
                            return null;
                        }

                        Map<KyberColor, BakedModel> bakedEmissiveModels =
                                new EnumMap<>(KyberColor.class);

                        for (Map.Entry<KyberColor, Identifier> entry
                                : emissiveIds.entrySet()) {

                            BakedModel emissiveModel =
                                    modelContext.baker().bake(
                                            entry.getValue(),
                                            modelContext.settings()
                                    );

                            if (emissiveModel != null) {
                                bakedEmissiveModels.put(
                                        entry.getKey(),
                                        emissiveModel
                                );
                            } else {
                                YodaSPath.LOGGER.error(
                                        "Unable to bake emissive model for Kyber color {}",
                                        entry.getKey().getId()
                                );
                            }
                        }

                        YodaSPath.LOGGER.info(
                                "Wrapping Light Saber with {} emissive models",
                                bakedEmissiveModels.size()
                        );

                        return new EmissiveLightSaberModel(
                                model,
                                bakedEmissiveModels
                        );
                    }
            );
        });

        ModelPredicateProviderRegistry.register(
                ModItems.LIGHT_SABER,
                Identifier.of(
                        YodaSPath.MOD_ID,
                        "activated"
                ),
                (stack, world, entity, seed) ->
                        LightSaberItem.isActivated(stack)
                                ? 1.0F
                                : 0.0F
        );

        ModelPredicateProviderRegistry.register(
                ModItems.LIGHT_SABER,
                Identifier.of(
                        YodaSPath.MOD_ID,
                        "kyber_color"
                ),
                (stack, world, entity, seed) -> {

                    String colorId =
                            stack.get(ModDataComponents.KYBER_COLOR);

                    if (colorId == null) {
                        return 0.0F;
                    }

                    KyberColor color =
                            KyberColor.fromId(colorId);

                    if (color == null) {
                        return 0.0F;
                    }

                    return switch (color) {
                        case WHITE -> 0.00F;
                        case ORANGE -> 0.01F;
                        case MAGENTA -> 0.02F;
                        case LIGHT_BLUE -> 0.03F;
                        case YELLOW -> 0.04F;
                        case LIME -> 0.05F;
                        case PINK -> 0.06F;
                        case GRAY -> 0.07F;
                        case LIGHT_GRAY -> 0.08F;
                        case CYAN -> 0.09F;
                        case PURPLE -> 0.10F;
                        case BLUE -> 0.11F;
                        case BROWN -> 0.12F;
                        case GREEN -> 0.13F;
                        case RED -> 0.14F;
                        case BLACK -> 0.15F;
                    };
                }
        );

        ClientPreAttackCallback.EVENT.register(
                (client, player, clickCount) -> {

                    if (clickCount <= 0) {
                        return false;
                    }

                    ItemStack stack =
                            player.getMainHandStack();

                    if (!stack.isOf(ModItems.LIGHT_SABER)) {
                        return false;
                    }

                    if (!LightSaberItem.isActivated(stack)) {
                        return false;
                    }

                    ClientPlayNetworking.send(
                            new DeflectPayload()
                    );

                    return false;
                }
        );
    }
}