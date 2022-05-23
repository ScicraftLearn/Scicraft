package be.uantwerpen.scicraft.model;

import be.uantwerpen.scicraft.entity.BalloonEntity;
import com.google.common.collect.ImmutableList;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;

public class BalloonEntityModel extends EntityModel<BalloonEntity> {

    private final ModelPart base;

    public BalloonEntityModel(ModelPart root) {
        this.base = root.getChild("main");
    }

    @Override
    public void setAngles(BalloonEntity entity, float limbAngle, float limbDistance,
                          float animationProgress, float headYaw, float headPitch) {

    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay,
                       float red, float green, float blue, float alpha) {
        ImmutableList.of(this.base).forEach((modelRenderer) -> {
            modelRenderer.render(matrices, vertices, light, overlay, red, green, blue, alpha);
        });
    }

    public static TexturedModelData getTexturedModelData() {
        // TODO: fix graphical issues
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData modelPartData2 = modelPartData.addChild("main", ModelPartBuilder.create(), ModelTransform.NONE);

        try {
            Identifier identifier = new Identifier("scicraft:models/entity/balloon.json");
            String fileName = "assets/" + identifier.getNamespace() + "/" + identifier.getPath();

            ClassLoader classLoader = identifier.getClass().getClassLoader();
            URL resource = classLoader.getResource(fileName);
            FileReader fileReader = new FileReader(resource.getPath());

            JsonObject jo = JsonHelper.deserialize(fileReader);
            JsonArray elements = jo.getAsJsonArray("elements");
            for(int i = 0; i < elements.size(); ++i) {
                JsonObject bone = elements.get(i).getAsJsonObject();
                JsonArray from = bone.get("from").getAsJsonArray();
                JsonArray to = bone.get("to").getAsJsonArray();
                float fx = from.get(0).getAsFloat();
                float fy = from.get(1).getAsFloat();
                float fz = from.get(2).getAsFloat();
                float tx = to.get(0).getAsFloat();
                float ty = to.get(1).getAsFloat();
                float tz = to.get(2).getAsFloat();
                modelPartData2.addChild("bone" + i, ModelPartBuilder.create().uv(0, 0)
                        .cuboid(fx, fy, fz, tx - fx, ty - fy, tz - fz), ModelTransform.NONE);
            }
            JsonArray sizes = jo.getAsJsonArray("texture_size");
            return TexturedModelData.of(modelData, sizes.get(0).getAsInt(), sizes.get(1).getAsInt());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        modelPartData.addChild(EntityModelPartNames.CUBE, ModelPartBuilder.create().uv(0, 0)
                .cuboid(-6F, 12F, -6F, 12F, 12F, 12F),
                ModelTransform.pivot(0F, 0F, 0F));
        return TexturedModelData.of(modelData, 64, 64);
    }
}