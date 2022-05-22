package be.uantwerpen.scicraft.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.World;

public class BalloonEntity extends MobEntity {

    public BalloonEntity(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }
}