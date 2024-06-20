package net.forcemaster_rpg.item.weapons;

import net.forcemaster_rpg.ForcemasterClassMod;
import net.forcemaster_rpg.item.ForcemasterGroup;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterials;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;
import net.spell_engine.api.item.ItemConfig;
import net.spell_engine.api.item.weapon.Weapon;
import net.spell_power.api.SpellPowerMechanics;
import net.spell_power.api.SpellSchools;

import java.util.ArrayList;
import java.util.Map;
import java.util.function.Supplier;

public class WeaponsRegister {
    public static final ArrayList<Weapon.Entry> entries = new ArrayList<>();

    private static ItemConfig.Attribute armorAddition(float value) {
        return new ItemConfig.Attribute(
                "generic.armor",
                value,
                EntityAttributeModifier.Operation.ADDITION);
    }

    private static Weapon.Entry entry(String name, Weapon.CustomMaterial material, Item item, ItemConfig.Weapon defaults) {
        return entry(null, name, material, item, defaults);
    }

    private static Weapon.Entry entry(String requiredMod, String name, Weapon.CustomMaterial material, Item item, ItemConfig.Weapon defaults) {
        var entry = new Weapon.Entry(ForcemasterClassMod.MOD_ID, name, material, item, defaults, null);
        if (entry.isRequiredModInstalled()) {
            entries.add(entry);
        }
        return entry;
    }

    private static Supplier<Ingredient> ingredient(String idString) {
        return ingredient(idString, Items.DIAMOND);
    }

    private static Supplier<Ingredient> ingredient(String idString, Item fallback) {
        var id = new Identifier(idString);
        return () -> {
            var item = Registries.ITEM.get(id);
            var ingredient = item != null ? item : fallback;
            return Ingredient.ofItems(ingredient);
        };
    }

    public static float knuckle_attackSpeed = -2.2f;

    //KNUCKLES
    private static Weapon.Entry knuckles(String name, Weapon.CustomMaterial material, float damage) {
        return knuckles(null, name, material, damage);}
    private static Weapon.Entry knuckles(String requiredMod, String name, Weapon.CustomMaterial material, float damage){
        var settings = new Item.Settings();
        var item = new KnuckleItem(material,settings);
        return entry(requiredMod, name, material, item, new ItemConfig.Weapon(damage, knuckle_attackSpeed));
    }

    private static Weapon.Entry special_knuckle_1(String name, Weapon.CustomMaterial material, float damage) {
        return special_knuckle_1(null, name, material, damage);}
    private static Weapon.Entry special_knuckle_1(String requiredMod, String name, Weapon.CustomMaterial material, float damage){
        var settings = new Item.Settings();
        var item = new BloodyKnuckleItem(material,settings);
        return entry(requiredMod, name, material, item, new ItemConfig.Weapon(damage, knuckle_attackSpeed));
    }

    public static final Weapon.Entry wooden_knuckle = knuckles("wooden_knuckle",
            Weapon.CustomMaterial.matching(ToolMaterials.WOOD, () -> Ingredient.fromTag(ItemTags.PLANKS)), 1.5F)
            .attribute(ItemConfig.Attribute.bonus(SpellSchools.ARCANE.id, 0.5F))
            ;
    public static final Weapon.Entry stone_knuckle = knuckles("stone_knuckle",
            Weapon.CustomMaterial.matching(ToolMaterials.STONE, () -> Ingredient.fromTag(ItemTags.STONE_TOOL_MATERIALS)), 2.5F)
            .attribute(ItemConfig.Attribute.bonus(SpellSchools.ARCANE.id, 1.0F))
            .attribute(ItemConfig.Attribute.multiply(SpellPowerMechanics.HASTE.id, 0.01F))
            ;
    public static final Weapon.Entry iron_knuckle = knuckles("iron_knuckle",
            Weapon.CustomMaterial.matching(ToolMaterials.IRON, () -> Ingredient.ofItems(Items.IRON_INGOT)), 3.5F)
            .attribute(ItemConfig.Attribute.bonus(SpellSchools.ARCANE.id, 2.0F))
            .attribute(ItemConfig.Attribute.multiply(SpellPowerMechanics.HASTE.id, 0.02F))
            ;
    public static final Weapon.Entry golden_knuckle = knuckles("golden_knuckle",
            Weapon.CustomMaterial.matching(ToolMaterials.GOLD, () -> Ingredient.ofItems(Items.GOLD_INGOT)), 1.5F)
            .attribute(ItemConfig.Attribute.bonus(SpellSchools.ARCANE.id, 2.0F))
            .attribute(ItemConfig.Attribute.multiply(SpellPowerMechanics.HASTE.id, 0.02F))
            ;
    public static final Weapon.Entry diamond_knuckle = knuckles("diamond_knuckle",
            Weapon.CustomMaterial.matching(ToolMaterials.DIAMOND, () -> Ingredient.ofItems(Items.DIAMOND)), 4.5F)
            .attribute(ItemConfig.Attribute.bonus(SpellSchools.ARCANE.id, 3.0F))
            .attribute(ItemConfig.Attribute.multiply(SpellPowerMechanics.HASTE.id, 0.03F))
            ;
    public static final Weapon.Entry netherite_knuckle = knuckles("netherite_knuckle",
            Weapon.CustomMaterial.matching(ToolMaterials.NETHERITE, () -> Ingredient.ofItems(Items.NETHERITE_INGOT)), 5.5F)
            .attribute(ItemConfig.Attribute.bonus(SpellSchools.ARCANE.id, 4.0F))
            .attribute(ItemConfig.Attribute.multiply(SpellPowerMechanics.HASTE.id, 0.05F))
            ;
    public static final Weapon.Entry legendary_golden_knuckle = knuckles("legendary_golden_knuckle",
            Weapon.CustomMaterial.matching(ToolMaterials.DIAMOND, () -> Ingredient.ofItems(Items.GOLD_INGOT)),4.5F)
            .attribute(ItemConfig.Attribute.bonus(SpellSchools.ARCANE.id, 4.0F))
            .attribute(ItemConfig.Attribute.multiply(SpellPowerMechanics.HASTE.id, 0.05F))
            .attribute(ItemConfig.Attribute.multiply(SpellPowerMechanics.CRITICAL_CHANCE.id, 0.05F))
            ;
    public static final Weapon.Entry guardian_knuckle = knuckles("guardian_knuckle",
            Weapon.CustomMaterial.matching(ToolMaterials.DIAMOND, () -> Ingredient.ofItems(Items.IRON_INGOT)),5.0F)
            .attribute(ItemConfig.Attribute.bonus(SpellSchools.ARCANE.id, 3.0F))
            .attribute(armorAddition(4.0F))
            .attribute(ItemConfig.Attribute.multiply(SpellPowerMechanics.HASTE.id, 0.025F))
            ;
    public static final Weapon.Entry bloody_knuckle = special_knuckle_1("bloody_knuckle",
            Weapon.CustomMaterial.matching(ToolMaterials.NETHERITE, () -> Ingredient.ofItems(Items.NETHERITE_INGOT)),6.5F)
            .attribute(ItemConfig.Attribute.bonus(SpellSchools.ARCANE.id, 5.0F))
            .attribute(ItemConfig.Attribute.multiply(SpellPowerMechanics.HASTE.id, 0.07F))
            ;

    //Registration
    public static void register(Map<String,ItemConfig.Weapon> configs) {
        Weapon.register(configs, entries, ForcemasterGroup.FORCEMASTER_KEY);
    }
}
