package net.forcemaster_rpg.util.custom;

import net.forcemaster_rpg.item.tag.ModItemTags;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.spell_engine.api.effect.SpellEngineEffects;
import net.spell_engine.api.spell.event.SpellHandlers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.spell_engine.api.spell.Spell;
import net.spell_engine.internals.SpellHelper;
import net.spell_power.api.SpellPower;

import static net.forcemaster_rpg.ForcemasterClassMod.tweaksConfig;

public class FistWeaponOnlyStunImpact implements SpellHandlers.CustomImpact {

    @Override
    public SpellHandlers.ImpactResult onSpellImpact(
            RegistryEntry<Spell> spell,
            SpellPower.Result powerResult,
            LivingEntity caster,
            Entity target,
            SpellHelper.ImpactContext context
    ) {
        ItemStack stack = caster.getEquippedStack(EquipmentSlot.MAINHAND);
        if(stack.isIn(ModItemTags.FIST_WEAPON)&& target instanceof LivingEntity livingEntity && target.isLiving() && !target.isSpectator()){
            livingEntity.addStatusEffect(new StatusEffectInstance(SpellEngineEffects.STUN.entry,
                    tweaksConfig.value.stonehand_stun_duration_seconds * 20,
                    0,false,false,true));
        }
        return new SpellHandlers.ImpactResult(true, false);
    }
}
