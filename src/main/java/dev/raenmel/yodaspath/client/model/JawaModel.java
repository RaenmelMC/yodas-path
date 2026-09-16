package dev.raenmel.yodaspath.client.model;

import dev.raenmel.yodaspath.entity.custom.JawaEntity;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class JawaModel extends EntityModel<JawaEntity> {

    private final ModelPart jawa;
    private final ModelPart body;
    private final ModelPart leftArm;
    private final ModelPart rightArm;
    private final ModelPart head;

    public JawaModel(ModelPart root) {
        this.jawa = root.getChild("jawa");
        this.body = this.jawa.getChild("body");
        this.leftArm = this.jawa.getChild("left_arm");
        this.rightArm = this.jawa.getChild("right_arm");
        this.head = this.jawa.getChild("head");
    }

    public static TexturedModelData getTexturedModelData() {

        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData jawa = modelPartData.addChild(
                "jawa",
                ModelPartBuilder.create(),
                ModelTransform.pivot(
                        0.0F,
                        16.0F,
                        1.0F
                )
        );

        jawa.addChild(
                "body",
                ModelPartBuilder.create()
                        .uv(0, 12)
                        .cuboid(
                                -2.0F,
                                -8.0F,
                                -1.0F,
                                4.0F,
                                8.0F,
                                4.0F,
                                new Dilation(0.0F)
                        ),
                ModelTransform.pivot(
                        0.0F,
                        8.0F,
                        -1.0F
                )
        );

        jawa.addChild(
                "left_arm",
                ModelPartBuilder.create()
                        .uv(16, 12)
                        .cuboid(
                                2.0F,
                                0.0F,
                                0.0F,
                                2.0F,
                                7.0F,
                                2.0F,
                                new Dilation(0.0F)
                        ),
                ModelTransform.pivot(
                        0.0F,
                        0.0F,
                        -1.0F
                )
        );

        jawa.addChild(
                "right_arm",
                ModelPartBuilder.create()
                        .uv(16, 21)
                        .cuboid(
                                -4.0F,
                                0.0F,
                                0.0F,
                                2.0F,
                                7.0F,
                                2.0F,
                                new Dilation(0.0F)
                        ),
                ModelTransform.pivot(
                        0.0F,
                        0.0F,
                        -1.0F
                )
        );

        jawa.addChild(
                "head",
                ModelPartBuilder.create()
                        .uv(0, 0)
                        .cuboid(
                                -3.0F,
                                -6.0F,
                                -2.0F,
                                6.0F,
                                6.0F,
                                6.0F,
                                new Dilation(0.0F)
                        ),
                ModelTransform.pivot(
                        0.0F,
                        0.0F,
                        -1.0F
                )
        );

        return TexturedModelData.of(
                modelData,
                32,
                32
        );
    }

    @Override
    public void setAngles(
            JawaEntity entity,
            float limbAngle,
            float limbDistance,
            float animationProgress,
            float headYaw,
            float headPitch
    ) {
        this.head.yaw =
                headYaw * ((float) Math.PI / 180.0F);

        this.head.pitch =
                headPitch * ((float) Math.PI / 180.0F);

        this.rightArm.pitch =
                MathHelper.cos(
                        limbAngle * 0.6662F + (float) Math.PI
                ) * 2.0F * limbDistance * 0.5F;

        this.leftArm.pitch =
                MathHelper.cos(
                        limbAngle * 0.6662F
                ) * 2.0F * limbDistance * 0.5F;
    }

    @Override
    public void render(
            MatrixStack matrices,
            VertexConsumer vertexConsumer,
            int light,
            int overlay,
            int color
    ) {
        this.jawa.render(
                matrices,
                vertexConsumer,
                light,
                overlay,
                color
        );
    }
}