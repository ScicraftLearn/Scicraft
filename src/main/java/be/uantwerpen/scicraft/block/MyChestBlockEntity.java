package be.uantwerpen.scicraft.block;

import be.uantwerpen.scicraft.Scicraft;
import be.uantwerpen.scicraft.entity.Entities;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.util.math.BlockPos;

public class MyChestBlockEntity extends ChestBlockEntity {
    protected MyChestBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }

    public MyChestBlockEntity(BlockPos pos, BlockState state) {
        this(Entities.MY_CHEST, pos, state);
    }

    @Environment(EnvType.CLIENT)
    public void clientTick() {
//        if (world != null && world.isClient) {
//            int viewerCount = countViewers();
//            lastAnimationAngle = animationAngle;
//            if (viewerCount > 0 && animationAngle == 0.0F) playSound(SoundEvents.BLOCK_CHEST_OPEN);
//            if (viewerCount == 0 && animationAngle > 0.0F || viewerCount > 0 && animationAngle < 1.0F) {
//                float float_2 = animationAngle;
//                if (viewerCount > 0) animationAngle += 0.1F;
//                else animationAngle -= 0.1F;
//                animationAngle = MathHelper.clamp(animationAngle, 0, 1);
//                if (animationAngle < 0.5F && float_2 >= 0.5F) playSound(SoundEvents.BLOCK_CHEST_CLOSE);
//            }
//        }
    }
}
