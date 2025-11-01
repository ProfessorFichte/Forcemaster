package net.forcemaster_rpg.item.weapons;

import com.google.common.collect.Multimap;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.annotation.Nullable;
import more_rpg_loot.api.WeaponPassives;
import more_rpg_loot.api.WeaponTooltips;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import net.spell_engine.api.item.ConfigurableAttributes;
import net.spell_engine.api.item.weapon.SpellWeaponItem;

import java.util.List;

public class DragonKnuckle extends SpellWeaponItem implements ConfigurableAttributes {
    private Multimap<EntityAttribute, EntityAttributeModifier> attributes;
    public void setAttributes(Multimap<EntityAttribute, EntityAttributeModifier> attributes) {
        this.attributes = attributes;
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        if (attributes == null) {
            return super.getAttributeModifiers(slot);
        }
        return slot == EquipmentSlot.MAINHAND ? attributes : super.getAttributeModifiers(slot);
    }

    public DragonKnuckle(ToolMaterial material, Settings settings) {
        this(material, 1, 2.4F, settings);
    }

    public DragonKnuckle(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }
    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (FabricLoader.getInstance().isModLoaded("loot_n_explore")) {
            WeaponPassives.EnderDragonMelee(stack, target, attacker);
        }
        stack.damage(1, attacker, (e)->{
            e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND);
        });
        return true;
    }
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (FabricLoader.getInstance().isModLoaded("loot_n_explore")) {
            WeaponTooltips.EnderDragonMelee(stack, world, tooltip, context);
        }
        super.appendTooltip(stack, world, tooltip, context);
    }
}