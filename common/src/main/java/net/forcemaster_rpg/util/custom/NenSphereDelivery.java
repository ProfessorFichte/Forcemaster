package net.forcemaster_rpg.util.custom;

import net.forcemaster_rpg.entity.NenSphereBeamEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.spell_engine.api.spell.Spell;
import net.spell_engine.api.spell.event.SpellHandlers;
import net.spell_engine.internals.SpellExecution;
import net.spell_engine.internals.SpellParameters;
import net.spell_engine.internals.delivery.LaunchGeometry;
import net.spell_engine.internals.impact.SpellImpacts;
import net.spell_engine.internals.target.EntityRelations;
import net.spell_engine.internals.target.SpellTarget;
import net.spell_engine.utils.TargetHelper;
import net.spell_power.api.SpellPower;

import java.util.function.Predicate;

import static net.forcemaster_rpg.ForcemasterClassMod.MOD_ID;

public class NenSphereDelivery {

    // The engagement cone widens a bit with charge - a bigger sphere sweeps a wider arc when unleashed.
    private static final float MIN_CONE_ANGLE_DEGREES = 6F;
    private static final float MAX_CONE_ANGLE_DEGREES = 18F;
    private static final float MIN_BEAM_WIDTH = 0.01F;
    private static final float MAX_BEAM_WIDTH = 3.0F;

    public static void register() {
        SpellHandlers.registerCustomDelivery(
                Identifier.of(MOD_ID, "nen_sphere"),
                (world, spellEntry, caster, targets, context, targetLocation) -> {
                    if (world.isClient) return false;

                    var spell = spellEntry.value();
                    var impactContext = context;
                    if (impactContext.power() == null) {
                        impactContext = impactContext.power(SpellPower.getSpellPower(spell.school, caster));
                    }

                    var chargeRatio = MathHelper.clamp(impactContext.charge(), 0F, 1F);
                    var effectiveRange = SpellParameters.getRange(caster, spellEntry, chargeRatio);

                    var origin = LaunchGeometry.launchPoint(caster);
                    var lookVector = caster.getRotationVector().normalize();
                    var beamPosition = TargetHelper.castBeam(caster, lookVector, effectiveRange);
                    var beamLength = beamPosition.length();

                    var coneAngle = MathHelper.lerp(chargeRatio, MIN_CONE_ANGLE_DEGREES, MAX_CONE_ANGLE_DEGREES);
                    var area = new Spell.Target.Area();
                    area.angle_degrees = coneAngle;

                    Predicate<Entity> predicate = target ->
                            EntityRelations.actionAllowed(SpellTarget.FocusMode.AREA, SpellTarget.Intent.HARMFUL, caster, target);
                    var hitTargets = TargetHelper.targetsFromArea(world, caster, origin, lookVector, beamLength, area, predicate);

                    var casterPos = caster.getPos().add(0, caster.getHeight() / 2F, 0);
                    for (var target : hitTargets) {
                        var position = target.getPos().add(0, target.getHeight() / 2F, 0).lerp(casterPos, 0.01);
                        var targetContext = impactContext.position(position);
                        SpellImpacts.performImpacts(world, caster, target, target, spellEntry, spell.impacts, targetContext);
                    }

                    var beamWidth = MathHelper.lerp(chargeRatio, MIN_BEAM_WIDTH, MAX_BEAM_WIDTH);
                    var beam = new NenSphereBeamEntity(world, origin, lookVector, beamLength, beamWidth);
                    world.spawnEntity(beam);

                    return true;
                }
        );
    }
}
