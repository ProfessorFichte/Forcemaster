package net.forcemaster_rpg.custom.custom_spells;

import net.forcemaster_rpg.effect.Effects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.more_rpg_classes.effect.MRPGCEffects;
import net.spell_engine.api.spell.CustomSpellHandler;
import net.spell_engine.api.spell.Spell;
import net.spell_engine.internals.SpellHelper;
import net.spell_engine.utils.SoundHelper;
import net.spell_engine.utils.TargetHelper;
import net.spell_engine.api.spell.SpellInfo;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import static net.forcemaster_rpg.ForcemasterClassMod.MOD_ID;
import static net.spell_engine.internals.SpellRegistry.getSpell;

public class CustomSpells {
    public static void register() {

        int speed_belial_smashing = 4;
        double knockup_burstcrack = 0.35;
        double knockup_burstcrack_stonehand = 0.65;
        int stun_duration_straight_punch = 60;

        /////FORCEMASTER_SPELLS
        /// STRAIGHT PUNCH
        CustomSpellHandler.register(Identifier.of(MOD_ID, "straight_punch"), (data) -> {
            CustomSpellHandler.Data data1 = (CustomSpellHandler.Data) data;
            var actualSchool = data1.caster().getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
            for (Entity entity : data1.targets()) {
                if (entity instanceof LivingEntity living) {
                    entity.damage(living.getDamageSources().playerAttack(data1.caster()),(float) actualSchool);
                    SoundHelper.playSound(data1.caster().getWorld(), living, getSpell(Identifier.of(MOD_ID, "straight_punch")).impact[0].sound);
                    if (data1.caster().hasStatusEffect(Effects.STONE_HAND.registryEntry)) {
                        living.addStatusEffect(new StatusEffectInstance(MRPGCEffects.STUNNED.registryEntry, stun_duration_straight_punch));
                    }
                    return true;
                }
            }
            return true;
        });
        /// BURSTCRACK
        CustomSpellHandler.register(Identifier.of(MOD_ID, "burstcrack"), (data) -> {
            SpellInfo spellinfo = new SpellInfo(getSpell(Identifier.of(MOD_ID, "burstcrack")),Identifier.of(MOD_ID));
            Spell.Impact[] impacts = getSpell(Identifier.of(MOD_ID, "burstcrack")).impact;


            CustomSpellHandler.Data data1 = (CustomSpellHandler.Data) data;
            Predicate<Entity> selectionPredicate = (target2) -> {
                return (TargetHelper.actionAllowed(TargetHelper.TargetingMode.AREA, TargetHelper.Intent.HARMFUL, data1.caster(), target2)
                );
            };
            if (!data1.caster().getWorld().isClient) {
                List<Entity> list = data1.caster().getWorld().getOtherEntities(data1.caster(), data1.caster().getBoundingBox().expand(getSpell(Identifier.of(MOD_ID, "burstcrack")).range), selectionPredicate);
                for (Entity entity : list) {
                    SpellHelper.performImpacts(entity.getWorld(), data1.caster(), entity, entity, spellinfo,impacts ,data1.impactContext());
                    SoundHelper.playSound(data1.caster().getWorld(), entity, getSpell(Identifier.of(MOD_ID, "burstcrack")).impact[0].sound);
                    Vec3d currentMovement = entity.getVelocity();
                    entity.setVelocity(currentMovement.x, currentMovement.y + knockup_burstcrack, currentMovement.z);
                    entity.velocityModified = true;
                    if (data1.caster().hasStatusEffect(Effects.STONE_HAND.registryEntry)) {
                        SpellHelper.performImpacts(entity.getWorld(), data1.caster(), entity, entity, spellinfo,impacts ,data1.impactContext());
                        SoundHelper.playSound(data1.caster().getWorld(), entity, getSpell(Identifier.of(MOD_ID, "burstcrack")).impact[0].sound);
                        Vec3d currentMovement2 = entity.getVelocity();
                        entity.setVelocity(currentMovement2.x, currentMovement2.y + knockup_burstcrack_stonehand, currentMovement2.z);
                        entity.velocityModified = true;
                        if(entity instanceof LivingEntity){
                            LivingEntity livingEntity = (LivingEntity) entity;
                            livingEntity.addStatusEffect(new StatusEffectInstance(MRPGCEffects.STUNNED.registryEntry, stun_duration_straight_punch));
                        }

                    }
                }
            }
            return true;
        });
        /// BELIAL SMASHING
        CustomSpellHandler.register(Identifier.of(MOD_ID, "belial_smashing"), (data) -> {
            SpellInfo spellinfo = new SpellInfo(getSpell(Identifier.of(MOD_ID, "belial_smashing")),Identifier.of(MOD_ID));
            Spell.Impact[] impacts = getSpell(Identifier.of(MOD_ID, "belial_smashing")).impact;
            CustomSpellHandler.Data data1 = (CustomSpellHandler.Data) data;
            List<Entity> list = TargetHelper.targetsFromRaycast(data1.caster(), getSpell(Identifier.of(MOD_ID, "belial_smashing")).range, Objects::nonNull);
            if (!data1.caster().getWorld().isClient) {
                data1.caster().velocityDirty = true;
                data1.caster().velocityModified = true;
                Vec3d rotationVector = data1.caster().getRotationVector();
                Vec3d velocity = data1.caster().getVelocity();
                data1.caster().addVelocity(rotationVector.x * 0.1 + (rotationVector.x * 2.5 - velocity.x) * speed_belial_smashing, 0, rotationVector.z * 0.1 + (rotationVector.z * 2.5 - velocity.z) * speed_belial_smashing);
                for (Entity entity : list) {
                    SpellHelper.performImpacts(entity.getWorld(), data1.caster(), entity, entity, spellinfo,impacts ,data1.impactContext());
                    Vec3d currentMovement2 = entity.getVelocity();
                    entity.setVelocity(currentMovement2.x, currentMovement2.y + 0.6f, currentMovement2.z);
                    if(entity instanceof LivingEntity){
                        LivingEntity livingEntity = (LivingEntity) entity;
                        livingEntity.addStatusEffect(new StatusEffectInstance(MRPGCEffects.STUNNED.registryEntry, stun_duration_straight_punch));
                    }
                }
            }
            return true;
        });

    }
}
