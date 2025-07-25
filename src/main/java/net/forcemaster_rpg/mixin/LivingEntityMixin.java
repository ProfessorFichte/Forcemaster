package net.forcemaster_rpg.mixin;

import net.forcemaster_rpg.effect.Effects;
import net.forcemaster_rpg.item.tag.ModItemTags;
import net.forcemaster_rpg.util.tags.FMEntityTypeTags;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.more_rpg_classes.util.tags.MRPGCEntityTags;
import net.spell_engine.api.effect.SpellEngineEffects;
import net.spell_engine.internals.casting.SpellCasterEntity;
import org.spongepowered.asm.mixin.Unique;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.more_rpg_classes.effect.MRPGCEffects;
import org.spongepowered.asm.mixin.Mixin;
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

        if(stack.isIn(ModItemTags.FIST_WEAPON)
                && entity .hasStatusEffect(Effects.STONE_HAND.registryEntry)
                && target.isLiving()
                && !target.isSpectator()
                && lastAttack != entity.age
                && target instanceof LivingEntity
                && !type.isIn(MRPGCEntityTags.STUN_IMMUNE)){
            float stonhand_stun_chance = tweaksConfig.value.stonehand_stun_effect_on_attack;
            float randomrange_stun = new Random().nextFloat(1.0F);
            if (randomrange_stun < stonhand_stun_chance ){
                LivingEntity attackedEntity = (LivingEntity) target;
                attackedEntity.addStatusEffect(new StatusEffectInstance(SpellEngineEffects.STUN.entry,
                        tweaksConfig.value.stonehand_stun_duration_seconds * 20,
                        0,false,false,true));
                }

        }
        if(stack.isIn(ModItemTags.KNUCKLES)
                && target instanceof LivingEntity
                && !target.isSpectator()
                && lastAttack != entity.age
                && target.isLiving()
                && !type.isIn(FMEntityTypeTags.KNOCK_UP_IMMUNE)
                && entity instanceof SpellCasterEntity caster
                && !caster.isCastingSpell()

        ){
            float knuckle_chance_knockup = tweaksConfig.value.knuckle_knock_up_chance_on_attack;
            float randomrange_knockup = new Random().nextFloat(1.0F);
            if (randomrange_knockup < knuckle_chance_knockup ){
                Vec3d currentMovement = target.getVelocity();
                target.setVelocity(currentMovement.x, currentMovement.y + tweaksConfig.value.knuckle_knock_up_height ,currentMovement.z);
                target.velocityModified = true;
            }
        }
    }
}
