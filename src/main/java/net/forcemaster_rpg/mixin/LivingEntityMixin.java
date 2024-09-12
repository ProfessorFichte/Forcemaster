package net.forcemaster_rpg.mixin;

import net.forcemaster_rpg.effect.Effects;
import net.forcemaster_rpg.item.tag.KnuckleTag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import org.spongepowered.asm.mixin.Unique;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.more_rpg_classes.effect.MRPGCEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Unique
    private int lastAttack = 0;

    @Inject(method = "onAttacking", at = @At("HEAD"))
    private void onAttacking_knuckle(Entity target, CallbackInfo ci) {
        var entity = (LivingEntity) (Object) this;
        LivingEntity attackedEntity = (LivingEntity) target;
        ItemStack stack = entity .getEquippedStack(EquipmentSlot.MAINHAND);

        int knuckle_chance_knockup = 15;
        int randomrange_knockup = (int) ((Math.random() * (1 + knuckle_chance_knockup)) + 1);
        int stonhand_stun_chance = 8;
        int randomrange_stun = (int) ((Math.random() * (1 + stonhand_stun_chance)) + 1);
        int stun_duration = 30;

        if(stack.isIn(KnuckleTag.FIST_WEAPON) && target.isAlive()
        &&  !target.isSpectator() && lastAttack != entity.age
        ){
            if(entity .hasStatusEffect(Effects.STONE_HAND.registryEntry)){
                if (randomrange_stun >= stonhand_stun_chance ){
                    attackedEntity.addStatusEffect(new StatusEffectInstance(MRPGCEffects.STUNNED.registryEntry, stun_duration));
                }
            }
        }
        if(stack.isIn(KnuckleTag.KNUCKLES) && target.isAlive()
                &&  !target.isSpectator() && lastAttack != entity.age
        ){
            if (randomrange_knockup >= knuckle_chance_knockup ){
                Vec3d currentMovement = attackedEntity.getVelocity();
                attackedEntity.setVelocity(currentMovement.x, currentMovement.y + 0.5, currentMovement.z);
                attackedEntity.velocityModified = true;

            }
        }
    }
}
