package be.uantwerpen.scicraft.block;

import be.uantwerpen.scicraft.block.blockentity.BlockEntityTypes;
import be.uantwerpen.scicraft.block.blockentity.SchrodingerChestBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.function.Supplier;

public class SchrodingerChestBlock extends ChestBlock {
    protected SchrodingerChestBlock(FabricBlockSettings settings, Supplier<BlockEntityType<? extends ChestBlockEntity>> supplier) {
        super(settings, supplier);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new SchrodingerChestBlockEntity(pos, state);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction direction = ctx.getPlayerFacing().getOpposite();
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        return this.getDefaultState().with(FACING, direction).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
//        return world.isClient & type == Entities.MY_CHEST ? (world1, pos, state1, blockEntity) -> ((MyChestBlockEntity)blockEntity).clientTick() : null;
        return world.isClient & type == BlockEntityTypes.SCHRODINGER_CHEST ? (world1, pos, state1, blockEntity) -> ChestBlockEntity.clientTick(world1, pos, state1, (ChestBlockEntity) blockEntity) : null;
    }
}
