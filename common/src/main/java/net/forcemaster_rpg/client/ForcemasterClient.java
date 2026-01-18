package net.forcemaster_rpg.client;

import mod.azure.azurelibarmor.common.render.armor.AzArmorRenderer;
import mod.azure.azurelibarmor.common.render.armor.AzArmorRendererRegistry;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.forcemaster_rpg.client.armor.CustomArmorRenderer;
import net.forcemaster_rpg.client.effect.ArcaneOverDriveRenderer;
import net.forcemaster_rpg.client.effect.BarqEsnaParticles;
import net.forcemaster_rpg.client.particle.Particles;
import net.forcemaster_rpg.client.particle.PunchParticle;
import net.forcemaster_rpg.effect.ForcemasterEffects;
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
import static net.forcemaster_rpg.compat.CompatLoadingCheck.armoryLoadCheck;

public class ForcemasterClient{

    public static void init() {

        CustomModels.registerModelIds(List.of(
                Identifier.of(MOD_ID, "projectile/barqesna_projectile"),
                Identifier.of(MOD_ID, "projectile/fist_projectile"),
                ArcaneOverDriveRenderer.modelId
        ));

        registerArmorRenderer(Armors.orieneArmorSet.armorSet(), CustomArmorRenderer::oriene_armor);
        registerArmorRenderer(Armors.phaslebArmorSet.armorSet(), CustomArmorRenderer::phasleb_armor);
        registerArmorRenderer(Armors.akenArmorSet.armorSet(), CustomArmorRenderer::aken_armor);
        if (armoryLoadCheck()) {
            registerArmorRenderer(Armors.billporonArmorSet.armorSet(), CustomArmorRenderer::billporon_armor);
        }

        CustomParticleStatusEffect.register(ForcemasterEffects.BARQ_ESNA.effect, new BarqEsnaParticles(1));
        CustomModelStatusEffect.register(ForcemasterEffects.ARCANE_OVERFLOW.effect, new ArcaneOverDriveRenderer());
    }

    public static void registerParticleAppearances() {
        ParticleFactoryRegistry registry = ParticleFactoryRegistry.getInstance();

        registry.register(Particles.ASAL_EXPLODE, ExplosionLargeParticle.Factory::new);
        registry.register(Particles.BARQ_ESNA_FLAME, FlameParticle.Factory::new);
        registry.register(Particles.SONICHAND_VACUUM, CustomSpellExplosionParticle.Factory::new);
        registry.register(Particles.PUNCH, PunchParticle.Factory::new);
        registry.register(Particles.SONIC_PUNCH, PunchParticle.Factory::new);
        registry.register(Particles.GROUND_PUNCH, GroundParticle.DefaultFactory::new);
    }

    private static void registerArmorRenderer(Armor.Set set, Supplier<AzArmorRenderer> armorRendererSupplier) {
        AzArmorRendererRegistry.register(armorRendererSupplier, set.head, set.chest, set.legs, set.feet);
    }
}
