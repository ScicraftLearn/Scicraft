package be.uantwerpen.scicraft.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

public class HologramParticle extends SpriteBillboardParticle {


    /*Constructor that sets all the variables of the particle effect*/
    protected HologramParticle(ClientWorld level, double xCoord, double yCoord, double zCoord,
                              SpriteProvider spriteSet, double xd, double yd, double zd) {
        super(level, xCoord, yCoord, zCoord, xd, yd, zd);

        this.velocityMultiplier = 0.1F;
        this.scale *= 6F;
        this.maxAge = 60;
        this.setSpriteForAge(spriteSet);

    }

    @Override
    public void tick() {
        super.tick();
        fadeOut();
    }

    //linear function so the particles fadeout over the requested time (age)
    private void fadeOut() {
        this.alpha = (-(1/(float)maxAge) * age + 1);
    }


    //sets an translucent effect over the sprite that is used as particle
    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }


    //class that creates the particle so that it can be registered
    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider sprites;

        public Factory(SpriteProvider spriteSet) {
            this.sprites = spriteSet;
        }

        public Particle createParticle(DefaultParticleType particleType, ClientWorld level, double x, double y, double z,
                                       double dx, double dy, double dz) {
            return new HologramParticle(level, x, y, z, this.sprites, dx, dy, dz);
        }
    }
}
