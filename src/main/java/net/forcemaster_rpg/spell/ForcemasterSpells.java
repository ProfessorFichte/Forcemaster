package net.forcemaster_rpg.spell;

import net.forcemaster_rpg.effect.Effects;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.more_rpg_classes.effect.MRPGCEffects;
import net.spell_engine.api.datagen.SpellBuilder;
import net.spell_engine.api.spell.ExternalSpellSchools;
import net.spell_engine.api.spell.Spell;
import net.spell_engine.api.spell.fx.ParticleBatch;
import net.spell_engine.api.spell.fx.Sound;
import net.spell_engine.api.util.TriState;
import net.spell_engine.client.gui.SpellTooltip;
import net.spell_engine.client.util.Color;
import net.spell_engine.fx.SpellEngineParticles;
import net.spell_engine.internals.target.SpellTarget;
import net.spell_power.api.SpellSchools;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import static net.forcemaster_rpg.ForcemasterClassMod.MOD_ID;

public class ForcemasterSpells {
    public record Entry(Identifier id, Spell spell, String title, String description,
                        @Nullable SpellTooltip.DescriptionMutator mutator) {
    }

    public static final List<Entry> entries = new ArrayList<>();

    private static Entry add(Entry entry) {
        entries.add(entry);
        return entry;
    }
    private static Spell activeSpellBase() {
        var spell = new Spell();
        spell.type = Spell.Type.ACTIVE;
        spell.active = new Spell.Active();
        spell.active.cast = new Spell.Active.Cast();

        spell.learn = new Spell.Learn();

        return spell;
    }

    private static Spell passiveSpellBase() {
        var spell = new Spell();
        spell.range = 0;
        spell.tier = 7;

        spell.type = Spell.Type.PASSIVE;
        spell.passive = new Spell.Passive();

        spell.tooltip = new Spell.Tooltip();
        spell.tooltip.show_header = false;
        spell.tooltip.name = new Spell.Tooltip.LineOptions(false, false);
        spell.tooltip.description.color = Formatting.DARK_GREEN.asString();
        spell.tooltip.description.show_in_compact = true;

        return spell;
    }

    private static Spell.Impact createEffectImpact(Identifier effectId, float duration) {
        var buff = new Spell.Impact();
        buff.action = new Spell.Impact.Action();
        buff.action.type = Spell.Impact.Action.Type.STATUS_EFFECT;
        buff.action.status_effect = new Spell.Impact.Action.StatusEffect();
        buff.action.status_effect.effect_id = effectId.toString();
        buff.action.status_effect.duration = duration;
        return buff;
    }

    private static Spell.Impact createHeal(float coefficient) {
        var buff = new Spell.Impact();
        buff.action = new Spell.Impact.Action();
        buff.action.type = Spell.Impact.Action.Type.HEAL;
        buff.action.heal = new Spell.Impact.Action.Heal();
        buff.action.heal.spell_power_coefficient = coefficient;
        return buff;
    }

    private static Spell.Impact createDamage(float coefficient, float knockback) {
        var buff = new Spell.Impact();
        buff.action = new Spell.Impact.Action();
        buff.action.type = Spell.Impact.Action.Type.DAMAGE;
        buff.action.damage = new Spell.Impact.Action.Damage();
        buff.action.damage.spell_power_coefficient = coefficient;
        buff.action.damage.knockback = knockback;
        return buff;
    }

    private static Spell.Impact.TargetModifier createImpactModifier(String entityType) {
        var condition = new Spell.TargetCondition();
        condition.entity_type = entityType;
        var modifier = new Spell.Impact.TargetModifier();
        modifier.conditions = List.of(condition);
        return modifier;
    }

    private static void configureCooldown(Spell spell, float duration, float exhaust) {
        if (spell.cost == null) {
            spell.cost = new Spell.Cost();
        }
        spell.cost.exhaust = exhaust;
        spell.cost.cooldown = new Spell.Cost.Cooldown();
        spell.cost.cooldown.duration = duration;
    }

    private static void stunImmuneDeny(Spell.Impact impact) {
        var modifier = createImpactModifier("#more_rpg_classes:stun_immune");
        modifier.execute = TriState.DENY;
        impact.target_modifiers = List.of(modifier);
    }
    public static final Color ORANGE = new Color(255.0F, 165.0F, 0.0F);
    private static void bossDeny(Spell.Impact impact) {
        var modifier = createImpactModifier("#c:bosses");
        modifier.execute = TriState.DENY;
        impact.target_modifiers = List.of(modifier);
    }


    ///PASSIVES
    public static final Entry knuckle_knockup = add(knuckle_knockup());
    private static Entry knuckle_knockup() {
        var id = Identifier.of(MOD_ID, "knuckle_knockup");
        var title = "Knuckle Knock Up";
        var description = "Hitting enemies has a {trigger_chance} chance to knock up the target.";
        var spell = SpellBuilder.createSpellPassive();
        spell.tooltip = new Spell.Tooltip();
        spell.tooltip.show_header = true;
        spell.tooltip.name = new Spell.Tooltip.LineOptions(true, false);
        spell.tooltip.description.color = Formatting.DARK_GREEN.asString();
        spell.tooltip.description.show_in_compact = false;
        spell.school = ExternalSpellSchools.PHYSICAL_MELEE;
        spell.range = 0;

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;

        var trigger = SpellBuilder.Triggers.meleeAttack(false);
        trigger.chance = 0.15F;
        spell.passive.triggers = List.of(trigger);

        var custom = new Spell.Impact();

        custom.action = new Spell.Impact.Action();
        custom.action.custom = new Spell.Impact.Action.Custom();
        bossDeny(custom);
        custom.action.type = Spell.Impact.Action.Type.CUSTOM;
        custom.action.custom.intent = SpellTarget.Intent.HARMFUL;
        custom.action.custom.handler = "more_rpg_classes:knock_up_fixed";
        custom.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.smoke_medium.id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.FEET,
                        20, 0.1F, 0.3F)
                        .extent(0.25F)
                        .color(Color.WHITE.toRGBA()),
        };
        spell.impacts = List.of(custom);

        SpellBuilder.Cost.cooldown(spell, 2F);

        return new Entry(id, spell, title, description, null);
    }
    public static final Entry knuckle_arcane_overflow = add(knuckle_arcane_overflow());
    private static Entry knuckle_arcane_overflow() {
        var id = Identifier.of(MOD_ID, "knuckle_arcane_overflow");
        var title = "Arcane Overflow";
        var description = "Casting Forcemaster Spells stacks Arcane Overflow for {effect_duration} sec.";
        var effect = Effects.ARCANE_OVERFLOW;

        var spell = SpellBuilder.createSpellPassive();
        spell.tooltip = new Spell.Tooltip();
        spell.tooltip.show_header = true;
        spell.tooltip.name = new Spell.Tooltip.LineOptions(true, false);
        spell.tooltip.description.color = Formatting.DARK_GREEN.asString();
        spell.tooltip.description.show_in_compact = false;
        spell.school = SpellSchools.ARCANE;
        spell.range = 0;

        var trigger = SpellBuilder.Triggers.activeSpellCast(SpellSchools.ARCANE);
        trigger.target_override = Spell.Trigger.TargetSelector.CASTER;
        spell.passive.triggers = List.of(trigger);

        var impact = SpellBuilder.Impacts.effectAdd(effect.id.toString(), 15, 1, 9);
        impact.action.status_effect.amplifier_cap_power_multiplier = 0.15F;
        impact.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.MagicParticles.get(
                                SpellEngineParticles.MagicParticles.Shape.ARCANE,
                                SpellEngineParticles.MagicParticles.Motion.DECELERATE
                        ).id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        30, 0.5F, 0.5F)
                        .color(Color.from(SpellSchools.ARCANE.color).toRGBA()),
                new ParticleBatch(
                        SpellEngineParticles.aura_effect_642.id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        1, 0, 0)
                        .color(Color.from(SpellSchools.ARCANE.color).toRGBA()),
        };
        spell.impacts = List.of(impact);
        SpellBuilder.Cost.cooldown(spell,3);

        return new Entry(id, spell, title, description, null);
    }
    ///ACTIVES
    public static Entry stonehand = add(stonehand());
    private static Entry stonehand() {
        var id = Identifier.of(MOD_ID, "stonehand");
        var effect = Effects.STONE_HAND;
        var title = "Stonehand";
        var description = "";
        var spell = activeSpellBase();
        spell.school = SpellSchools.ARCANE;
        spell.tier = 1;
        spell.range = 2;

        spell.release.animation = "forcemaster_rpg:stonehand_cast";
        spell.release.sound = Sound.withVolume(Identifier.of("forcemaster_rpg:stonehand_cast"), 0.35F);
        spell.release.particles = new ParticleBatch[]{
                new ParticleBatch("more_rpg_classes:stone_particle",
                        ParticleBatch.Shape.WIDE_PIPE, ParticleBatch.Origin.FEET,
                        15, 0.1F, 0.25F)
                        .extent(1),
                new ParticleBatch(SpellEngineParticles.sign_fist.id().toString(),
                        ParticleBatch.Shape.LINE_VERTICAL, ParticleBatch.Origin.CENTER,
                        1, 0.75F, 0.75F)
                        .scale(1.2F)
                        .color(ORANGE.toRGBA())
                        .followEntity(true)
        };
        spell.release.particles_scaled_with_ranged = new ParticleBatch[]{
                new ParticleBatch(SpellEngineParticles.area_effect_658.id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.GROUND,
                        1, 0.0F, 0.F)
                        .color(ORANGE.toRGBA())
        };

        float stonehand_trigger_chance = 0.25F;


        spell.deliver.type = Spell.Delivery.Type.STASH_EFFECT;
        spell.deliver.stash_effect = new Spell.Delivery.StashEffect();
        spell.deliver.stash_effect.id = effect.id.toString();
        spell.deliver.stash_effect.consume = 0;
        var trigger_melee = new Spell.Trigger();
        trigger_melee.chance = stonehand_trigger_chance;
        trigger_melee.type = Spell.Trigger.Type.MELEE_IMPACT;
        var trigger_spell_impact = new Spell.Trigger();
        trigger_spell_impact.chance = stonehand_trigger_chance;
        trigger_spell_impact.type = Spell.Trigger.Type.SPELL_IMPACT_SPECIFIC;
        trigger_spell_impact.spell = new Spell.Trigger.SpellCondition();
        trigger_spell_impact.spell.type = Spell.Type.ACTIVE;
        trigger_spell_impact.impact = new Spell.Trigger.ImpactCondition();
        trigger_spell_impact.impact.impact_type = Spell.Impact.Action.Type.DAMAGE.toString();
        spell.deliver.stash_effect.triggers = List.of(trigger_melee,trigger_spell_impact);

        var custom = new Spell.Impact();
        stunImmuneDeny(custom);
        custom.action = new Spell.Impact.Action();
        custom.action.custom = new Spell.Impact.Action.Custom();
        custom.action.type = Spell.Impact.Action.Type.CUSTOM;
        custom.action.custom.intent = SpellTarget.Intent.HARMFUL;
        custom.action.custom.handler = "forcemaster_rpg:fist_weapon_stun";

        spell.impacts = List.of(custom);
        configureCooldown(spell, 20, 0.3F);
        return new Entry(id, spell, title, description, null);
    }
    public static Entry belial_smashing = add(belial_smashing());
    private static Entry belial_smashing() {
        var id = Identifier.of(MOD_ID, "belial_smashing");
        var effect = Effects.ARCANE_OVERFLOW;
        var title = "Stonehand";
        var description = "";
        var spell = activeSpellBase();
        spell.school = SpellSchools.ARCANE;
        spell.tier = 3;
        spell.range = 8.5F;

        spell.target.type = Spell.Target.Type.AIM;
        spell.target.aim = new Spell.Target.Aim();
        spell.target.aim.sticky = true;

        spell.release.animation = "forcemaster_rpg:fist_rush";
        spell.release.sound = Sound.withVolume(Identifier.of("entity.generic.explode"), 0.35F);
        spell.release.particles = new ParticleBatch[]{
                new ParticleBatch(SpellEngineParticles.smoke_large.id().toString(),
                        ParticleBatch.Shape.WIDE_PIPE, ParticleBatch.Origin.FEET,
                        35, 0.1F, 0.5F).followEntity(true).color(Color.WHITE.toRGBA())
        };

        var damage = SpellBuilder.Impacts.damage(0.75F,0.5F);
        damage.particles = new ParticleBatch[]{
                new ParticleBatch(SpellEngineParticles.smoke_large.id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        15, 0.1F, 0.3F).color(Color.WHITE.toRGBA())
        };
        damage.sound = new Sound("forcemaster_rpg:knuckle_attack");

        var damagePhysical = SpellBuilder.Impacts.damage(0.5F,0F);
        damagePhysical.school = ExternalSpellSchools.PHYSICAL_MELEE;

        var buff = SpellBuilder.Impacts.effectAdd(effect.id.toString(), 10,1,5);
        buff.action.status_effect.refresh_duration = true;

        var custom = new Spell.Impact();
        custom.action = new Spell.Impact.Action();
        custom.action.custom = new Spell.Impact.Action.Custom();
        custom.action.type = Spell.Impact.Action.Type.CUSTOM;
        custom.action.custom.intent = SpellTarget.Intent.HARMFUL;
        custom.action.custom.handler = "more_rpg_classes:rush_forward_to_target";

        spell.impacts = List.of(custom, damage,damagePhysical, buff);
        configureCooldown(spell, 22, 0.5F);
        return new Entry(id, spell, title, description, null);
    }
}
