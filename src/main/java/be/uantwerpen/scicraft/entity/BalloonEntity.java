package be.uantwerpen.scicraft.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.EntityAttachS2CPacket;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BalloonEntity extends MobEntity {
    private static final double MAX_DISTANCE = 2.0;
    public static List<UUID> balloons = new ArrayList<>();

    private boolean liftoff = false;
    private int ballooned_id = 0;
    private LivingEntity ballooned = null;
    private UUID ballooned_uuid = null;
    private boolean helium = true;

    public BalloonEntity(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
        setHealth(1.0f);
        inanimate = true;
        setNoGravity(true);
        balloons.add(getUuid());
    }

    public Vec3d getLeashOffset() {
        return new Vec3d(0.0D, (double)(0.5F * this.getStandingEyeHeight()), (double)(this.getWidth() * 0.2F));
    }

    public LivingEntity getBallooned() {
        if(ballooned == null && ballooned_id != 0 && world.isClient) {
            ballooned = (LivingEntity) world.getEntityById(ballooned_id);
        }
        if(ballooned == null && world instanceof ServerWorld) {
            Entity entity = ((ServerWorld)this.world).getEntity(ballooned_uuid);
            if(entity != null) {
                setBallooned((LivingEntity) entity, true);
            }
        }
        return ballooned;
    }

    public void unsetBallooned(boolean sendPacket) {
        ballooned = null;
        ballooned_id = 0;
        if (!this.world.isClient && sendPacket && this.world instanceof ServerWorld) {
            ((ServerWorld)this.world).getChunkManager().sendToOtherNearbyPlayers(this, new EntityAttachS2CPacket(this, (Entity)null));
        }
        discard();
    }

    public void setBallooned(LivingEntity target, boolean sendUpdate) {
        ballooned_id = target.getId();
        ballooned_uuid = target.getUuid();
        ballooned = target;
        if (!world.isClient && sendUpdate && world instanceof ServerWorld) {
            ((ServerWorld)this.world).getChunkManager().sendToOtherNearbyPlayers(this, new EntityAttachS2CPacket(this, ballooned));
        }
    }

    public void setHelium(boolean new_value) {
        helium = new_value;
    }

    public boolean getHelium() {
        return helium;
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        if (ballooned != null) {
            nbt.putUuid("Ballooned", ballooned.getUuid());
        }
        nbt.putBoolean("CanLiftOff", helium);
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("Ballooned")) {
            ballooned_uuid = nbt.getUuid("Ballooned");
            if(world instanceof ServerWorld) {
                Entity entity = ((ServerWorld)this.world).getEntity(ballooned_uuid);
                if(entity != null) {
                    setBallooned((LivingEntity) entity, true);
                }
            }
        }
        if(nbt.contains("CanLiftOff")) {
            helium = nbt.getBoolean("CanLiftOff");
        }
    }

    @Override
    public boolean cannotDespawn() {
        // Prevents the balloon from disappearing when you're not looking
        return true;
    }

    @Override
    public void tick() {
        super.tick();
        addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 10, 3, false, false));
        LivingEntity target = getBallooned();
        if(target != null) {
            Vec3d mpos = getPos();
            if(mpos.getY() >= 300) {
                // POP GOES THE BALLOON
                unsetBallooned(true);
                return;
            }
            Vec3d tpos = target.getPos();
            double distance = tpos.distanceTo(mpos);
            if(distance >= MAX_DISTANCE) {
                Vec3d direction = new Vec3d(tpos.x - mpos.x, tpos.y - mpos.y, tpos.z - mpos.z).normalize();
                setVelocity(direction.multiply(0.1));
                liftoff = true;
            }
            if(liftoff && helium) {
                target.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 3, 1, false, false));
            }
        }
    }
}