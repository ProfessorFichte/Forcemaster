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
import net.minecraft.util.Rarity;
import net.more_rpg_classes.custom.MoreSpellSchools;
import net.spell_engine.api.config.AttributeModifier;
import net.spell_engine.api.config.WeaponConfig;
import net.spell_engine.api.item.Equipment;
import net.spell_engine.api.item.weapon.Weapon;
import net.spell_power.api.SpellSchools;

import java.util.ArrayList;
import java.util.Map;
import java.util.function.Supplier;

import static net.forcemaster_rpg.ForcemasterClassMod.MOD_ID;

public class WeaponsRegister {
    private static AttributeModifier armorAddition(float value) {
        return new AttributeModifier(
                "generic.armor",
                value,
                EntityAttributeModifier.Operation.ADD_VALUE);
    }

    public static final ArrayList<Weapon.Entry> entries = new ArrayList<>();

    private static Weapon.Entry entry(String name, Weapon.CustomMaterial material, Weapon.Factory factory, WeaponConfig defaults, Equipment.WeaponType category) {
        var entry = new Weapon.Entry(MOD_ID, name, material, factory, defaults, category);
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
    private static final float T1_KNUCKLE_POWER = 3F;
    private static final float T2_KNUCKLE_POWER = 4F;
    private static final float T3_KNUCKLE_POWER = 5F;
    private static final float T4_KNUCKLE_POWER = 5.5F;

    //KNUCKLES
    private static Weapon.Entry knuckle(String name, Weapon.CustomMaterial material, float damage) {
        return entry(name, material, KnuckleItem::new, new WeaponConfig(damage, knuckle_attackSpeed), Equipment.WeaponType.SWORD);
    }

    public static final Weapon.Entry wooden_knuckle = knuckle("wooden_knuckle",
            Weapon.CustomMaterial.matching(ToolMaterials.WOOD, () -> Ingredient.fromTag(ItemTags.LOGS)), 2.0F)
            .attribute(armorAddition(1.0F))
            .loot(Equipment.LootProperties.of(0));
    public static final Weapon.Entry stone_knuckle = knuckle("stone_knuckle",
            Weapon.CustomMaterial.matching(ToolMaterials.STONE, () -> Ingredient.fromTag(ItemTags.STONE_TOOL_MATERIALS)), 3.0F)
            .attribute(armorAddition(1.0F))
            .loot(Equipment.LootProperties.of(0));
    public static final Weapon.Entry iron_knuckle = knuckle("iron_knuckle",
            Weapon.CustomMaterial.matching(ToolMaterials.IRON, () -> Ingredient.ofItems(Items.IRON_INGOT)), 4.0F)
            .attribute(AttributeModifier.bonus(SpellSchools.ARCANE.id, T1_KNUCKLE_POWER))
            .attribute(armorAddition(2.0F))
            .loot(Equipment.LootProperties.of(1));
    public static final Weapon.Entry golden_knuckle = knuckle("golden_knuckle",
            Weapon.CustomMaterial.matching(ToolMaterials.GOLD, () -> Ingredient.ofItems(Items.GOLD_INGOT)), 2.0F)
            .attribute(AttributeModifier.bonus(SpellSchools.ARCANE.id, T1_KNUCKLE_POWER))
            .attribute(armorAddition(1.0F))
            .loot(Equipment.LootProperties.of("golden_weapon"));
    public static final Weapon.Entry diamond_knuckle = knuckle("diamond_knuckle",
            Weapon.CustomMaterial.matching(ToolMaterials.DIAMOND, () -> Ingredient.ofItems(Items.DIAMOND)), 5.0F)
            .attribute(AttributeModifier.bonus(SpellSchools.ARCANE.id, T2_KNUCKLE_POWER))
            .attribute(armorAddition(2.0F))
            .loot(Equipment.LootProperties.of(2));
    public static final Weapon.Entry netherite_knuckle = knuckle("netherite_knuckle",
            Weapon.CustomMaterial.matching(ToolMaterials.NETHERITE, () -> Ingredient.ofItems(Items.NETHERITE_INGOT)), 5.5F)
            .attribute(AttributeModifier.bonus(SpellSchools.ARCANE.id, T3_KNUCKLE_POWER))
            .attribute(armorAddition(3.0F))
            .loot(Equipment.LootProperties.of(3));


    private static final String BETTER_END = "betterend";
    private static final String BETTER_NETHER = "betternether";
    private static final String AETHER = "aether";
    private static final String LNE = "loot_n_explore";
    private static final float lneWeaponSpellPower = 4.0F;
    //Registration
    public static void register(Map<String,WeaponConfig> configs) {
        if (FabricLoader.getInstance().isModLoaded(BETTER_NETHER) || ForcemasterClassMod.tweaksConfig.value.ignore_items_required_mods) {
            var repair = ingredient("betternether:nether_ruby", FabricLoader.getInstance().isModLoaded(BETTER_NETHER), Items.NETHERITE_INGOT);
            knuckle("ruby_knuckle",
                    Weapon.CustomMaterial.matching(ToolMaterials.NETHERITE, repair),7.0F)
                    .attribute(AttributeModifier.bonus(SpellSchools.ARCANE.id, T4_KNUCKLE_POWER))
                    .attribute(armorAddition(3.0F))
                    .loot(Equipment.LootProperties.of(4));
        }
        if (FabricLoader.getInstance().isModLoaded(BETTER_END) || ForcemasterClassMod.tweaksConfig.value.ignore_items_required_mods) {
            var repair = ingredient("betterend:aeternium_ingot", FabricLoader.getInstance().isModLoaded(BETTER_END), Items.NETHERITE_INGOT);
            knuckle("aeternium_knuckle",
                    Weapon.CustomMaterial.matching(ToolMaterials.NETHERITE, repair),7.0F)
                    .attribute(AttributeModifier.bonus(SpellSchools.ARCANE.id, T4_KNUCKLE_POWER))
                    .attribute(armorAddition(3.0F))
                    .loot(Equipment.LootProperties.of(4));
        }
        if (FabricLoader.getInstance().isModLoaded(AETHER) || ForcemasterClassMod.tweaksConfig.value.ignore_items_required_mods) {
            var repair = ingredient("aether:ambrosium_shard", FabricLoader.getInstance().isModLoaded(AETHER), Items.NETHERITE_INGOT);
            knuckle("aether_knuckle",
                    Weapon.CustomMaterial.matching(ToolMaterials.NETHERITE, repair),7.0F)
                    .attribute(AttributeModifier.bonus(SpellSchools.ARCANE.id, T4_KNUCKLE_POWER))
                    .attribute(armorAddition(3.0F))
                    .loot(Equipment.LootProperties.of("aether"));

        }
        if(FabricLoader.getInstance().isModLoaded(LNE)|| ForcemasterClassMod.tweaksConfig.value.ignore_items_required_mods){
            knuckle( "ender_dragon_knuckle",
                    Weapon.CustomMaterial.matching(ToolMaterials.NETHERITE, () -> Ingredient.ofItems(Items.AMETHYST_SHARD)),7.0F)
                    .attribute(armorAddition(3.0F))
                    .attribute(AttributeModifier.bonus(SpellSchools.ARCANE.id, T4_KNUCKLE_POWER))
                    .rarity = Rarity.RARE;
            knuckle( "elder_guardian_knuckle",
                    Weapon.CustomMaterial.matching(ToolMaterials.NETHERITE, () -> Ingredient.ofItems(Items.PRISMARINE_SHARD)),7.0F)
                    .attribute(armorAddition(3.0F))
                    .attribute(AttributeModifier.bonus(SpellSchools.ARCANE.id, T4_KNUCKLE_POWER))
                    .attribute(AttributeModifier.bonus(MoreSpellSchools.WATER.id, lneWeaponSpellPower))
                    .rarity = Rarity.RARE;
            knuckle( "wither_knuckle",
                    Weapon.CustomMaterial.matching(ToolMaterials.NETHERITE, () -> Ingredient.ofItems(Items.BONE)),7.0F)
                    .attribute(armorAddition(3.0F))
                    .attribute(AttributeModifier.bonus(SpellSchools.ARCANE.id, T4_KNUCKLE_POWER))
                    .attribute(AttributeModifier.bonus(SpellSchools.SOUL.id, lneWeaponSpellPower))
                    .rarity = Rarity.RARE;
            knuckle( "glacial_knuckle",
                    Weapon.CustomMaterial.matching(ToolMaterials.NETHERITE, () -> Ingredient.ofItems(Items.ICE)),7.0F)
                    .attribute(armorAddition(3.0F))
                    .attribute(AttributeModifier.bonus(SpellSchools.ARCANE.id, T4_KNUCKLE_POWER))
                    .attribute(AttributeModifier.bonus(SpellSchools.FROST.id, lneWeaponSpellPower))
                    .rarity = Rarity.RARE;
        }
        Weapon.register(configs, entries, ForcemasterGroup.FORCEMASTER_KEY);
    }

}
