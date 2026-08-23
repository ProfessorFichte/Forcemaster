package net.forcemaster_rpg.client.effect;

import net.forcemaster_rpg.client.particle.Particles;
import net.minecraft.entity.LivingEntity;
import net.spell_engine.api.effect.CustomParticleStatusEffect;
import net.spell_engine.api.spell.fx.ParticleGroup;
import net.spell_engine.api.spell.fx.ParticleGroupBuilder;
import net.spell_engine.fx.ParticleHelper;

public class BarqEsnaParticles implements CustomParticleStatusEffect.Spawner {

    private final ParticleGroup particles;

    public BarqEsnaParticles(int particleCount) {
        this.particles = ParticleGroupBuilder.of(Particles.BARQ_ESNA_FLAME)
                .batch(b -> b.shape(ParticleGroup.Shape.SPHERE).count(particleCount).speed(0.1F, 0.3F));
    }

    @Override
    public void spawnParticles(LivingEntity livingEntity, int amplifier) {
        var scaledParticles = particles.copy();
        scaledParticles.batch.count *= (1);
        ParticleHelper.play(livingEntity.getWorld(), livingEntity, scaledParticles);
    }
}
