package net.forcemaster_rpg.item.weapons;

import net.fabricmc.loader.api.FabricLoader;
import net.forcemaster_rpg.ForcemasterClassMod;
import net.forcemaster_rpg.item.ForcemasterGroup;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterials;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;
import net.more_rpg_classes.custom.MoreSpellSchools;
import net.spell_engine.api.item.ItemConfig;
import net.spell_engine.api.item.weapon.Weapon;
import net.spell_power.api.SpellPowerMechanics;
import net.spell_power.api.SpellSchools;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
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


    private static Supplier<Ingredient> ingredient(String idString, boolean requirement, Item fallback) {
        var id = new Identifier(idString);
        if (requirement) {
            return () -> {
                return Ingredient.ofItems(fallback);
            };
        } else {
            return () -> {
                var item = Registries.ITEM.get(id);
                var ingredient = item != null ? item : fallback;
                return Ingredient.ofItems(ingredient);
            };
        }
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
    private static Weapon.Entry knuckles(String name, Weapon.CustomMaterial material, float damage, boolean fireproof) {
        return knuckles(null, name, material, damage, fireproof);}
    private static Weapon.Entry knuckles(String requiredMod, String name, Weapon.CustomMaterial material, float damage, boolean fireproof){
        var settings = new Item.Settings();
        if (fireproof) {
            settings = settings.fireproof();
        }
        var item = new KnuckleItem(material,settings);
        return entry(requiredMod, name, material, item, new ItemConfig.Weapon(damage, knuckle_attackSpeed));
    }

    public static final Weapon.Entry wooden_knuckle = knuckles("wooden_knuckle",
            Weapon.CustomMaterial.matching(ToolMaterials.WOOD, () -> Ingredient.fromTag(ItemTags.LOGS)), 2.0F, false)
            .attribute(armorAddition(1.0F))
            ;
    public static final Weapon.Entry stone_knuckle = knuckles("stone_knuckle",
            Weapon.CustomMaterial.matching(ToolMaterials.STONE, () -> Ingredient.fromTag(ItemTags.STONE_TOOL_MATERIALS)), 3.0F, false)
            .attribute(armorAddition(1.0F))
            ;
    public static final Weapon.Entry iron_knuckle = knuckles("iron_knuckle",
            Weapon.CustomMaterial.matching(ToolMaterials.IRON, () -> Ingredient.ofItems(Items.IRON_INGOT)), 4.0F, false)
            .attribute(armorAddition(2.0F))
            .attribute(ItemConfig.Attribute.bonus(SpellSchools.ARCANE.id, 2.0F))
            ;
    public static final Weapon.Entry golden_knuckle = knuckles("golden_knuckle",
            Weapon.CustomMaterial.matching(ToolMaterials.GOLD, () -> Ingredient.ofItems(Items.GOLD_INGOT)), 2.0F, false)
            .attribute(armorAddition(1.0F))
            .attribute(ItemConfig.Attribute.bonus(SpellSchools.ARCANE.id, 2.0F))
            ;
    public static final Weapon.Entry diamond_knuckle = knuckles("diamond_knuckle",
            Weapon.CustomMaterial.matching(ToolMaterials.DIAMOND, () -> Ingredient.ofItems(Items.DIAMOND)), 5.0F, false)
            .attribute(armorAddition(2.0F))
            .attribute(ItemConfig.Attribute.bonus(SpellSchools.ARCANE.id, 3.0F))
            ;
    public static final Weapon.Entry netherite_knuckle = knuckles("netherite_knuckle",
            Weapon.CustomMaterial.matching(ToolMaterials.NETHERITE, () -> Ingredient.ofItems(Items.NETHERITE_INGOT)), 5.5F, true)
            .attribute(armorAddition(3.0F))
            .attribute(ItemConfig.Attribute.bonus(SpellSchools.ARCANE.id, 4.0F))
            ;

    ///LNE
    private static Weapon.Entry dragonKnuckle(String requiredMod, String name, Weapon.CustomMaterial material, float damage, boolean fireproof){
        var settings = new Item.Settings();
        if (fireproof) {
            settings = settings.fireproof();
        }
        var item = new DragonKnuckle(material,settings);
        return entry(requiredMod, name, material, item, new ItemConfig.Weapon(damage, knuckle_attackSpeed));
    }
    private static Weapon.Entry elderGuardianKnuckle(String requiredMod, String name, Weapon.CustomMaterial material, float damage, boolean fireproof){
        var settings = new Item.Settings();
        if (fireproof) {
            settings = settings.fireproof();
        }
        var item = new OceanKnuckle(material,settings);
        return entry(requiredMod, name, material, item, new ItemConfig.Weapon(damage, knuckle_attackSpeed));
    }
    private static Weapon.Entry glacialKnuckle(String requiredMod, String name, Weapon.CustomMaterial material, float damage, boolean fireproof){
        var settings = new Item.Settings();
        if (fireproof) {
            settings = settings.fireproof();
        }
        var item = new GlacialKnuckle(material,settings);
        return entry(requiredMod, name, material, item, new ItemConfig.Weapon(damage, knuckle_attackSpeed));
    }
    private static Weapon.Entry witherKnuckle(String requiredMod, String name, Weapon.CustomMaterial material, float damage, boolean fireproof){
        var settings = new Item.Settings();
        if (fireproof) {
            settings = settings.fireproof();
        }
        var item = new WitherKnuckle(material,settings);
        return entry(requiredMod, name, material, item, new ItemConfig.Weapon(damage, knuckle_attackSpeed));
    }
    private static final float lneWeaponSpellPower = 2.0F;
    static Supplier<Ingredient> dragonRepair = ingredient("loot_n_explore:ender_dragon_scales");
    static Supplier<Ingredient> elderGuardianRepair = ingredient("loot_n_explore:elder_guardian_eye");
    static Supplier<Ingredient> frostMonarchRepair = ingredient("loot_n_explore:frozen_soul");
    static Supplier<Ingredient> witherRepair = ingredient("minecraft:nether_star");

    private static final String BETTER_END = "betterend";
    private static final String BETTER_NETHER = "betternether";
    private static final String LNE = "loot_n_explore";

    //Registration
    public static void register(Map<String,ItemConfig.Weapon> configs) {
        if(FabricLoader.getInstance().isModLoaded(BETTER_NETHER)|| ForcemasterClassMod.tweaksConfig.value.ignore_items_required_mods){
            var repair = ingredient("betternether:nether_ruby");
            knuckles("betternether","ruby_knuckle",
                    Weapon.CustomMaterial.matching(ToolMaterials.NETHERITE,repair), 7.0F, true)
                    .attribute(ItemConfig.Attribute.bonus(SpellSchools.ARCANE.id, 4.5F))
                    .attribute(armorAddition(3.0F));
        }
        if(FabricLoader.getInstance().isModLoaded(BETTER_END)|| ForcemasterClassMod.tweaksConfig.value.ignore_items_required_mods){
            var repair = ingredient("betterend:aeternium_ingot");
            knuckles("betterend","aeternium_knuckle",
                    Weapon.CustomMaterial.matching(ToolMaterials.NETHERITE, repair), 7.0F, true)
                    .attribute(ItemConfig.Attribute.bonus(SpellSchools.ARCANE.id, 4.5F))
                    .attribute(armorAddition(3.0F));
        }
        if(FabricLoader.getInstance().isModLoaded(LNE)|| ForcemasterClassMod.tweaksConfig.value.ignore_items_required_mods) {
            dragonKnuckle("loot_n_explore","ender_dragon_knuckle",
                    Weapon.CustomMaterial.matching(ToolMaterials.NETHERITE, dragonRepair), 7.0F, true)
                    .attribute(ItemConfig.Attribute.bonus(SpellSchools.ARCANE.id, 4.5F))
                    .attribute(armorAddition(3.0F));
            elderGuardianKnuckle("loot_n_explore","elder_guardian_knuckle",
                    Weapon.CustomMaterial.matching(ToolMaterials.NETHERITE, elderGuardianRepair), 7.0F, true)
                    .attribute(ItemConfig.Attribute.bonus(MoreSpellSchools.WATER.id, lneWeaponSpellPower))
                    .attribute(ItemConfig.Attribute.bonus(SpellSchools.ARCANE.id, 4.5F))
                    .attribute(armorAddition(3.0F));
            glacialKnuckle("loot_n_explore","glacial_knuckle",
                    Weapon.CustomMaterial.matching(ToolMaterials.NETHERITE, frostMonarchRepair), 7.0F, true)
                    .attribute(ItemConfig.Attribute.bonus(SpellSchools.FROST.id, lneWeaponSpellPower))
                    .attribute(ItemConfig.Attribute.bonus(SpellSchools.ARCANE.id, 4.5F))
                    .attribute(armorAddition(3.0F));
            witherKnuckle("loot_n_explore","wither_knuckle",
                    Weapon.CustomMaterial.matching(ToolMaterials.NETHERITE, witherRepair), 7.0F, true)
                    .attribute(ItemConfig.Attribute.bonus(SpellSchools.SOUL.id, lneWeaponSpellPower))
                    .attribute(ItemConfig.Attribute.bonus(SpellSchools.ARCANE.id, 4.5F))
                    .attribute(armorAddition(3.0F));
        }
        ///ARCHIVED WEAPONS WITH THE POSIBILITY TO ENABLE THEM FOR MODPACK DEVS
        if(ForcemasterClassMod.tweaksConfig.value.ignore_items_required_mods) {
            knuckles("legendary_golden_knuckle",
                    Weapon.CustomMaterial.matching(ToolMaterials.DIAMOND, () -> Ingredient.ofItems(Items.GOLD_INGOT)),4.5F, true)
                    .attribute(ItemConfig.Attribute.bonus(SpellSchools.ARCANE.id, 4.5F))
                    .attribute(armorAddition(3.0F))
                    .attribute(ItemConfig.Attribute.multiply(SpellPowerMechanics.HASTE.id, 0.05F))
                    .attribute(ItemConfig.Attribute.multiply(SpellPowerMechanics.CRITICAL_CHANCE.id, 0.05F))
                    ;
            knuckles("guardian_knuckle",
                    Weapon.CustomMaterial.matching(ToolMaterials.DIAMOND, () -> Ingredient.ofItems(Items.IRON_INGOT)),5.0F, true)
                    .attribute(ItemConfig.Attribute.bonus(SpellSchools.ARCANE.id, 4.5F))
                    .attribute(armorAddition(4.0F))
                    .attribute(ItemConfig.Attribute.multiply(SpellPowerMechanics.HASTE.id, 0.025F))
                    ;
            knuckles("bloody_knuckle",
                    Weapon.CustomMaterial.matching(ToolMaterials.NETHERITE, () -> Ingredient.ofItems(Items.NETHERITE_INGOT)),6.5F, true)
                    .attribute(ItemConfig.Attribute.bonus(SpellSchools.ARCANE.id, 4.5F))
                    .attribute(armorAddition(3.0F))
                    .attribute(ItemConfig.Attribute.multiply(Objects.requireNonNull(Identifier.tryParse("more_rpg_classes:lifesteal_modifier")),0.10F))
                    ;
        }
        Weapon.register(configs, entries, ForcemasterGroup.FORCEMASTER_KEY);
    }
}
