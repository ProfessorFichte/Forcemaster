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
import net.spell_engine.api.item.ItemConfig;
import net.spell_engine.api.item.weapon.Weapon;
import net.spell_power.api.SpellPowerMechanics;
import net.spell_power.api.SpellSchools;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

import static net.forcemaster_rpg.ForcemasterClassMod.MOD_ID;

public class WeaponsRegister {
    public static final Map<Identifier, Item> ITEMS = new LinkedHashMap<>();
    private static ItemConfig.Attribute armorAddition(float value) {
        return new ItemConfig.Attribute(
                "generic.armor",
                value,
                EntityAttributeModifier.Operation.ADD_VALUE);
    }

    public static final ArrayList<Weapon.Entry> entries = new ArrayList<>();
    private static Weapon.Entry entry(String name, Weapon.CustomMaterial material, Weapon.Factory factory, ItemConfig.Weapon defaults) {
        return entry(null, name, material, factory, defaults);
    }

    private static Weapon.Entry entry(String requiredMod, String name, Weapon.CustomMaterial material, Weapon.Factory factory, ItemConfig.Weapon defaults) {
        var entry = new Weapon.Entry(MOD_ID, name, material, factory, defaults, null);
        if (entry.isRequiredModInstalled()) {
            entries.add(entry);
        }
        return entry;
    }


    private static Supplier<Ingredient> ingredient(String idString, boolean requirement, Item fallback) {
        var id = Identifier.of(idString);
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

    public static float knuckle_attackSpeed = -2.2f;

    //KNUCKLES
    private static Weapon.Entry knuckle(String name, Weapon.CustomMaterial material, float damage) {
        return entry(name, material, KnuckleItem::new, new ItemConfig.Weapon(damage, knuckle_attackSpeed));
    }


    public static final Weapon.Entry wooden_knuckle = knuckle("wooden_knuckle",
            Weapon.CustomMaterial.matching(ToolMaterials.WOOD, () -> Ingredient.fromTag(ItemTags.PLANKS)), 1.5F)
            .attribute(ItemConfig.Attribute.bonus(SpellSchools.ARCANE.id, 0.5F))
            ;
    public static final Weapon.Entry stone_knuckle = knuckle("stone_knuckle",
            Weapon.CustomMaterial.matching(ToolMaterials.STONE, () -> Ingredient.fromTag(ItemTags.STONE_TOOL_MATERIALS)), 2.5F)
            .attribute(ItemConfig.Attribute.bonus(SpellSchools.ARCANE.id, 1.0F))
            .attribute(ItemConfig.Attribute.multiply(SpellPowerMechanics.HASTE.id, 0.01F))
            ;
    public static final Weapon.Entry iron_knuckle = knuckle("iron_knuckle",
            Weapon.CustomMaterial.matching(ToolMaterials.IRON, () -> Ingredient.ofItems(Items.IRON_INGOT)), 3.5F)
            .attribute(ItemConfig.Attribute.bonus(SpellSchools.ARCANE.id, 2.0F))
            .attribute(ItemConfig.Attribute.multiply(SpellPowerMechanics.HASTE.id, 0.02F))
            ;
    public static final Weapon.Entry golden_knuckle = knuckle("golden_knuckle",
            Weapon.CustomMaterial.matching(ToolMaterials.GOLD, () -> Ingredient.ofItems(Items.GOLD_INGOT)), 1.5F)
            .attribute(ItemConfig.Attribute.bonus(SpellSchools.ARCANE.id, 2.0F))
            .attribute(ItemConfig.Attribute.multiply(SpellPowerMechanics.HASTE.id, 0.02F))
            ;
    public static final Weapon.Entry diamond_knuckle = knuckle("diamond_knuckle",
            Weapon.CustomMaterial.matching(ToolMaterials.DIAMOND, () -> Ingredient.ofItems(Items.DIAMOND)), 4.5F)
            .attribute(ItemConfig.Attribute.bonus(SpellSchools.ARCANE.id, 3.0F))
            .attribute(ItemConfig.Attribute.multiply(SpellPowerMechanics.HASTE.id, 0.03F))
            ;
    public static final Weapon.Entry netherite_knuckle = knuckle("netherite_knuckle",
            Weapon.CustomMaterial.matching(ToolMaterials.NETHERITE, () -> Ingredient.ofItems(Items.NETHERITE_INGOT)), 5.5F)
            .attribute(ItemConfig.Attribute.bonus(SpellSchools.ARCANE.id, 4.0F))
            .attribute(ItemConfig.Attribute.multiply(SpellPowerMechanics.HASTE.id, 0.05F))
            ;
    public static final Weapon.Entry legendary_golden_knuckle = knuckle("legendary_golden_knuckle",
            Weapon.CustomMaterial.matching(ToolMaterials.DIAMOND, () -> Ingredient.ofItems(Items.GOLD_INGOT)),4.5F)
            .attribute(ItemConfig.Attribute.bonus(SpellSchools.ARCANE.id, 4.0F))
            .attribute(ItemConfig.Attribute.multiply(SpellPowerMechanics.HASTE.id, 0.05F))
            .attribute(ItemConfig.Attribute.multiply(SpellPowerMechanics.CRITICAL_CHANCE.id, 0.05F))
            ;
    public static final Weapon.Entry guardian_knuckle = knuckle("guardian_knuckle",
            Weapon.CustomMaterial.matching(ToolMaterials.DIAMOND, () -> Ingredient.ofItems(Items.IRON_INGOT)),5.0F)
            .attribute(ItemConfig.Attribute.bonus(SpellSchools.ARCANE.id, 3.0F))
            .attribute(armorAddition(4.0F))
            .attribute(ItemConfig.Attribute.multiply(SpellPowerMechanics.HASTE.id, 0.025F))
            ;
    public static final Weapon.Entry bloody_knuckle = knuckle("bloody_knuckle",
            Weapon.CustomMaterial.matching(ToolMaterials.NETHERITE, () -> Ingredient.ofItems(Items.NETHERITE_INGOT)),6.5F)
            .attribute(ItemConfig.Attribute.bonus(SpellSchools.ARCANE.id, 5.0F))
            .attribute(ItemConfig.Attribute.multiply(SpellPowerMechanics.HASTE.id, 0.07F))
            .attribute(ItemConfig.Attribute.multiply(Objects.requireNonNull(Identifier.tryParse("more_rpg_classes:lifesteal_modifier")),0.10F))
            .attribute(ItemConfig.Attribute.multiply(Objects.requireNonNull(Identifier.tryParse("more_rpg_classes:arcane_fuse_modifier")),0.05F))
            ;


    private static final String BETTER_END = "betterend";
    private static final String BETTER_NETHER = "betternether";
    //Registration
    public static void register(Map<String,ItemConfig.Weapon> configs) {
        if (FabricLoader.getInstance().isModLoaded(BETTER_NETHER)) {
            var repair = ingredient("betternether:nether_ruby", FabricLoader.getInstance().isModLoaded(BETTER_NETHER), Items.NETHERITE_INGOT);
            knuckle("ruby_knuckle",
                    Weapon.CustomMaterial.matching(ToolMaterials.NETHERITE, repair),7.0F)
                    .attribute(ItemConfig.Attribute.bonus(SpellSchools.ARCANE.id, 5.0F))
                    .attribute(ItemConfig.Attribute.multiply(SpellPowerMechanics.HASTE.id, 0.07F));
        }
        if (FabricLoader.getInstance().isModLoaded(BETTER_END)) {
            var repair = ingredient("betterend:aeternium_ingot", FabricLoader.getInstance().isModLoaded(BETTER_END), Items.NETHERITE_INGOT);
            knuckle("aeternium_knuckle",
                    Weapon.CustomMaterial.matching(ToolMaterials.NETHERITE, repair),7.0F)
                    .attribute(ItemConfig.Attribute.bonus(SpellSchools.ARCANE.id, 5.0F))
                    .attribute(ItemConfig.Attribute.multiply(SpellPowerMechanics.HASTE.id, 0.07F));
        }

        Weapon.register(configs, entries, ForcemasterGroup.FORCEMASTER_KEY);
    }

}
