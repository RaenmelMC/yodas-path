package dev.raenmel.yodaspath.client.model;

import dev.raenmel.yodaspath.YodaSPath;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class ModEntityModelLayers {

    public static final EntityModelLayer JAWA =
            new EntityModelLayer(
                    Identifier.of(
                            YodaSPath.MOD_ID,
                            "jawa"
                    ),
                    "main"
            );
}