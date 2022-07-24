package be.uantwerpen.scicraft.renderer;

import be.uantwerpen.scicraft.Scicraft;
import be.uantwerpen.scicraft.block.MologramBlock;
import be.uantwerpen.scicraft.block.entity.MologramBlockEntity;
import be.uantwerpen.scicraft.particle.Particles;
import be.uantwerpen.scicraft.renderer.color.Color;
import be.uantwerpen.scicraft.renderer.color.Colors;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import org.apache.commons.lang3.ObjectUtils;
import java.util.Random;
import be.uantwerpen.scicraft.renderer.Renderer3d;

public class MologramBlockRenderer implements BlockEntityRenderer<MologramBlockEntity> {

    private static ItemStack stack = ItemStack.EMPTY;

    public MologramBlockRenderer(BlockEntityRendererFactory.Context ctx) {
    }

    @Override
    public void render(MologramBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        World world = entity.getWorld();
        if (world == null) return;

        stack = entity.getInventory().getStack(0);
        if (stack.isEmpty()) return;

        BlockPos pos = entity.getPos();
        // render beam
        //renderBeam(world, pos);
        // Render stack inside the block
        renderInsideOfBlock(matrices, vertexConsumers, light, overlay);
        rendersphere(1,10,10, pos, matrices);
        float xx = pos.getX();
        float yy = pos.getY();
        float zz = pos.getZ();
    }
    private void rendersphere(double r, int lats, int longs, BlockPos pos, MatrixStack matrices) {

        sphere_vertices(pos).drawAllWithoutVbo(matrices);
    }
    public static RenderActionBatch sphere_vertices(BlockPos pos){
        float[] fillColor = Colors.intArrayToFloatArray(Colors.RGBAIntToRGBA(Color.RED.toRGBAInt()));

        float x1 = ((float) pos.getX()+1);
        float y1 = (float) pos.getY()+3;
        float z1 = ((float) pos.getZ()+1);

        //        Matrix4f matrix = stack.peek().getPositionMatrix();
        BufferBuilder buffer = Tessellator.getInstance().getBuffer();
        buffer.begin(VertexFormat.DrawMode.TRIANGLES, VertexFormats.POSITION_COLOR);
        int i,j;
        for(i=1; i<11; i++){
            float lat0 = (float) (MathHelper.PI * (-0.5 + (float)(i-1)/10));
            float z0 = MathHelper.sin(lat0);
            float zr0 = MathHelper.cos(lat0);
            float lat1 = (float) (MathHelper.PI * (-0.5 + (float)(i)/10));
            float z = MathHelper.sin(lat1);
            float zr1 = MathHelper.cos(lat1);

            for(j=1; j<11; j++){
                float lng = 2 * MathHelper.PI * (float) (j-1)/10;
                float x = MathHelper.cos(lng);
                float y = MathHelper.sin(lng);
                float lng2 = 2 * MathHelper.PI * (float) j/10;
                float x2 = MathHelper.cos(lng2);
                float y2 = MathHelper.sin(lng2);

                buffer.vertex(0.25*x*zr0+x1, 0.25*y*zr0+y1, 0.25*z0+z1).color(fillColor[0], fillColor[1], fillColor[2], fillColor[3]).next();
                buffer.vertex(0.25*x*zr1+x1, 0.25*y*zr1+y1, 0.25*z+z1).color(fillColor[0], fillColor[1], fillColor[2], fillColor[3]).next();
                buffer.vertex(0.25*x2*zr0+x1, 0.25*y2*zr0+y1, 0.25*z0+z1).color(fillColor[0], fillColor[1], fillColor[2], fillColor[3]).next();

                buffer.vertex(0.25*x2*zr0+x1, 0.25*y2*zr0+y1, 0.25*z0+z1).color(fillColor[0], fillColor[1], fillColor[2], fillColor[3]).next();
                buffer.vertex(0.25*x*zr1+x1, 0.25*y*zr1+y1, 0.25*z+z1).color(fillColor[0], fillColor[1], fillColor[2], fillColor[3]).next();
                buffer.vertex(0.25*x2*zr1+x1, 0.25*y2*zr1+y1, 0.25*z+z1).color(fillColor[0], fillColor[1], fillColor[2], fillColor[3]).next();
            }
        }

        RenderAction action1 = new RenderAction(buffer.end(), GameRenderer.getPositionColorLightmapShader());

        return new RenderActionBatch(action1);
    }
    private static void renderBeam(World world, BlockPos pos) {
        renderBeamAsParticle(world, pos);
        //renderBeam()
    }
    private static void renderBeamAsParticle(World world, BlockPos pos){
        Random random = new Random();
        if(random.nextDouble()<0.2f){
            world.addParticle(Particles.HOLOGRAM_PARTICLE, pos.getX()+ 0.5d, pos.getY() + 1.75d, pos.getZ() + 0.5d,
                    0,0,0);
        }
    }
    private static void renderInsideOfBlock(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay){
        matrices.push();
        if (Block.getBlockFromItem(stack.getItem()) != Blocks.AIR) matrices.translate(0.5, 0, 0.5);
        else {
            matrices.translate(0.5, 0.10, 0.64);
            matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(-90));
        }

        MinecraftClient.getInstance().getItemRenderer().renderItem(stack, ModelTransformation.Mode.GROUND, light, overlay, matrices, vertexConsumers, 0);
        // Mandatory call after GL calls
        matrices.pop();
    }
    // code for beacon beam rendering --> inspiration
    /*
    public static void renderBeam(MatrixStack matrices, VertexConsumerProvider vertexConsumers, Identifier textureId, float tickDelta, float heightScale, long worldTime, int yOffset, int maxY, float[] color, float innerRadius, float outerRadius) {
        int i = yOffset + maxY;
        matrices.push();
        matrices.translate(0.5, 0.0, 0.5);
        float f = (float)Math.floorMod(worldTime, 40) + tickDelta;
        float g = maxY < 0 ? f : -f;
        float h = MathHelper.fractionalPart(g * 0.2F - (float)MathHelper.floor(g * 0.1F));
        float j = color[0];
        float k = color[1];
        float l = color[2];
        matrices.push();
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(f * 2.25F - 45.0F));
        float m = 0.0F;
        float p = 0.0F;
        float q = -innerRadius;
        float r = 0.0F;
        float s = 0.0F;
        float t = -innerRadius;
        float u = 0.0F;
        float v = 1.0F;
        float w = -1.0F + h;
        float x = (float)maxY * heightScale * (0.5F / innerRadius) + w;
        renderBeamLayer(matrices, vertexConsumers.getBuffer(RenderLayer.getBeaconBeam(textureId, false)), j, k, l, 1.0F, yOffset, i, 0.0F, innerRadius, innerRadius, 0.0F, q, 0.0F, 0.0F, t, 0.0F, 1.0F, x, w);
        matrices.pop();
        m = -outerRadius;
        float n = -outerRadius;
        p = -outerRadius;
        q = -outerRadius;
        u = 0.0F;
        v = 1.0F;
        w = -1.0F + h;
        x = (float)maxY * heightScale + w;
        renderBeamLayer(matrices, vertexConsumers.getBuffer(RenderLayer.getBeaconBeam(textureId, true)), j, k, l, 0.125F, yOffset, i, m, n, outerRadius, p, q, outerRadius, outerRadius, outerRadius, 0.0F, 1.0F, x, w);
        matrices.pop();
    }
    private static void renderBeamLayer(MatrixStack matrices, VertexConsumer vertices, float red, float green, float blue, float alpha, int yOffset, int height, float x1, float z1, float x2, float z2, float x3, float z3, float x4, float z4, float u1, float u2, float v1, float v2) {
        MatrixStack.Entry entry = matrices.peek();
        Matrix4f matrix4f = entry.getPositionMatrix();
        Matrix3f matrix3f = entry.getNormalMatrix();
        renderBeamFace(matrix4f, matrix3f, vertices, red, green, blue, alpha, yOffset, height, x1, z1, x2, z2, u1, u2, v1, v2);
        renderBeamFace(matrix4f, matrix3f, vertices, red, green, blue, alpha, yOffset, height, x4, z4, x3, z3, u1, u2, v1, v2);
        renderBeamFace(matrix4f, matrix3f, vertices, red, green, blue, alpha, yOffset, height, x2, z2, x4, z4, u1, u2, v1, v2);
        renderBeamFace(matrix4f, matrix3f, vertices, red, green, blue, alpha, yOffset, height, x3, z3, x1, z1, u1, u2, v1, v2);
    }
    private static void renderBeamFace(Matrix4f positionMatrix, Matrix3f normalMatrix, VertexConsumer vertices, float red, float green, float blue, float alpha, int yOffset, int height, float x1, float z1, float x2, float z2, float u1, float u2, float v1, float v2) {
        renderBeamVertex(positionMatrix, normalMatrix, vertices, red, green, blue, alpha, height, x1, z1, u2, v1);
        renderBeamVertex(positionMatrix, normalMatrix, vertices, red, green, blue, alpha, yOffset, x1, z1, u2, v2);
        renderBeamVertex(positionMatrix, normalMatrix, vertices, red, green, blue, alpha, yOffset, x2, z2, u1, v2);
        renderBeamVertex(positionMatrix, normalMatrix, vertices, red, green, blue, alpha, height, x2, z2, u1, v1);
    }
    private static void renderBeamVertex(Matrix4f positionMatrix, Matrix3f normalMatrix, VertexConsumer vertices, float red, float green, float blue, float alpha, int y, float x, float z, float u, float v) {
        vertices.vertex(positionMatrix, x, (float)y, z).color(red, green, blue, alpha).texture(u, v).overlay(OverlayTexture.DEFAULT_UV).light(15728880).normal(normalMatrix, 0.0F, 1.0F, 0.0F).next();
    }*/
}
