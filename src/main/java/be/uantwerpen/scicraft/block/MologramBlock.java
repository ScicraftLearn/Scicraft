package be.uantwerpen.scicraft.block;

import be.uantwerpen.scicraft.block.entity.MologramBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


public class MologramBlock extends BlockWithEntity {


    public MologramBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new MologramBlockEntity(pos, state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        //With inheriting from BlockWithEntity this defaults to INVISIBLE, so we need to change that!
        return BlockRenderType.MODEL;
    }

    @Override
    public ActionResult onUse(BlockState blockState, World world, BlockPos blockPos, PlayerEntity player, Hand hand, BlockHitResult blockHitResult) {
        if (world.isClient) return ActionResult.SUCCESS;
        BlockEntity blockEntity = world.getBlockEntity(blockPos);

        if (!(blockEntity instanceof MologramBlockEntity)) {
            return ActionResult.PASS;
        }
        Inventory blockInventory = ((MologramBlockEntity) blockEntity).getInventory();

        if (!player.getStackInHand(hand).isEmpty()) {
            // Check what is the first open slot and put an item from the player's hand there
            if (blockInventory.getStack(0).isEmpty()) {
                // Put the stack the player is holding into the inventory
                blockInventory.setStack(0, player.getStackInHand(hand).copy());
                blockInventory.getStack(0).setCount(1);
                // Decrement the stack from the player's hand
                player.getStackInHand(hand).decrement(1);
            } else {
                // If the inventory is full we'll print it's contents
                System.out.println("The first slot holds " + blockInventory.getStack(0));
            }
        } else { // open
            // If the player is not holding anything we'll get give him the items in the block entity one by one
            // Find the first slot that has an item and give it to the player
            if (!blockInventory.getStack(0).isEmpty()) {
                player.getInventory().offerOrDrop(blockInventory.getStack(0));
                blockInventory.removeStack(0);
            }
        }

        return ActionResult.SUCCESS;
    }


}
