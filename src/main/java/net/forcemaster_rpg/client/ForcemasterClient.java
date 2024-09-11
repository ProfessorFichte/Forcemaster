package net.forcemaster_rpg.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.forcemaster_rpg.client.effect.ArcaneOverDriveRenderer;
import net.forcemaster_rpg.client.effect.BarqEsnaParticles;
import net.forcemaster_rpg.client.particle.Particles;
import net.forcemaster_rpg.client.particle.PunchParticle;
import net.forcemaster_rpg.effect.Effects;
import net.minecraft.client.particle.ExplosionLargeParticle;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.util.Identifier;
import net.more_rpg_classes.client.particle.CustomSpellExplosionParticle;
import net.more_rpg_classes.client.particle.GroundParticle;
import net.spell_engine.api.effect.CustomModelStatusEffect;
import net.spell_engine.api.effect.CustomParticleStatusEffect;
import net.spell_engine.api.render.CustomModels;

import java.util.List;

import static net.forcemaster_rpg.ForcemasterClassMod.MOD_ID;
@Environment(EnvType.CLIENT)
public class ForcemasterClient implements ClientModInitializer {

    public void onInitializeClient() {

        CustomModels.registerModelIds(List.of(
                Identifier.of(MOD_ID, "projectile/barqesna_projectile"),
                Identifier.of(MOD_ID, "projectile/fist_projectile"),
                ArcaneOverDriveRenderer.modelId
        ));
        ParticleFactoryRegistry.getInstance().register(Particles.ASAL_EXPLODE, ExplosionLargeParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(Particles.BARQ_ESNA_FLAME, FlameParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(Particles.SONICHAND_VACUUM, CustomSpellExplosionParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(Particles.PUNCH, PunchParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(Particles.SONIC_PUNCH, PunchParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(Particles.GROUND_PUNCH, GroundParticle.DefaultFactory::new);

        CustomParticleStatusEffect.register(Effects.BARQ_ESNA.effect, new BarqEsnaParticles(1));
        CustomModelStatusEffect.register(Effects.ARCANE_OVERFLOW.effect, new ArcaneOverDriveRenderer());
    }
}
