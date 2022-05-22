package be.uantwerpen.scicraft.renderer;

import be.uantwerpen.scicraft.entity.BalloonEntity;
import be.uantwerpen.scicraft.entity.Entities;
import be.uantwerpen.scicraft.model.BalloonEntityModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class BalloonEntityRenderer extends MobEntityRenderer<BalloonEntity, BalloonEntityModel> {
    public BalloonEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new BalloonEntityModel(context.getPart(Entities.BALLOON_MODEL)), 0.5f);
    }

    @Override
    public Identifier getTexture(BalloonEntity entity) {
        return new Identifier("scicraft:textures/entity/balloon/balloon.png");
    }
}