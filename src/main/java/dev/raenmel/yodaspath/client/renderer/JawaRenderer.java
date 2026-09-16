package dev.raenmel.yodaspath.client.renderer;

import dev.raenmel.yodaspath.YodaSPath;
import dev.raenmel.yodaspath.client.model.JawaModel;
import dev.raenmel.yodaspath.client.model.ModEntityModelLayers;
import dev.raenmel.yodaspath.entity.custom.JawaEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class JawaRenderer
        extends MobEntityRenderer<JawaEntity, JawaModel> {

    private static final Identifier TEXTURE =
            Identifier.of(
                    YodaSPath.MOD_ID,
                    "textures/entity/jawa.png"
            );

    public JawaRenderer(EntityRendererFactory.Context context) {
        super(
                context,
                new JawaModel(
                        context.getPart(ModEntityModelLayers.JAWA)
                ),
                0.3F
        );
    }

    @Override
    public Identifier getTexture(JawaEntity entity) {
        return TEXTURE;
    }
}