package be.uantwerpen.scicraft.renderer;

import be.uantwerpen.scicraft.Scicraft;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.enums.ChestType;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.block.entity.ChestBlockEntityRenderer;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;

@Environment(value=EnvType.CLIENT)
public class MyChestBlockEntityRenderer<T extends ChestBlockEntity> extends ChestBlockEntityRenderer<T> {
    private static final String BASE = "bottom";
    private static final String LID = "lid";
    private static final String LATCH = "lock";
    private boolean christmas;

    public MyChestBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        super(ctx);
        Scicraft.LOGGER.info("renderer created");
    }

    @Override
    public void render(T entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        super.render(entity, tickDelta, matrices, vertexConsumers, light, overlay);
        Scicraft.LOGGER.info("render");
//        World world = ((BlockEntity)entity).getWorld();
//        boolean bl = world != null;
//        BlockState blockState = bl ? ((BlockEntity)entity).getCachedState() : (BlockState)Blocks.CHEST.getDefaultState().with(ChestBlock.FACING, Direction.SOUTH);
//        ChestType chestType = blockState.contains(ChestBlock.CHEST_TYPE) ? blockState.get(ChestBlock.CHEST_TYPE) : ChestType.SINGLE;
//        Block block = blockState.getBlock();
//        if (!(block instanceof AbstractChestBlock)) {
//            return;
//        }
//        AbstractChestBlock abstractChestBlock = (AbstractChestBlock)block;
//        boolean bl2 = chestType != ChestType.SINGLE;
//        matrices.push();
//        float f = blockState.get(ChestBlock.FACING).asRotation();
//        matrices.translate(0.5, 0.5, 0.5);
//        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(-f));
//        matrices.translate(-0.5, -0.5, -0.5);
//        DoubleBlockProperties.PropertySource<Object> propertySource = bl ? abstractChestBlock.getBlockEntitySource(blockState, world, ((BlockEntity)entity).getPos(), true) : DoubleBlockProperties.PropertyRetriever::getFallback;
//        float g = propertySource.apply(ChestBlock.getAnimationProgressRetriever((ChestAnimationProgress)entity)).get(tickDelta);
//        g = 1.0f - g;
//        g = 1.0f - g * g * g;
//        int i = ((Int2IntFunction)propertySource.apply(new LightmapCoordinatesRetriever())).applyAsInt(light);
        SpriteIdentifier spriteIdentifier = TexturedRenderLayers.getChestTexture(entity, ChestType.SINGLE, this.christmas);
        Scicraft.LOGGER.info("sprite " + spriteIdentifier);
        VertexConsumer vertexConsumer = spriteIdentifier.getVertexConsumer(vertexConsumers, RenderLayer::getEntityCutout);
//        if (bl2) {
//            if (chestType == ChestType.LEFT) {
//                this.render(matrices, vertexConsumer, this.doubleChestRightLid, this.doubleChestRightLatch, this.doubleChestRightBase, g, i, overlay);
//            } else {
//                this.render(matrices, vertexConsumer, this.doubleChestLeftLid, this.doubleChestLeftLatch, this.doubleChestLeftBase, g, i, overlay);
//            }
//        } else {
//            this.render(matrices, vertexConsumer, this.singleChestLid, this.singleChestLatch, this.singleChestBase, g, i, overlay);
//        }
//        matrices.pop();
    }

    private void render(MatrixStack matrices, VertexConsumer vertices, ModelPart lid, ModelPart latch, ModelPart base, float openFactor, int light, int overlay) {
        latch.pitch = lid.pitch = -(openFactor * 1.5707964f);
        lid.render(matrices, vertices, light, overlay);
        latch.render(matrices, vertices, light, overlay);
        base.render(matrices, vertices, light, overlay);
    }
}

