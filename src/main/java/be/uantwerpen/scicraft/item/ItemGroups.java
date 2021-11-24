package be.uantwerpen.scicraft.item;

import be.uantwerpen.scicraft.Scicraft;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import be.uantwerpen.scicraft.block.Blocks;

public class ItemGroups {
    public static final net.minecraft.item.ItemGroup QUANTUM_FIELDS = FabricItemGroupBuilder.create(
                    new Identifier(Scicraft.MOD_ID, "quantum_fields"))
            .icon(() -> new ItemStack(Items.PROTON))
            .build();

    public static final net.minecraft.item.ItemGroup ELEMENTARY_PARTICLES = FabricItemGroupBuilder.create(
                    new Identifier(Scicraft.MOD_ID, "elementary_particles"))
            .icon(() -> new ItemStack(Items.PION_NUL))
            .build();
}