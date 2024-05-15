package net.forcemaster_rpg.effect;

import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.more_rpg_classes.entity.attribute.MRPGCEntityAttributes;
import net.spell_engine.api.effect.*;
import net.spell_power.api.SpellPower;
import net.spell_power.api.SpellSchools;

import static net.forcemaster_rpg.ForcemasterClassMod.MOD_ID;
import static net.forcemaster_rpg.ForcemasterClassMod.effectsConfig;

public class Effects {
    //STONE_HAND
    public static StatusEffect STONE_HAND = new StoneHandEffect(StatusEffectCategory.BENEFICIAL, 0xbce5fe);

    //ARCANE OVERFLOW
    public static StatusEffect ARCANE_OVERFLOW = new ArcaneOverflowEffect(StatusEffectCategory.BENEFICIAL, 0xff8bef);

    //BARQ_ESNA
    public static StatusEffect BARQ_ESNA = new BarqEsnaEffect(StatusEffectCategory.HARMFUL, 0x8db4fe)
            .setVulnerability(SpellSchools.ARCANE, new SpellPower.Vulnerability(
                    effectsConfig.value.barq_esna_arcane_damage_vulnerability, 0.05F, 0.1F));



    public static void register(){
        STONE_HAND
                .addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE, "d1843e0f-8a63-4c96-a854-9c9444981042",
                effectsConfig.value.stone_hand_attack_damage_increase, EntityAttributeModifier.Operation.ADDITION)
                .addAttributeModifier(EntityAttributes.GENERIC_ATTACK_SPEED, "9bf58fe9-4b58-4174-9f41-a492a3510271",
                        effectsConfig.value.stone_hand_attack_speed_decrease, EntityAttributeModifier.Operation.ADDITION);

        ARCANE_OVERFLOW
                .addAttributeModifier(MRPGCEntityAttributes.ARCANE_FUSE_MODIFIER, "1077f55d-9d26-49ef-8804-f52eee72dca7",
                        effectsConfig.value.arcane_overflow_arcane_fuse_multiply, EntityAttributeModifier.Operation.MULTIPLY_BASE)
                .addAttributeModifier(SpellSchools.ARCANE.attribute, "0f88e4e8-becb-437b-9beb-6ef08fda3b49",
                        effectsConfig.value.arcane_overflow_arcane_spellpower_multiply, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);

        Synchronized.configure(BARQ_ESNA,true);
        Synchronized.configure(STONE_HAND,true);
        Synchronized.configure(ARCANE_OVERFLOW,true);


        int forcemaster_effectid = 200;
        Registry.register(Registries.STATUS_EFFECT, forcemaster_effectid++, new Identifier(MOD_ID, "barq_esna").toString(), BARQ_ESNA);
        Registry.register(Registries.STATUS_EFFECT, forcemaster_effectid++, new Identifier(MOD_ID, "stone_hand").toString(), STONE_HAND);
        Registry.register(Registries.STATUS_EFFECT, forcemaster_effectid++, new Identifier(MOD_ID, "arcane_overflow").toString(), ARCANE_OVERFLOW);
    }
}
