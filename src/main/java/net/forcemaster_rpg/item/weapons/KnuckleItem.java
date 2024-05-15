package net.forcemaster_rpg.item.weapons;

import net.forcemaster_rpg.effect.Effects;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.more_rpg_classes.effect.MRPGCEffects;
import net.spell_engine.api.item.weapon.SpellWeaponItem;

public class KnuckleItem extends SpellWeaponItem {
    public KnuckleItem(ToolMaterial material, Settings settings) {
        super(material, settings);
    }

    @Override
    public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
        if (state.isOf(Blocks.DIRT)) {
            return 10.0F;
        } else if (state.isIn(BlockTags.PICKAXE_MINEABLE)){
            return 10.0F;
        }else{
            return state.isIn(BlockTags.SHOVEL_MINEABLE) ? 10.0F : 1.0F;
        }
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        if (!world.isClient && state.getHardness(world, pos) != 0.0f) {
            stack.damage(1, miner, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
        }
        return true;
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        int knuckle_chance_knockup = 15;
        int randomrange_knockup = (int) ((Math.random() * (1 + knuckle_chance_knockup)) + 1);
        int stonhand_stun_chance = 8;
        int randomrange_stun = (int) ((Math.random() * (1 + stonhand_stun_chance)) + 1);
        int stun_duration = 100;
        PlayerEntity player = (PlayerEntity) attacker;

        if (randomrange_knockup >= knuckle_chance_knockup ){
        if (target.isPlayer()) {
            Vec3d currentMovement = target.getVelocity();
            target.setVelocity(currentMovement.x, currentMovement.y + 0.95, currentMovement.z);
            target.velocityModified = true;
        } else {
            target.addVelocity(0, 0.4, 0);
        }
    stack.damage(1, attacker, (e)->{
        e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND);
    });
    }
        if(player.hasStatusEffect(Effects.STONE_HAND)){
            if (randomrange_stun >= stonhand_stun_chance ){
                target.addStatusEffect(new StatusEffectInstance(MRPGCEffects.STUNNED, stun_duration));
            }
        }
        stack.damage(1, attacker, (e) -> {
            e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND);
        });
        return true;
    }
}
