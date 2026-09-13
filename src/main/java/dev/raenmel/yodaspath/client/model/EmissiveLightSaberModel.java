package dev.raenmel.yodaspath.client.model;

import dev.raenmel.yodaspath.item.custom.LightSaberItem;
import dev.raenmel.yodaspath.item.kyber.KyberColor;
import net.fabricmc.fabric.api.renderer.v1.Renderer;
import net.fabricmc.fabric.api.renderer.v1.RendererAccess;
import net.fabricmc.fabric.api.renderer.v1.material.RenderMaterial;
import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel;
import net.fabricmc.fabric.api.renderer.v1.model.ForwardingBakedModel;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.random.Random;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

public class EmissiveLightSaberModel extends ForwardingBakedModel {

    private final Map<KyberColor, BakedModel> emissiveModels;
    private final RenderMaterial emissiveMaterial;

    public EmissiveLightSaberModel(
            BakedModel baseModel,
            Map<KyberColor, BakedModel> emissiveModels
    ) {
        super(baseModel);

        this.emissiveModels = new EnumMap<>(emissiveModels);

        Renderer renderer = RendererAccess.INSTANCE.getRenderer();

        if (renderer == null) {
            throw new IllegalStateException("Fabric Renderer API is not available.");
        }

        this.emissiveMaterial = renderer.materialFinder()
                .emissive(true)
                .find();
    }

    @Override
    public boolean isVanillaAdapter() {
        return false;
    }

    @Override
    public void emitItemQuads(
            ItemStack stack,
            Supplier<Random> randomSupplier,
            RenderContext context
    ) {
        super.emitItemQuads(stack, randomSupplier, context);

        if (!LightSaberItem.isActivated(stack)) {
            return;
        }

        KyberColor color = LightSaberItem.getKyberColor(stack);

        if (color == null) {
            color = KyberColor.WHITE;
        }

        BakedModel emissiveModel = emissiveModels.get(color);

        if (emissiveModel == null) {
            return;
        }

        context.pushTransform(quad -> {
            quad.material(emissiveMaterial);
            return true;
        });

        ((FabricBakedModel) emissiveModel)
                .emitItemQuads(stack, randomSupplier, context);

        context.popTransform();
    }
}