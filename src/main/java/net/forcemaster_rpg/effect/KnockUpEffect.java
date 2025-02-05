package net.forcemaster_rpg.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.math.Vec3d;

public class KnockUpEffect extends StatusEffect {
    protected KnockUpEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    public void onApplied(LivingEntity entity, int amplifier) {
        Vec3d currentMovement = entity.getVelocity();
        entity.setVelocity(currentMovement.x, currentMovement.y + 0.5F, currentMovement.z);
        entity.velocityModified = true;
        super.onApplied(entity, amplifier);
    }
}
