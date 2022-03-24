package be.uantwerpen.scicraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

public class AtomItem extends BlockItem {

    private final int atomicNumber;
    private final String symbol;

    public AtomItem(Block block, Item.Settings settings, int atomicNumber, String symbol) {
        super(block, settings);
        this.atomicNumber = atomicNumber;
        this.symbol = symbol;
    }

    public AtomItem(Item.Settings settings, int atomicNumber, String symbol) {
        super(Blocks.DIRT, settings);
        this.atomicNumber = atomicNumber;
        this.symbol = symbol;
    }

    public int getAtomicNumber(){
        return atomicNumber;
    }

    public String getSymbol() {
        return symbol;
    }
}
