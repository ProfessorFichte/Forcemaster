package net.forcemaster_rpg.mixin;

import net.forcemaster_rpg.effect.Effects;
import net.forcemaster_rpg.util.ForcemasterTags;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.more_rpg_classes.effect.MRPGCEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

import static net.forcemaster_rpg.ForcemasterClassMod.tweaksConfig;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Unique
    private int lastAttack = 0;

    @Inject(method = "onAttacking", at = @At("HEAD"))
    private void onAttacking_knuckle(Entity target, CallbackInfo ci) {
        var entity = (LivingEntity) (Object) this;
        ItemStack stack = entity.getEquippedStack(EquipmentSlot.MAINHAND);
        EntityType<?> type = target.getType();

        if (target != null) {
            if (stack.isIn(ForcemasterTags.KNUCKLES)
                    && target instanceof LivingEntity
                    && !target.isSpectator()
                    && lastAttack != entity.age
                    && target.isLiving()
                    && !type.isIn(ForcemasterTags.KNOCK_UP_IMMUNE)
            ) {
                float knuckle_chance_knockup = tweaksConfig.value.knuckle_chance_knockup;
                float randomrange_knockup = new Random().nextFloat(1.0F);
                if (randomrange_knockup < knuckle_chance_knockup) {
                    Vec3d currentMovement = target.getVelocity();
                    target.setVelocity(currentMovement.x, currentMovement.y + tweaksConfig.value.knuckle_knockup_height, currentMovement.z);
                    target.velocityModified = true;
                }
            }
            if(stack.isIn(ForcemasterTags.FIST_WEAPON)
                    && entity .hasStatusEffect(Effects.STONE_HAND)
                    && target.isLiving()
                    && !target.isSpectator()
                    && lastAttack != entity.age
                    && target instanceof LivingEntity
                    && !type.isIn(ForcemasterTags.STUN_IMMUNE)){
                float stonhand_stun_chance = tweaksConfig.value.stonehand_stun_chance_on_attack;
                float randomrange_stun = new Random().nextFloat(1.0F);
                if (randomrange_stun < stonhand_stun_chance ){
                    LivingEntity attackedEntity = (LivingEntity) target;
                    attackedEntity.addStatusEffect(new StatusEffectInstance(MRPGCEffects.STUNNED,
                            tweaksConfig.value.stonehand_stun_duration_seconds * 20,
                            0,false,false,true));
                }

            }
        }
    }
}
