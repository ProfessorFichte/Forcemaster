package net.forcemaster_rpg.client;

import mod.azure.azurelibarmor.common.render.armor.AzArmorRenderer;
import mod.azure.azurelibarmor.common.render.armor.AzArmorRendererRegistry;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.forcemaster_rpg.client.armor.CustomArmorRenderer;
import net.forcemaster_rpg.client.effect.BarqEsnaParticles;
import net.forcemaster_rpg.client.entity.NenSphereBeamRenderer;
import net.forcemaster_rpg.client.particle.Particles;
import net.forcemaster_rpg.effect.ForcemasterEffects;
import net.forcemaster_rpg.entity.NenSphereBeamEntity;
import net.forcemaster_rpg.item.armor.Armors;
import net.spell_engine.api.effect.CustomParticleStatusEffect;
import net.spell_engine.client.particle.SpellParticle;
import net.spell_engine.rpg_series.item.Armor;
import java.util.function.Supplier;

import static net.forcemaster_rpg.compat.CompatLoadingCheck.armoryLoadCheck;

public class ForcemasterClient{

    public static void init() {

        registerArmorRenderer(Armors.orieneArmorSet.armorSet(), CustomArmorRenderer::oriene_armor);
        registerArmorRenderer(Armors.phaslebArmorSet.armorSet(), CustomArmorRenderer::phasleb_armor);
        registerArmorRenderer(Armors.akenArmorSet.armorSet(), CustomArmorRenderer::aken_armor);
        if (armoryLoadCheck()) {
            registerArmorRenderer(Armors.billporonArmorSet.armorSet(), CustomArmorRenderer::billporon_armor);
        }

        CustomParticleStatusEffect.register(ForcemasterEffects.BARQ_ESNA.effect, new BarqEsnaParticles(1));

        EntityRendererRegistry.register(NenSphereBeamEntity.ENTITY_TYPE, NenSphereBeamRenderer::new);
    }

    public static void registerParticleAppearances() {
        ParticleFactoryRegistry registry = ParticleFactoryRegistry.getInstance();

        for (var entry: Particles.entries()) {
            registry.register(entry.type(), provider -> new SpellParticle.Factory(provider, entry));
        }
    }

    private static void registerArmorRenderer(Armor.Set set, Supplier<AzArmorRenderer> armorRendererSupplier) {
        AzArmorRendererRegistry.register(armorRendererSupplier, set.head, set.chest, set.legs, set.feet);
    }
}
