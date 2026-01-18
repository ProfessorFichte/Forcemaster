package net.forcemaster_rpg.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.forcemaster_rpg.item.armor.Armors;
import net.forcemaster_rpg.item.weapons.WeaponsRegister;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.more_rpg_classes.datagen.SmithingRecipeGenerator;

import static net.forcemaster_rpg.ForcemasterClassMod.MOD_ID;

/**
 * Generates all smithing transform recipes for Forcemaster RPG
 */
public class ForcemasterSmithingRecipes extends SmithingRecipeGenerator {

    public ForcemasterSmithingRecipes(FabricDataOutput output) {
        super(output, MOD_ID);
    }

    @Override
    public String getName() {
        return "Smithing Recipes (" + MOD_ID + ")";
    }

    @Override
    public void generate() {
        // ====================
        // KNUCKLE UPGRADES - NETHERITE
        // ====================
        createSimpleSmithingRecipe(
                "netherite_knuckle",
                WeaponsRegister.diamond_knuckle.item(),
                Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE,
                Items.NETHERITE_INGOT,
                WeaponsRegister.netherite_knuckle.item()
        );

        // ====================
        // KNUCKLE UPGRADES - BETTERNETHER (Ruby)
        // ====================
        var rubyKnuckle = WeaponsRegister.entries.stream()
                .filter(e -> e.id().getPath().equals("ruby_knuckle"))
                .findFirst().map(e -> e.item()).orElse(null);

        if (rubyKnuckle != null) {
            createSmithingTransformRecipe(
                    "ruby_knuckle",
                    WeaponsRegister.netherite_knuckle.item(),
                    Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE,
                    Identifier.of("betternether", "nether_ruby"),
                    rubyKnuckle,
                    "betternether"
            );
        }

        // ====================
        // KNUCKLE UPGRADES - LOOT N EXPLORE
        // ====================
        var glacialKnuckle = WeaponsRegister.entries.stream()
                .filter(e -> e.id().getPath().equals("glacial_knuckle"))
                .findFirst().map(e -> e.item()).orElse(null);

        if (glacialKnuckle != null) {
            createSmithingTransformRecipe(
                    "glacial_knuckle",
                    WeaponsRegister.netherite_knuckle.item(),
                    Identifier.of("loot_n_explore", "frostmonarch_upgrade_smithing_template"),
                    Identifier.of("loot_n_explore", "frozen_soul"),
                    glacialKnuckle,
                    "loot_n_explore"
            );
        }

        var elderGuardianKnuckle = WeaponsRegister.entries.stream()
                .filter(e -> e.id().getPath().equals("elder_guardian_knuckle"))
                .findFirst().map(e -> e.item()).orElse(null);

        if (elderGuardianKnuckle != null) {
            createSmithingTransformRecipe(
                    "elder_guardian_knuckle",
                    WeaponsRegister.netherite_knuckle.item(),
                    Identifier.of("loot_n_explore", "guardian_upgrade_smithing_template"),
                    Identifier.of("loot_n_explore", "elder_guardian_eye"),
                    elderGuardianKnuckle,
                    "loot_n_explore"
            );
        }

        var enderDragonKnuckle = WeaponsRegister.entries.stream()
                .filter(e -> e.id().getPath().equals("ender_dragon_knuckle"))
                .findFirst().map(e -> e.item()).orElse(null);

        if (enderDragonKnuckle != null) {
            createSmithingTransformRecipe(
                    "ender_dragon_knuckle",
                    WeaponsRegister.netherite_knuckle.item(),
                    Identifier.of("loot_n_explore", "dragonslayer_upgrade_smithing_template"),
                    Identifier.of("loot_n_explore", "ender_dragon_scale"),
                    enderDragonKnuckle,
                    "loot_n_explore"
            );
        }

        var witherKnuckle = WeaponsRegister.entries.stream()
                .filter(e -> e.id().getPath().equals("wither_knuckle"))
                .findFirst().map(e -> e.item()).orElse(null);

        if (witherKnuckle != null) {
            createSmithingTransformRecipe(
                    "wither_knuckle",
                    WeaponsRegister.netherite_knuckle.item(),
                    Identifier.of("loot_n_explore", "wither_upgrade_smithing_template"),
                    Identifier.of("loot_n_explore", "wither_spine"),
                    witherKnuckle,
                    "loot_n_explore"
            );
        }

        // ====================
        // ARMOR UPGRADES - PHASLEB TO AKEN (Netherite)
        // ====================
        createSimpleArmorSetUpgrade(
                "smithing",
                Armors.phaslebArmorSet.armorSet(),
                Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE,
                Items.NETHERITE_INGOT,
                Armors.akenArmorSet.armorSet()
        );

        // ====================
        // ARMOR UPGRADES - AKEN TO BILLPORON
        // ====================
        if (Armors.billporonArmorSet != null) {
            // From Aken to Billporon
            createSmithingTransformRecipe(
                    "smithing_billporon_head_aken_head",
                    (Item) Armors.akenArmorSet.armorSet().head,
                    Identifier.of("armory_rpgs", "epic_armor_upgrade"),
                    Identifier.of("more_rpg_classes", "ascetic_upgrade_crystal"),
                    (Item) Armors.billporonArmorSet.armorSet().head,
                    "armory_rpgs"
            );
            createSmithingTransformRecipe(
                    "smithing_billporon_chest_aken_chest",
                    (Item) Armors.akenArmorSet.armorSet().chest,
                    Identifier.of("armory_rpgs", "epic_armor_upgrade"),
                    Identifier.of("more_rpg_classes", "ascetic_upgrade_crystal"),
                    (Item) Armors.billporonArmorSet.armorSet().chest,
                    "armory_rpgs"
            );
            createSmithingTransformRecipe(
                    "smithing_billporon_legs_aken_legs",
                    (Item) Armors.akenArmorSet.armorSet().legs,
                    Identifier.of("armory_rpgs", "epic_armor_upgrade"),
                    Identifier.of("more_rpg_classes", "ascetic_upgrade_crystal"),
                    (Item) Armors.billporonArmorSet.armorSet().legs,
                    "armory_rpgs"
            );
            createSmithingTransformRecipe(
                    "smithing_billporon_feet_aken_feet",
                    (Item) Armors.akenArmorSet.armorSet().feet,
                    Identifier.of("armory_rpgs", "epic_armor_upgrade"),
                    Identifier.of("more_rpg_classes", "ascetic_upgrade_crystal"),
                    (Item) Armors.billporonArmorSet.armorSet().feet,
                    "armory_rpgs"
            );

            // ====================
            // ARMOR UPGRADES - PHASLEB TO BILLPORON
            // ====================
            createSmithingTransformRecipe(
                    "smithing_billporon_head_phasleb_head",
                    (Item) Armors.phaslebArmorSet.armorSet().head,
                    Identifier.of("armory_rpgs", "epic_armor_upgrade"),
                    Identifier.of("more_rpg_classes", "ascetic_upgrade_crystal"),
                    (Item) Armors.billporonArmorSet.armorSet().head,
                    "armory_rpgs"
            );
            createSmithingTransformRecipe(
                    "smithing_billporon_chest_phasleb_chest",
                    (Item) Armors.phaslebArmorSet.armorSet().chest,
                    Identifier.of("armory_rpgs", "epic_armor_upgrade"),
                    Identifier.of("more_rpg_classes", "ascetic_upgrade_crystal"),
                    (Item) Armors.billporonArmorSet.armorSet().chest,
                    "armory_rpgs"
            );
            createSmithingTransformRecipe(
                    "smithing_billporon_legs_phasleb_legs",
                    (Item) Armors.phaslebArmorSet.armorSet().legs,
                    Identifier.of("armory_rpgs", "epic_armor_upgrade"),
                    Identifier.of("more_rpg_classes", "ascetic_upgrade_crystal"),
                    (Item) Armors.billporonArmorSet.armorSet().legs,
                    "armory_rpgs"
            );
            createSmithingTransformRecipe(
                    "smithing_billporon_feet_phasleb_feet",
                    (Item) Armors.phaslebArmorSet.armorSet().feet,
                    Identifier.of("armory_rpgs", "epic_armor_upgrade"),
                    Identifier.of("more_rpg_classes", "ascetic_upgrade_crystal"),
                    (Item) Armors.billporonArmorSet.armorSet().feet,
                    "armory_rpgs"
            );
        }
    }
}
