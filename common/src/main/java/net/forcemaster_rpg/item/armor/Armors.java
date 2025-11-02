package net.forcemaster_rpg.item.armor;

import net.fabricmc.loader.api.FabricLoader;
import net.forcemaster_rpg.ForcemasterClassMod;
import net.forcemaster_rpg.item.ForcemasterGroup;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.spell_engine.api.config.ArmorSetConfig;
import net.spell_engine.api.config.AttributeModifier;
import net.spell_engine.api.item.Equipment;
import net.spell_engine.api.item.armor.Armor;
import net.spell_engine.api.spell.SpellDataComponents;
import net.spell_power.api.SpellSchools;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

import static net.forcemaster_rpg.ForcemasterClassMod.MOD_ID;

public class Armors {
    private static final Supplier<Ingredient> ORIENE_INGREDIENTS = () -> Ingredient.ofItems(
            Items.WHITE_WOOL, Items.ORANGE_WOOL, Items.MAGENTA_WOOL, Items.LIGHT_BLUE_WOOL, Items.YELLOW_WOOL,
            Items.LIME_WOOL, Items.PINK_WOOL, Items.GRAY_WOOL, Items.LIGHT_GRAY_WOOL, Items.CYAN_WOOL, Items.PURPLE_WOOL,
            Items.BLUE_WOOL, Items.BROWN_WOOL, Items.GREEN_WOOL, Items.RED_WOOL, Items.BLACK_WOOL, Items.LEATHER
    );
    private static final Supplier<Ingredient> PHASLEB_INGREDIENTS = () -> Ingredient.ofItems(
            Items.LEATHER, Items.GOLD_INGOT, Items.AMETHYST_SHARD
    );

    public static Identifier billporon_passive = Identifier.of(MOD_ID, "billporon");

    private static Armor.ItemSettingsTweaker commonSettings(Identifier equipmentSetId) {
        return Armor.ItemSettingsTweaker.standard(itemSettings -> {
            itemSettings
                    .component(SpellDataComponents.EQUIPMENT_SET, equipmentSetId)
                    .component(DataComponentTypes.RARITY, Rarity.RARE);
        });
    }

    private static final float orieneRobeSpellPower = 0.1F;
    private static final float orieneAttackSpeed = 0.02F;
    private static final float orieneArcaneFuse = 0.025F;
    private static final float phaslebRobeSpellPower = 0.15F;
    private static final float phaslebAttackSpeed = 0.03F;
    private static final float phaslebArcaneFuse = 0.05F;
    private static final float akenRobeSpellPower = 0.2F;
    private static final float akenAttackSpeed = 0.05F;
    private static final float akenArcaneFuse = 0.075F;
    private static final float billporonRobeSpellPower = 0.25F;
    private static final float billporonAttackSpeed = 0.05F;
    private static final float billporonArcaneFuse = 0.075F;

    public static RegistryEntry<ArmorMaterial> material(String name,
                                                        int protectionHead, int protectionChest, int protectionLegs, int protectionFeet,
                                                        int enchantability, RegistryEntry<SoundEvent> equipSound, Supplier<Ingredient> repairIngredient) {
        var material = new ArmorMaterial(
                Map.of(
                        ArmorItem.Type.HELMET, protectionHead,
                        ArmorItem.Type.CHESTPLATE, protectionChest,
                        ArmorItem.Type.LEGGINGS, protectionLegs,
                        ArmorItem.Type.BOOTS, protectionFeet),
                enchantability, equipSound, repairIngredient,
                List.of(new ArmorMaterial.Layer(Identifier.of(MOD_ID, name))),
                0,0
        );
        return Registry.registerReference(Registries.ARMOR_MATERIAL, Identifier.of(MOD_ID, name), material);
    }

    public static RegistryEntry<ArmorMaterial> material_oriene = material(
            "oriene",
            1, 3, 3, 1,
            9,
            SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, ORIENE_INGREDIENTS);

    public static RegistryEntry<ArmorMaterial> material_phasleb = material(
            "phasleb",
            2, 4, 4, 2,
            11,
            SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, PHASLEB_INGREDIENTS);
    public static RegistryEntry<ArmorMaterial> material_aken = material(
            "aken",
            2, 4, 4, 2,
            20,
            SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, () -> { return Ingredient.ofItems(Items.NETHERITE_INGOT); });
    public static RegistryEntry<ArmorMaterial> material_billporon = material(
            "billporon",
            2, 4, 4, 2,
            20,
            SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, () -> { return Ingredient.ofItems(Items.NETHERITE_INGOT); });


    public static final ArrayList<Armor.Entry> entries = new ArrayList<>();
    private static Armor.Entry create(RegistryEntry<ArmorMaterial> material, Identifier id, int durability,
                                      Armor.Set.ItemFactory factory, ArmorSetConfig defaults, int tier, Armor.ItemSettingsTweaker settings) {
        var entry = Armor.Entry.create(
                material,
                id,
                durability,
                factory,
                defaults,
                Equipment.LootProperties.of(tier),
                settings
        );
        entries.add(entry);
        return entry;
    }

    public static final Armor.Set orieneArmorSet =
            create(
                    material_oriene,
                    Identifier.of(MOD_ID, "oriene"),
                    15,
                    OrieneArmor::new,
                    ArmorSetConfig.with(
                            new ArmorSetConfig.Piece(1)
                                    .addAll(List.of(
                                            AttributeModifier.multiply(SpellSchools.ARCANE.id, orieneRobeSpellPower),
                                            AttributeModifier.multiply(Objects.requireNonNull(Identifier.tryParse("minecraft:generic.attack_speed")),orieneAttackSpeed),
                                            AttributeModifier.multiply(Objects.requireNonNull(Identifier.tryParse("more_rpg_classes:arcane_fuse_modifier")),orieneArcaneFuse)
                                    )),
                            new ArmorSetConfig.Piece(3)
                                    .addAll(List.of(
                                            AttributeModifier.multiply(SpellSchools.ARCANE.id, orieneRobeSpellPower),
                                            AttributeModifier.multiply(Objects.requireNonNull(Identifier.tryParse("minecraft:generic.attack_speed")),orieneAttackSpeed),
                                            AttributeModifier.multiply(Objects.requireNonNull(Identifier.tryParse("more_rpg_classes:arcane_fuse_modifier")),orieneArcaneFuse)
                                    )),
                            new ArmorSetConfig.Piece(3)
                                    .addAll(List.of(
                                            AttributeModifier.multiply(SpellSchools.ARCANE.id, orieneRobeSpellPower),
                                            AttributeModifier.multiply(Objects.requireNonNull(Identifier.tryParse("minecraft:generic.attack_speed")),orieneAttackSpeed),
                                            AttributeModifier.multiply(Objects.requireNonNull(Identifier.tryParse("more_rpg_classes:arcane_fuse_modifier")),orieneArcaneFuse)
                                    )),
                            new ArmorSetConfig.Piece(1)
                                    .addAll(List.of(
                                            AttributeModifier.multiply(SpellSchools.ARCANE.id, orieneRobeSpellPower),
                                            AttributeModifier.multiply(Objects.requireNonNull(Identifier.tryParse("minecraft:generic.attack_speed")),orieneAttackSpeed),
                                            AttributeModifier.multiply(Objects.requireNonNull(Identifier.tryParse("more_rpg_classes:arcane_fuse_modifier")),orieneArcaneFuse)
                                    ))
                    ),1, null)
                    .armorSet();

    public static final Armor.Set phaslebArmorSet =
            create(material_phasleb,
                    Identifier.of(MOD_ID, "phasleb"),
                    25,
                    PhaslebArmor::new,
                    ArmorSetConfig.with(
                            new ArmorSetConfig.Piece(2)
                                    .addAll(List.of(
                                            AttributeModifier.multiply(SpellSchools.ARCANE.id, phaslebRobeSpellPower),
                                            AttributeModifier.multiply(Objects.requireNonNull(Identifier.tryParse("minecraft:generic.attack_speed")),phaslebAttackSpeed),
                                            AttributeModifier.multiply(Objects.requireNonNull(Identifier.tryParse("more_rpg_classes:arcane_fuse_modifier")),phaslebArcaneFuse)
                                    )),
                            new ArmorSetConfig.Piece(4)
                                    .addAll(List.of(
                                            AttributeModifier.multiply(SpellSchools.ARCANE.id, phaslebRobeSpellPower),
                                            AttributeModifier.multiply(Objects.requireNonNull(Identifier.tryParse("minecraft:generic.attack_speed")),phaslebAttackSpeed),
                                            AttributeModifier.multiply(Objects.requireNonNull(Identifier.tryParse("more_rpg_classes:arcane_fuse_modifier")),phaslebArcaneFuse)
                                    )),
                            new ArmorSetConfig.Piece(4)
                                    .addAll(List.of(
                                            AttributeModifier.multiply(SpellSchools.ARCANE.id, phaslebRobeSpellPower),
                                            AttributeModifier.multiply(Objects.requireNonNull(Identifier.tryParse("minecraft:generic.attack_speed")),phaslebAttackSpeed),
                                            AttributeModifier.multiply(Objects.requireNonNull(Identifier.tryParse("more_rpg_classes:arcane_fuse_modifier")),phaslebArcaneFuse)
                                    )),
                            new ArmorSetConfig.Piece(2)
                                    .addAll(List.of(
                                            AttributeModifier.multiply(SpellSchools.ARCANE.id, phaslebRobeSpellPower),
                                            AttributeModifier.multiply(Objects.requireNonNull(Identifier.tryParse("minecraft:generic.attack_speed")),phaslebAttackSpeed),
                                            AttributeModifier.multiply(Objects.requireNonNull(Identifier.tryParse("more_rpg_classes:arcane_fuse_modifier")),phaslebArcaneFuse)
                                    ))
                    ),2, null)
                    .armorSet();

    public static final Armor.Set akenArmorSet =
            create(material_aken,
                    Identifier.of(MOD_ID, "aken"),
                    30,
                    AkenArmor::new,
                    ArmorSetConfig.with(
                            new ArmorSetConfig.Piece(2)
                                    .addAll(List.of(
                                            AttributeModifier.multiply(SpellSchools.ARCANE.id, akenRobeSpellPower),
                                            AttributeModifier.multiply(Objects.requireNonNull(Identifier.tryParse("minecraft:generic.attack_speed")),akenAttackSpeed),
                                            AttributeModifier.multiply(Objects.requireNonNull(Identifier.tryParse("more_rpg_classes:arcane_fuse_modifier")),akenArcaneFuse)
                                    )),
                            new ArmorSetConfig.Piece(4)
                                    .addAll(List.of(
                                            AttributeModifier.multiply(SpellSchools.ARCANE.id, akenRobeSpellPower),
                                            AttributeModifier.multiply(Objects.requireNonNull(Identifier.tryParse("minecraft:generic.attack_speed")),akenAttackSpeed),
                                            AttributeModifier.multiply(Objects.requireNonNull(Identifier.tryParse("more_rpg_classes:arcane_fuse_modifier")),akenArcaneFuse)
                                    )),
                            new ArmorSetConfig.Piece(4)
                                    .addAll(List.of(
                                            AttributeModifier.multiply(SpellSchools.ARCANE.id, akenRobeSpellPower),
                                            AttributeModifier.multiply(Objects.requireNonNull(Identifier.tryParse("minecraft:generic.attack_speed")),akenAttackSpeed),
                                            AttributeModifier.multiply(Objects.requireNonNull(Identifier.tryParse("more_rpg_classes:arcane_fuse_modifier")),akenArcaneFuse)
                                    )),
                            new ArmorSetConfig.Piece(2)
                                    .addAll(List.of(
                                            AttributeModifier.multiply(SpellSchools.ARCANE.id, akenRobeSpellPower),
                                            AttributeModifier.multiply(Objects.requireNonNull(Identifier.tryParse("minecraft:generic.attack_speed")),akenAttackSpeed),
                                            AttributeModifier.multiply(Objects.requireNonNull(Identifier.tryParse("more_rpg_classes:arcane_fuse_modifier")),akenArcaneFuse)
                                    ))
                    ),3, null)
                    .armorSet();

    public static Armor.Entry billporonArmorSet;


    public static void register(Map<String, ArmorSetConfig> configs) {
        if (FabricLoader.getInstance().isModLoaded("armory_rpgs") || ForcemasterClassMod.tweaksConfig.value.ignore_items_required_mods) {
            billporonArmorSet = create(
                    material_billporon,
                    Identifier.of(MOD_ID, "billporon"),
                    40,
                    BillporonArmor::new,
                    ArmorSetConfig.with(
                            new ArmorSetConfig.Piece(2)
                                    .addAll(List.of(
                                            AttributeModifier.multiply(SpellSchools.ARCANE.id, billporonRobeSpellPower),
                                            AttributeModifier.multiply(Objects.requireNonNull(Identifier.tryParse("minecraft:generic.attack_speed")),billporonAttackSpeed),
                                            AttributeModifier.multiply(Objects.requireNonNull(Identifier.tryParse("more_rpg_classes:arcane_fuse_modifier")),billporonArcaneFuse)
                                    )),
                            new ArmorSetConfig.Piece(4)
                                    .addAll(List.of(
                                            AttributeModifier.multiply(SpellSchools.ARCANE.id, billporonRobeSpellPower),
                                            AttributeModifier.multiply(Objects.requireNonNull(Identifier.tryParse("minecraft:generic.attack_speed")),billporonAttackSpeed),
                                            AttributeModifier.multiply(Objects.requireNonNull(Identifier.tryParse("more_rpg_classes:arcane_fuse_modifier")),billporonArcaneFuse)
                                    )),
                            new ArmorSetConfig.Piece(4)
                                    .addAll(List.of(
                                            AttributeModifier.multiply(SpellSchools.ARCANE.id, billporonRobeSpellPower),
                                            AttributeModifier.multiply(Objects.requireNonNull(Identifier.tryParse("minecraft:generic.attack_speed")),billporonAttackSpeed),
                                            AttributeModifier.multiply(Objects.requireNonNull(Identifier.tryParse("more_rpg_classes:arcane_fuse_modifier")),billporonArcaneFuse)
                                    )),
                            new ArmorSetConfig.Piece(2)
                                    .addAll(List.of(
                                            AttributeModifier.multiply(SpellSchools.ARCANE.id, billporonRobeSpellPower),
                                            AttributeModifier.multiply(Objects.requireNonNull(Identifier.tryParse("minecraft:generic.attack_speed")),billporonAttackSpeed),
                                            AttributeModifier.multiply(Objects.requireNonNull(Identifier.tryParse("more_rpg_classes:arcane_fuse_modifier")),billporonArcaneFuse)
                                    ))
                    ),5,
                    commonSettings(billporon_passive)
            );
        }
        Armor.register(configs, entries, ForcemasterGroup.FORCEMASTER_KEY);
    }
}