package be.uantwerpen.scicraft.block.blockentity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.util.math.BlockPos;

public class SchrodingerChestBlockEntity extends ChestBlockEntity {
    protected SchrodingerChestBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }

    public SchrodingerChestBlockEntity(BlockPos pos, BlockState state) {
        this(BlockEntityTypes.SCHRODINGER_CHEST, pos, state);
    }

}
