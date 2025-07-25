package net.forcemaster_rpg.effect;

import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.more_rpg_classes.entity.attribute.MRPGCEntityAttributes;
import net.spell_engine.api.effect.*;
import net.spell_power.api.SpellPower;
import net.spell_power.api.SpellSchools;

import java.util.ArrayList;

import static net.forcemaster_rpg.ForcemasterClassMod.MOD_ID;
import static net.forcemaster_rpg.ForcemasterClassMod.effectsConfig;

public class Effects {
    private static final ArrayList<Entry> entries = new ArrayList<>();
    public static class Entry {
        public final Identifier id;
        public final StatusEffect effect;
        public RegistryEntry<StatusEffect> registryEntry;
        public Entry(String name, StatusEffect effect) {
            this.id = Identifier.of(MOD_ID, name);
            this.effect = effect;
            entries.add(this);
        }
        public void register() {
            registryEntry = Registry.registerReference(Registries.STATUS_EFFECT, id, effect);
        }
        public Identifier modifierId() {
            return Identifier.of(MOD_ID, "effect." + id.getPath());
        }
    }

    public static final Entry STONE_HAND =  new Entry("stone_hand",new StoneHandEffect(StatusEffectCategory.BENEFICIAL, 0xbce5fe));
    public static final Entry ARCANE_OVERFLOW = new Entry("arcane_overflow",new ArcaneOverflowEffect(StatusEffectCategory.BENEFICIAL, 0xff8bef));
    public static final Entry BARQ_ESNA = new Entry("barq_esna",new BarqEsnaEffect(StatusEffectCategory.HARMFUL, 0x8db4fe)
            .setVulnerability(SpellSchools.ARCANE, new SpellPower.Vulnerability(
                    effectsConfig.value.barq_esna_arcane_damage_vulnerability, 0.1F, 0.2F)));

    public static void register(){
        STONE_HAND.effect
                .addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE, STONE_HAND.modifierId(),
                effectsConfig.value.stone_hand_attack_damage_increase, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE);

        ARCANE_OVERFLOW.effect
                .addAttributeModifier(MRPGCEntityAttributes.ARCANE_FUSE_MODIFIER, ARCANE_OVERFLOW.modifierId(),
                        effectsConfig.value.arcane_overflow_arcane_fuse_multiply, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE)
                .addAttributeModifier(SpellSchools.ARCANE.attributeEntry, ARCANE_OVERFLOW.modifierId(),
                        effectsConfig.value.arcane_overflow_arcane_spellpower_multiply, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE);
        BARQ_ESNA.effect
                .addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE, BARQ_ESNA.modifierId(),
                        effectsConfig.value.barq_esna_attack_damage_reduction,EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE);

        Synchronized.configure(BARQ_ESNA.effect,true);
        Synchronized.configure(STONE_HAND.effect,true);
        Synchronized.configure(ARCANE_OVERFLOW.effect,true);

        for (var entry: entries) {
            entry.register();
        }
    }
}
