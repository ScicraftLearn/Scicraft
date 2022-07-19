package be.uantwerpen.scicraft.block;

import be.uantwerpen.scicraft.Scicraft;
import be.uantwerpen.scicraft.entity.Entities;
import be.uantwerpen.scicraft.gui.LewisBlockScreenHandler;
import be.uantwerpen.scicraft.inventory.ImplementedInventory;
import be.uantwerpen.scicraft.lewisrecipes.DelegateSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;

public class LewisBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(36, ItemStack.EMPTY);
    private final int[] delegatedProperties;

    //PropertyDelegate is an interface which is implemented inline here.
    //It can normally contain multiple integers as data identified by the index.
    private final PropertyDelegate propertyDelegate;

    public LewisBlockEntity(BlockPos pos, BlockState state) {
        super(Entities.LEWIS_BLOCK_ENTITY, pos, state);

        delegatedProperties = new int[]{
                0, // textureID
                -1, // craftingProgress
                1, // slotItems
                1, // slotReady
                -1 // currentMolecule
        };

        propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return delegatedProperties[index];
            }

            @Override
            public void set(int index, int value) {
                delegatedProperties[index] = value;
                Scicraft.LOGGER.info(delegatedProperties);
            }

            //this is supposed to return the amount of integers you have in your delegate
            @Override
            public int size() {
                return DelegateSettings.DELEGATE_SIZE;
            }
        };
    }

    //From the ImplementedInventory Interface

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    //These Methods are from the NamedScreenHandlerFactory Interface
    //createMenu creates the ScreenHandler itself
    //getDisplayName will Provide its name which is normally shown at the top

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        //We provide *this* to the screenHandler as our class Implements Inventory
        //Only the Server has the Inventory at the start, this will be synced to the client in the ScreenHandler
        return new LewisBlockScreenHandler(syncId, playerInventory, this, propertyDelegate);
    }

    @Override
    public Text getDisplayName() {
        return new TranslatableText(getCachedState().getBlock().getTranslationKey());
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, this.inventory);
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, this.inventory);
    }

}
