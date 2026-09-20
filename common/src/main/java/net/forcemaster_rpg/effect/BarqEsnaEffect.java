package net.forcemaster_rpg.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.spell_power.api.SpellSchools;
import net.spell_power.api.statuseffects.SpellVulnerabilityStatusEffect;

import static net.forcemaster_rpg.ForcemasterClassMod.tweaksConfig;

public class BarqEsnaEffect
    extends SpellVulnerabilityStatusEffect {

    public BarqEsnaEffect(StatusEffectCategory statusEffectCategory, int color) {
        super(statusEffectCategory, color);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        var damage = tweaksConfig.value.barq_esna_dot_damage_per_amplifier * (amplifier + 1);
        entity.damage(entity.getDamageSources().create(SpellSchools.ARCANE.damageType), damage);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        if (getCategory() != StatusEffectCategory.HARMFUL) {
            return false;
        }
        return duration % 40 == 0;
    }
}
