package net.forcemaster_rpg.effect;

import net.forcemaster_rpg.spell.ForcemasterSpells;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Identifier;
import net.more_rpg_classes.entity.attribute.MRPGCEntityAttributes;
import net.spell_engine.rpg_series.config.AttributeModifier;
import net.spell_engine.rpg_series.config.ConfigFile;
import net.spell_engine.rpg_series.config.EffectConfig;
import net.spell_engine.api.effect.*;
import net.spell_power.api.SpellPower;
import net.spell_power.api.SpellSchools;

import java.util.ArrayList;
import java.util.List;

import static net.forcemaster_rpg.ForcemasterClassMod.MOD_ID;
import static net.forcemaster_rpg.ForcemasterClassMod.tweaksConfig;

public class ForcemasterEffects {
    public static final List<Effects.Entry> entries = new ArrayList<>();
    private static Effects.Entry add(Effects.Entry entry) {
        entries.add(entry);
        return entry;
    }

    public static final Effects.Entry STONE_HAND = add(new Effects.Entry(
            Identifier.of(MOD_ID, "stone_hand"),
            "Stonehand",
            "Increases Attack Damage, attacks with fist weapons have a chance to stun the target.",
            new StoneHandEffect(StatusEffectCategory.BENEFICIAL, 0xbce5fe),
            new EffectConfig(List.of(
                    new AttributeModifier(
                            EntityAttributes.GENERIC_ATTACK_DAMAGE.getIdAsString(),
                            0.25F,
                            EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
                    )
            ))
    ));

    public static final Effects.Entry ARCANE_OVERFLOW = add(new Effects.Entry(
            Identifier.of(MOD_ID, "arcane_overflow"),
            "Arcane Overflow",
            "Increases Arcane Spell Power and Arcane Fuse, enhancing Melee Hits with Arcane Magic.",
            new ArcaneOverflowEffect(StatusEffectCategory.BENEFICIAL, 0xff8bef),
            new EffectConfig(List.of(
                    new AttributeModifier(
                            MRPGCEntityAttributes.ARCANE_FUSE_MODIFIER.getIdAsString(),
                            0.05F,
                            EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
                    ),
                    new AttributeModifier(
                            SpellSchools.ARCANE.id.toString(),
                            0.02F,
                            EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
                    )
            ))
    ));

    public static final Effects.Entry BARQ_ESNA = add(new Effects.Entry(
            Identifier.of(MOD_ID, "barq_esna"),
            "Light of Baraqijal",
            "Reduces Offensive Attributes.",
            new BarqEsnaEffect(StatusEffectCategory.HARMFUL, 0x8db4fe),
            new EffectConfig(List.of(
                    new AttributeModifier(
                            EntityAttributes.GENERIC_ATTACK_DAMAGE.getIdAsString(),
                            -0.05F,
                            EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
                    ),
                    new AttributeModifier(
                            SpellSchools.GENERIC.id.toString(),
                            -0.05F,
                            EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
                    ),
                    new AttributeModifier(
                            "ranged_weapon:damage",
                            -0.05F,
                            EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
                    )
            ))
    ));

    public static final Effects.Entry NEN_FOCUS = add(new Effects.Entry(
            Identifier.of(MOD_ID, "nen_focus"),
            "Nen Focus",
            "You can now stack Arcane Overflow with melee attacks.",
            new CustomStatusEffect(StatusEffectCategory.BENEFICIAL, 0xff8bef),
            new EffectConfig(List.of(
            ))
    ));

    public static void register(ConfigFile.Effects config) {
        ((BarqEsnaEffect) BARQ_ESNA.effect).setVulnerability(
                SpellSchools.ARCANE,
                new SpellPower.Vulnerability(
                        tweaksConfig.value.barq_esna_arcane_damage_vulnerability, 0.025F, 0.05F
                )
        );

        GlowingItemStatusEffect.register(ARCANE_OVERFLOW.effect, ForcemasterSpells.FORCEMASTER_BLUE_COLOR, 0.1F);
        GlowingItemStatusEffect.register(NEN_FOCUS.effect, ForcemasterSpells.FORCEMASTER_BLUE_COLOR, 0.2F);
        for (var entry : entries) {
            Synchronized.configure(entry.effect, true);
        }

        Effects.register(entries, config.effects);
    }
}
