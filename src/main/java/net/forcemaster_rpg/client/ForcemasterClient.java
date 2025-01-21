package net.forcemaster_rpg.client;

import mod.azure.azurelibarmor.rewrite.render.armor.AzArmorRenderer;
import mod.azure.azurelibarmor.rewrite.render.armor.AzArmorRendererRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.forcemaster_rpg.client.armor.AkenArmorRenderer;
import net.forcemaster_rpg.client.armor.OrieneArmorRenderer;
import net.forcemaster_rpg.client.armor.PhaslebArmorRenderer;
import net.forcemaster_rpg.client.effect.ArcaneOverDriveRenderer;
import net.forcemaster_rpg.client.effect.BarqEsnaParticles;
import net.forcemaster_rpg.client.particle.Particles;
import net.forcemaster_rpg.client.particle.PunchParticle;
import net.forcemaster_rpg.effect.Effects;
import net.forcemaster_rpg.item.armor.Armors;
import net.minecraft.client.particle.ExplosionLargeParticle;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.util.Identifier;
import net.more_rpg_classes.client.particle.CustomSpellExplosionParticle;
import net.more_rpg_classes.client.particle.GroundParticle;
import net.spell_engine.api.effect.CustomModelStatusEffect;
import net.spell_engine.api.effect.CustomParticleStatusEffect;
import net.spell_engine.api.item.armor.Armor;
import net.spell_engine.api.render.CustomModels;

import java.util.List;
import java.util.function.Supplier;

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

        registerArmorRenderer(Armors.orieneArmorSet, OrieneArmorRenderer::oriene);
        registerArmorRenderer(Armors.phaslebArmorSet, PhaslebArmorRenderer::phasleb);
        registerArmorRenderer(Armors.akenArmorSet, AkenArmorRenderer::aken);

        CustomParticleStatusEffect.register(Effects.BARQ_ESNA.effect, new BarqEsnaParticles(1));
        CustomModelStatusEffect.register(Effects.ARCANE_OVERFLOW.effect, new ArcaneOverDriveRenderer());
    }

    private static void registerArmorRenderer(Armor.Set set, Supplier<AzArmorRenderer> armorRendererSupplier) {
        AzArmorRendererRegistry.register(armorRendererSupplier, set.head, set.chest, set.legs, set.feet);
    }
}
