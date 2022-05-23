package be.uantwerpen.scicraft.item;

import be.uantwerpen.scicraft.entity.BalloonEntity;
import be.uantwerpen.scicraft.entity.Entities;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class BalloonItem extends Item {
    public BalloonItem(Item.Settings settings) {
        super(settings);
    }

    private BalloonEntity summon(World world, LivingEntity entity) {
        BalloonEntity balloon = Entities.BALLOON.create(world);
        balloon.refreshPositionAndAngles(entity.getX(), entity.getY(), entity.getZ(), 0.0F, 0.0F);
        world.spawnEntity(balloon);
        balloon.setBallooned(entity);
        return balloon;
    }

    @Override
    public void inventoryTick(ItemStack itemStack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(itemStack, world, entity, slot, selected);
        if (world.isClient()) {
            if (entity instanceof  PlayerEntity) {
                if (!((PlayerEntity) entity).getAbilities().creativeMode) {
                    //System.out.println(Integer.toString(slot));
                    if ((selected || slot==0) && !((PlayerEntity) entity).getAbilities().allowFlying) {
                        ((PlayerEntity) entity).getAbilities().allowFlying = true;
                        ((ClientPlayerEntity) entity).sendAbilitiesUpdate();
                        // TODO item takes damage
                    } else if (!selected && slot!=0 && ((PlayerEntity) entity).getAbilities().allowFlying) {
                        ((PlayerEntity) entity).getAbilities().allowFlying = false;
                        ((PlayerEntity) entity).getAbilities().flying = false;
                        ((ClientPlayerEntity) entity).sendAbilitiesUpdate();
                    }
                }
            }
        }
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if(! (entity instanceof PlayerEntity)) {
            World world = user.getWorld();
            if(!world.isClient) {
                summon(world, entity);
                stack.decrement(1);
            }
            return ActionResult.success(world.isClient);
        }
        return ActionResult.PASS;
    }
}
