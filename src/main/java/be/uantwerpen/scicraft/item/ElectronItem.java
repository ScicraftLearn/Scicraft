package be.uantwerpen.scicraft.item;

import be.uantwerpen.scicraft.entity.ElectronEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import qouteall.imm_ptl.core.portal.Portal;

import java.util.EnumSet;

public class ElectronItem extends Item {
    public ElectronItem(Settings settings) {
        super(settings);
    }

    /**
     * When ElectronItem is right-clicked, use up the item if necessary and spawn the entity
     * @param world minecraft world
     * @param user player invoking the right click action
     * @param hand the hand of the user
     * @return TypedActionResult, indicates if the use of the item succeeded or not
     */
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand); // creates a new ItemStack instance of the user's itemStack in-hand

        /* TODO sound effect of Electron throw
         * Example with snowball sound
         * world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 1F);
         */

        /* TODO cooldown on the throw of an electron (like the cooldown on Ender Pearls)
         * Example cooldown of 5 ticks
         * user.getItemCooldownManager().set(this, 5);
         */
        if (!world.isClient) {
            // Spawns the electron entity with correct initial velocity (velocity has the same direction as the players looking direction)
            ElectronEntity electronEntity = new ElectronEntity(world, user);
            electronEntity.setItem(itemStack);
            electronEntity.setVelocity(user, user.getPitch(), user.getYaw(), user.getRoll(), 1.5F, 0F);
            world.spawnEntity(electronEntity);

            createPortals(world, user);
        }

        user.incrementStat(Stats.USED.getOrCreateStat(this));
        if (!user.getAbilities().creativeMode) {
            itemStack.decrement(1); // decrements itemStack if user is not in creative mode
        }

        return TypedActionResult.success(itemStack, world.isClient());
    }

    public void createPortals(World world, PlayerEntity user){
        /*
            TODO:
            - Client only rendering
            - make sure portals are always removed somehow
            - try with custom dimension
            - see what specificPlayerId does
            - Maybe there are other useful attributes?

         */

        Vec3d pos = user.getPos().add(new Vec3d(2, 0, 0)).floorAlongAxes(EnumSet.allOf(Direction.Axis.class));
        Vec3d dest = new Vec3d(100, 70, 100);
        createPortal(world, pos, dest, new Vec3d(0.5, 0.5, 1),
                new Vec3d(1, 0, 0), // axisW
                new Vec3d(0, 1, 0) // axisH
        );
        createPortal(world, pos, dest, new Vec3d(0.5, 0.5, 0),
                new Vec3d(0, 1, 0), // axisW
                new Vec3d(1, 0, 0) // axisH
        );

        createPortal(world, pos, dest, new Vec3d(0, 0.5, 0.5),
                new Vec3d(0, 0, 1), // axisW
                new Vec3d(0, 1, 0) // axisH
        );
        createPortal(world, pos, dest, new Vec3d(1, 0.5, 0.5),
                new Vec3d(0, 1, 0), // axisW
                new Vec3d(0, 0, 1) // axisH
        );

        createPortal(world, pos, dest, new Vec3d(0.5, 0, 0.5),
                new Vec3d(1, 0, 0), // axisW
                new Vec3d(0, 0, 1) // axisH
        );
        createPortal(world, pos, dest, new Vec3d(0.5, 1, 0.5),
                new Vec3d(0, 0, 1), // axisW
                new Vec3d(1, 0, 0) // axisH
        );
    }

    public void createPortal(World world, Vec3d origin, Vec3d destionation, Vec3d offset, Vec3d orientationW, Vec3d orientationH){
        Portal portal = Portal.entityType.create(world);
        if (portal == null) return;

        float scale = 16f;
        portal.setInteractable(false);
        portal.teleportable = false;
        portal.setScaleTransformation(scale);
        portal.fuseView = true;
        portal.setOriginPos(origin.add(offset));
        portal.setDestinationDimension(World.NETHER);
        portal.setDestination(destionation.add(offset.multiply(scale)));
        portal.setOrientationAndSize(
                orientationW, orientationH,
                1, // width
                1 // height
        );
        portal.world.spawnEntity(portal);
    }

}
