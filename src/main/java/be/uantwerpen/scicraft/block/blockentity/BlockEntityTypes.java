package be.uantwerpen.scicraft.block.blockentity;

import be.uantwerpen.scicraft.Scicraft;
import be.uantwerpen.scicraft.block.Blocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BlockEntityTypes {

    public static final BlockEntityType<SchrodingerChestBlockEntity> SCHRODINGER_CHEST = register(FabricBlockEntityTypeBuilder.create(SchrodingerChestBlockEntity::new, Blocks.SCHRODINGER_CHEST).build(null), "schrodingers_box");

    private static <T extends BlockEntity> BlockEntityType<T> register(BlockEntityType<T> entityType, String identifier) {
        return Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(Scicraft.MOD_ID, identifier), entityType);
    }

    /**
     * Main class method
     * Registers all Blocks
     */
    public static void registerBlockEntityTypes() {
        Scicraft.LOGGER.info("registering blockentitytypes");
    }
}
