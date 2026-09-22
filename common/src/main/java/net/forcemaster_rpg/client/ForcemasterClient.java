package net.forcemaster_rpg.client;

import net.forcemaster_rpg.client.armor.CustomArmorRenderer;
import net.forcemaster_rpg.client.effect.BarqEsnaParticles;
import net.forcemaster_rpg.client.particle.Particles;
import net.forcemaster_rpg.effect.ForcemasterEffects;
import net.forcemaster_rpg.item.armor.Armors;
import net.forcemaster_rpg.item.weapons.KnuckleItem;
import net.forcemaster_rpg.item.weapons.WeaponsRegister;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.rpg_foundation.armor_api.client.ArmorRenderers;
import net.rpg_foundation.armor_api.client.GeoArmorRenderer;
import net.spell_engine.api.effect.CustomParticleStatusEffect;
import net.spell_engine.api.render.CustomModels;
import net.spell_engine.client.particle.SpellParticle;
import net.spell_engine.rpg_series.item.Armor;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class ForcemasterClient{

    public static void init() {

        registerArmorRenderer(Armors.orieneArmorSet.armorSet(), CustomArmorRenderer::oriene_armor);
        registerArmorRenderer(Armors.phaslebArmorSet.armorSet(), CustomArmorRenderer::phasleb_armor);
        registerArmorRenderer(Armors.akenArmorSet.armorSet(), CustomArmorRenderer::aken_armor);
        registerArmorRenderer(Armors.billporonArmorSet.armorSet(), CustomArmorRenderer::billporon_armor);

        CustomParticleStatusEffect.register(ForcemasterEffects.BARQ_ESNA.effect, new BarqEsnaParticles(1));
        CustomModels.registerModelIds(knuckleEquipModelIds());
    }

    public interface ParticleFactoryRegistrar {
        void register(ParticleType type, Function<SpriteProvider, ParticleFactory> factory);
    }

    public static void registerParticleAppearances(ParticleFactoryRegistrar registrar) {
        for (var entry: Particles.entries()) {
            registrar.register(entry.type(), provider -> new SpellParticle.Factory(provider, entry));
        }
    }

    private static void registerArmorRenderer(Armor.Set set, Supplier<GeoArmorRenderer> armorRendererSupplier) {
        ArmorRenderers.register(armorRendererSupplier.get(), set.head, set.chest, set.legs, set.feet);
    }

    private static List<Identifier> knuckleEquipModelIds() {
        List<Identifier> ids = new ArrayList<>();
        for (var entry : WeaponsRegister.entries) {
            Item item = entry.item();
            if (item instanceof KnuckleItem) {
                Identifier itemId = Registries.ITEM.getId(item);
                ids.add(new ModelIdentifier(itemId.getNamespace(), itemId.getPath() + "_model", "inventory"));
            }
        }
        return ids;
    }
}
