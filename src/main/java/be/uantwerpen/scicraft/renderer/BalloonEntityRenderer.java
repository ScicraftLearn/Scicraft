package be.uantwerpen.scicraft.renderer;

import be.uantwerpen.scicraft.entity.BalloonEntity;
import be.uantwerpen.scicraft.entity.Entities;
import be.uantwerpen.scicraft.model.BalloonEntityModel;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3f;

public class BalloonEntityRenderer extends MobEntityRenderer<BalloonEntity, BalloonEntityModel> {


    public BalloonEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new BalloonEntityModel(context.getPart(Entities.BALLOON_MODEL)), 0.7F);
    }

    @Override
    public Identifier getTexture(BalloonEntity entity) {
        return new Identifier("scicraft:textures/entity/balloon/balloon.png");
    }

    @Override
    public void render(BalloonEntity mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        Quaternion rotation = Vec3f.POSITIVE_Y.getDegreesQuaternion(mobEntity.getRotationY());
        matrixStack.multiply(rotation);
        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}