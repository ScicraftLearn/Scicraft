package be.uantwerpen.scicraft.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BalloonEntity extends MobEntity {
    private static final double MAX_DISTANCE = 1.5;

    // Really ugly way of doing this, but hey... It works...
    public static Map<BalloonEntity, LivingEntity> mapping = new HashMap<>();

    private LivingEntity ballooned;
    private boolean liftoff = false;

    public BalloonEntity(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
        setHealth(1.0f);
        inanimate = true;
        setNoGravity(true);
    }

    public Vec3d getLeashOffset() {
        return new Vec3d(0.0D, (double)(0.5F * this.getStandingEyeHeight()), (double)(this.getWidth() * 0.2F));
    }

    public LivingEntity getBallooned() {
        return ballooned;
    }

    public void setBallooned(LivingEntity target) {
        this.ballooned = target;
        mapping.put(this, target);
    }

    @Override
    public void onDeath(DamageSource source) {
        super.onDeath(source);
        mapping.remove(this);
    }

    public void unsetBallooned() {
        ballooned = null;
        kill();
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        if (ballooned != null) {
            nbt.putUuid("Ballooned", ballooned.getUuid());
        }
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        // TODO: fix this -- make sure the balloon stays connected on reload
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("Ballooned")) {
            UUID id = nbt.getUuid("Ballooned");
            for(Entity ent: world.getOtherEntities(null, Box.of(getPos(), 200, 200, 200))) {
                if(ent.getUuid() == id) {
                    setBallooned((LivingEntity) ent);
                    break;
                }
            }
        }
    }

    @Override
    public void tick() {
        super.tick();
        addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 10, 3, false, false));
        if(ballooned != null) {
            Vec3d mpos = getPos();
            Vec3d tpos = ballooned.getPos();
            double distance = tpos.distanceTo(mpos);
            if(distance > MAX_DISTANCE) {
                Vec3d direction = new Vec3d(tpos.x - mpos.x, tpos.y - mpos.y, tpos.z - mpos.z).normalize().multiply(0.1, 0.0, 0.1);
                setVelocity(direction);
                liftoff = true;
            }
            if(liftoff) {
                ballooned.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 3, 1, false, false));
            }
        }
    }
}