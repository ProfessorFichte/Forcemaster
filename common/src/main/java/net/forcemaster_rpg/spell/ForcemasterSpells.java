package net.forcemaster_rpg.spell;

import net.forcemaster_rpg.effect.ForcemasterEffects;
import net.forcemaster_rpg.sounds.ModSounds;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.more_rpg_classes.effect.MRPGCEffects;
import net.spell_engine.api.datagen.SpellBuilder;
import net.spell_engine.api.spell.ExternalSpellSchools;
import net.spell_engine.api.spell.Spell;
import net.spell_engine.api.spell.fx.ParticleBatch;
import net.spell_engine.api.spell.fx.PlayerAnimation;
import net.spell_engine.api.spell.fx.Sound;
import net.spell_engine.api.util.TriState;
import net.spell_engine.client.gui.SpellTooltip;
import net.spell_engine.client.util.Color;
import net.spell_engine.fx.SpellEngineParticles;
import net.spell_engine.fx.SpellEngineSounds;
import net.spell_engine.internals.target.SpellTarget;
import net.spell_power.api.SpellSchools;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;

import static net.forcemaster_rpg.ForcemasterClassMod.MOD_ID;

public class ForcemasterSpells {
    public enum Book { FORCEMASTER }
    public record Entry(Identifier id, Spell spell, String title, String description,
                        @Nullable SpellTooltip.DescriptionMutator mutator,
                        @Nullable Book book) {
        public Entry(Identifier id, Spell spell, String title, String description) {
            this(id, spell, title, description, null, null);
        }
        public Entry(Identifier id, Spell spell, String title, String description, @Nullable SpellTooltip.DescriptionMutator mutator) {
            this(id, spell, title, description, mutator, null);
        }
        public Entry mutator(SpellTooltip.DescriptionMutator mutator) {
            return new Entry(id, spell, title, description, mutator, book);
        }
        public Entry book(Book book) {
            return new Entry(id, spell, title, description, mutator, book);
        }
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
    public static Spell.Trigger arcaneSkillImpact() {
        Spell.Trigger trigger = new Spell.Trigger();
        trigger.type = net.spell_engine.api.spell.Spell.Trigger.Type.SPELL_IMPACT_SPECIFIC;
        trigger.spell = new Spell.Trigger.SpellCondition();
        trigger.spell.school = SpellSchools.ARCANE.id.toString();
        trigger.spell.type = Spell.Type.ACTIVE;
        return trigger;
    }

    public static List<Spell.Trigger> forcemasterSpellImpact() {
        return List.of(arcaneSkillImpact(), SpellBuilder.Triggers.meleeSkillImpact());
    }
    public static List<Spell.Trigger> forcemasterSpellsKills() {
        List<Spell.Trigger> triggers = forcemasterSpellImpact();

        Spell.Trigger trigger;
        Spell.TargetCondition deadCondition;
        for(Iterator var1 = triggers.iterator(); var1.hasNext(); trigger.target_conditions = List.of(deadCondition)) {
            trigger = (Spell.Trigger)var1.next();
            deadCondition = SpellBuilder.TargetConditions.dead();
        }

        return triggers;
    }

    /// WEAPON SKILL
    public static final Entry burstcrack = add(burstcrack());
    private static Entry burstcrack() {
        var id = Identifier.of(MOD_ID, "burstcrack");
        var title = "Burstcrack";
        var description = "Unleashes a shockwave around you, knocking up enemies and dealing physical {damage_1} & {damage_2} arcane-damage.";
        var spell = SpellBuilder.createWeaponSpell();
        spell.school = ExternalSpellSchools.PHYSICAL_MELEE;
        spell.range = 5.5F;

        spell.active.cast.movement_speed = 0.25F;
        spell.active.cast.duration = 0.5F;
        spell.active.cast.animation = PlayerAnimation.of("forcemaster_rpg:burstcrack_cast");

        spell.release.animation = PlayerAnimation.of("forcemaster_rpg:burstcrack_release");
        spell.release.sound = Sound.withVolume(Identifier.of("entity.generic.explode"), 0.4F);
        spell.release.particles = new ParticleBatch[]{
                new ParticleBatch(SpellEngineParticles.smoke_medium.id().toString(),
                        ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.CENTER,
                        50, 0.2F, 0.3F)
        };
        spell.release.particles_scaled_with_ranged = new ParticleBatch[]{
                new ParticleBatch(SpellEngineParticles.area_effect_658.id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.GROUND,
                        1, 0, 0)
                        .scale(0.5F)
                        .color(ORANGE.toRGBA())
        };

        spell.target.type = Spell.Target.Type.AREA;
        spell.target.area = new Spell.Target.Area();
        spell.target.area.angle_degrees = 360;

        var damage = SpellBuilder.Impacts.damage(0.5F, 0F);
        damage.particles = new ParticleBatch[]{
                new ParticleBatch(SpellEngineParticles.smoke_medium.id().toString(),
                        ParticleBatch.Shape.PIPE, ParticleBatch.Origin.CENTER,
                        20, 0.1F, 3.0F)
        };
        damage.sound = new Sound("forcemaster_rpg:knuckle_attack");

        var damageArcane = SpellBuilder.Impacts.damage(0.2F, 0F);
        damageArcane.school = SpellSchools.ARCANE;

        var custom = new Spell.Impact();
        bossDeny(custom);
        custom.action = new Spell.Impact.Action();
        custom.action.custom = new Spell.Impact.Action.Custom();
        custom.action.type = Spell.Impact.Action.Type.CUSTOM;
        custom.action.custom.intent = SpellTarget.Intent.HARMFUL;
        custom.action.custom.handler = "more_rpg_classes:knock_up";

        spell.impacts = List.of(damage, damageArcane, custom);
        SpellBuilder.Cost.cooldown(spell, 15);
        spell.cost.cooldown.attempt_duration = 1.5F;

        return new Entry(id, spell, title, description, null);
    }
    ///PASSIVES
    public static final Entry knuckle_arcane_overflow = add(knuckle_arcane_overflow());
    private static Entry knuckle_arcane_overflow() {
        var id = Identifier.of(MOD_ID, "knuckle_arcane_overflow");
        var title = "Arcane Overflow";
        var description = "Casting Forcemaster Spells stacks Arcane Overflow for {effect_duration} sec.";
        var effect = ForcemasterEffects.ARCANE_OVERFLOW;

        var spell = SpellBuilder.createSpellPassive();
        spell.tooltip = new Spell.Tooltip();
        spell.tooltip.show_header = false;
        spell.tooltip.name = new Spell.Tooltip.LineOptions(true, false);
        spell.tooltip.description.color = Formatting.DARK_GREEN.asString();
        spell.tooltip.description.show_in_compact = true;
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
    public static Entry nen_focus = add(nen_focus());
    private static Entry nen_focus() {
        var id = Identifier.of(MOD_ID, "nen_focus");
        var title = "Nen Focus";
        var impactEffect = ForcemasterEffects.ARCANE_OVERFLOW;
        var stashEffect = ForcemasterEffects.NEN_FOCUS;
        var description = "Defeating enemies with Forcemaster Spells grants " + stashEffect.title + " effect, stacking " + impactEffect.title + " with melee attacks up to {effect_amplifier_cap} times, lasting {effect_duration} seconds.";
        var spell = SpellBuilder.createSpellPassive();
        spell.school = SpellSchools.ARCANE;

        var triggers = SpellBuilder.Triggers.withConditionMustWield(
                forcemasterSpellsKills()
        );
        for (var trigger : triggers) {
            trigger.target_override = Spell.Trigger.TargetSelector.CASTER;
        }
        spell.passive.triggers = triggers;

        var triggersStash = SpellBuilder.Triggers.meleeImpact();
        for (var trigger : triggersStash) {
            trigger.target_override = Spell.Trigger.TargetSelector.CASTER;
        }

        spell.deliver.type = Spell.Delivery.Type.STASH_EFFECT;
        spell.deliver.stash_effect = new Spell.Delivery.StashEffect();
        spell.deliver.stash_effect.id = stashEffect.id.toString();
        spell.deliver.stash_effect.consume = 0;
        spell.deliver.stash_effect.triggers = triggersStash;

        spell.target.type = Spell.Target.Type.FROM_TRIGGER;

        var buff = SpellBuilder.Impacts.effectAdd(impactEffect.id.toString(), 8,1,6);
        buff.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.MagicParticles.get(
                        SpellEngineParticles.MagicParticles.Shape.ARCANE,
                        SpellEngineParticles.MagicParticles.Motion.DECELERATE).id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        10, 0.3F, 0.35F)
                        .color(Color.ARCANE.toRGBA())
        };
        buff.action.status_effect.apply_mode = Spell.Impact.Action.StatusEffect.ApplyMode.ADD;
        buff.action.status_effect.amplifier_cap_power_multiplier = 0.15F;
        buff.action.status_effect.refresh_duration = false;

        spell.impacts = List.of(buff);

        configureCooldown(spell, 20,0);
        spell.cost.batching = true;

        return new Entry(id, spell, title, description, null);
    }
    ///ACTIVES
    public static Entry stonehand = add(stonehand());
    private static Entry stonehand() {
        var id = Identifier.of(MOD_ID, "stonehand");
        var effect = ForcemasterEffects.STONE_HAND;
        var title = "Stonehand";
        var description = "{trigger_chance_1} chance for {stash_duration} seconds to stun targets on damage, only with fist weapons.";
        var spell = activeSpellBase();
        spell.school = SpellSchools.ARCANE;
        spell.tier = 2;
        spell.range = 2;

        spell.release.animation = PlayerAnimation.of("forcemaster_rpg:stonehand_cast");
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
        return new Entry(id, spell, title, description, null).book(Book.FORCEMASTER);
    }
    public static Entry belial_smashing = add(belial_smashing());
    private static Entry belial_smashing() {
        var id = Identifier.of(MOD_ID, "belial_smashing");
        var title = "Belial Smashing";
        var description = "Charges to the targets direction, punching all enemies in your path.";
        var spell = SpellBuilder.createSpellActive();
        spell.school = SpellSchools.ARCANE;
        spell.tier = 3;

        SpellBuilder.Casting.instant(spell);
        SpellBuilder.Target.none(spell);

        spell.release.particles = new ParticleBatch[]{
                new ParticleBatch(SpellEngineParticles.smoke_large.id().toString(),
                        ParticleBatch.Shape.WIDE_PIPE, ParticleBatch.Origin.FEET,
                        35, 0.1F, 0.5F).followEntity(true).color(Color.WHITE.toRGBA())
        };

        var attack = new Spell.Delivery.Melee.Attack();
        attack.attack_speed_multiplier = 1.2F;
        attack.delay = 0.1F;
        attack.hitbox = new Spell.Delivery.Melee.HitBox();
        attack.hitbox.arc = 160;
        attack.hitbox.height = 0.5F;
        attack.hitbox.width = 0.5F;
        attack.forward_momentum = 3.0F;
        attack.movement_slipperiness = 0.2F;

        attack.additional_strikes = 8;
        attack.additional_strike_delay = 0.15F;
        attack.additional_hits_on_same_target = false;
        attack.animation = PlayerAnimation.of("forcemaster_rpg:fist_rush");
        attack.animation.speed = 1F;
        attack.swing_sound = Sound.of(SpellEngineSounds.WEAPON_HAMMER_SWING.id());
        attack.impact_sound = Sound.of(ModSounds.KNUCKLE_ATTACK.id());

        var damageArcane = SpellBuilder.Impacts.damage(0.3F,0F);
        damageArcane.school  = SpellSchools.ARCANE;

        spell.impacts = List.of(damageArcane);
        SpellBuilder.Deliver.melee(spell, List.of(attack));
        spell.deliver.melee.allow_airborne = false;

        SpellBuilder.Cost.cooldown(spell, 23);
        return new Entry(id, spell, title, description, null).book(Book.FORCEMASTER);
    }
    public static final Entry asal = add(asal());
    private static Entry asal() {
        var id = Identifier.of(MOD_ID, "asal");
        var title = "Asalraalaikum";
        var description = "A devastating charged punch that deals massive {damage} damage. Consumes Arcane Overflow and exhausts the caster.";
        var spell = activeSpellBase();
        spell.school = SpellSchools.ARCANE;
        spell.tier = 4;
        spell.range = 7.5F;

        spell.active.cast.movement_speed = 0.1F;
        spell.active.cast.duration = 0.75F;
        spell.active.cast.animation = PlayerAnimation.of("forcemaster_rpg:asal_cast");
        spell.active.cast.sound = new Sound("spell_engine:generic_arcane_casting");
        spell.active.cast.particles = new ParticleBatch[]{
                new ParticleBatch(SpellEngineParticles.MagicParticles.get(
                        SpellEngineParticles.MagicParticles.Shape.ARCANE,
                        SpellEngineParticles.MagicParticles.Motion.ASCEND).id().toString(),
                        ParticleBatch.Shape.WIDE_PIPE, ParticleBatch.Origin.FEET,
                        10, 0.2F, 0.6F).extent(0.5F).color(Color.ARCANE.toRGBA())
        };

        spell.release.sound = Sound.withVolume(Identifier.of("forcemaster_rpg:asal_release"), 0.35F);
        spell.release.particles = new ParticleBatch[]{
                new ParticleBatch(SpellEngineParticles.smoke_medium.id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        ParticleBatch.Rotation.LOOK,
                        15, 1.0F, 10.0F, 360, 2).color(Color.ARCANE.toRGBA())
        };

        SpellBuilder.Target.none(spell);

        var punch = new Spell.Delivery.Melee.Attack();
        punch.attack_speed_multiplier = 2.0F;
        punch.delay = 0.25F;
        punch.hitbox = new Spell.Delivery.Melee.HitBox();
        punch.hitbox.height = 2.0F;
        punch.hitbox.length = 6.5F;
        punch.hitbox.width = 1.5F;
        punch.animation = PlayerAnimation.of("forcemaster_rpg:asal_release");

        SpellBuilder.Deliver.melee(spell, List.of(punch));

        var damage = SpellBuilder.Impacts.damage(3.0F, 2.0F);
        damage.particles = new ParticleBatch[]{
                new ParticleBatch(SpellEngineParticles.smoke_medium.id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        50, 0.1F, 5.0F).color(ORANGE.toRGBA())
        };
        damage.sound = Sound.withVolume(Identifier.of("entity.dragon_fireball.explode"), 0.5F);

        spell.impacts = List.of(damage);

        spell.cost.effect_id = ForcemasterEffects.ARCANE_OVERFLOW.id.toString();
        spell.cost.exhaust = 40F;
        spell.cost.durability = 1;
        spell.cost.cooldown = new Spell.Cost.Cooldown();
        spell.cost.cooldown.duration = 30;
        spell.cost.cooldown.haste_affected = true;

        return new Entry(id, spell, title, description, null).book(Book.FORCEMASTER);
    }
    /// MODIFIER
    public static final Entry improved_belial_smashing = add(improved_belial_smashing());
    private static Entry improved_belial_smashing() {
        var id = Identifier.of(MOD_ID, "improved_belial_smashing");
        var title = "Improved Belial Smashing";
        var description = "Reduces the cooldown of Belial Smashing by {cooldown_duration_deduct} sec.";
        var spell = new Spell();
        spell.school = SpellSchools.ARCANE;
        spell.range = 0;
        spell.tier = 1;

        spell.type = Spell.Type.MODIFIER;

        spell.tooltip = new Spell.Tooltip();
        spell.tooltip.show_header = false;
        spell.tooltip.name = new Spell.Tooltip.LineOptions(false, false);
        spell.tooltip.description.color = "gray";
        spell.tooltip.description.show_in_compact = true;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "forcemaster_rpg:belial_smashing";
        modifier.cooldown_duration_deduct = 3;
        spell.modifiers = List.of(modifier);

        return new Entry(id, spell, title, description, null);
    }
}
