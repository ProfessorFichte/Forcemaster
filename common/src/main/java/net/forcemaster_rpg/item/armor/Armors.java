package net.forcemaster_rpg.item.armor;

import net.forcemaster_rpg.item.ForcemasterGroup;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.more_rpg_classes.item.MRPGCItemGroups;
import net.spell_engine.rpg_series.config.ArmorSetConfig;
import net.spell_engine.rpg_series.config.AttributeModifier;
import net.spell_engine.rpg_series.item.Equipment;
import net.spell_engine.rpg_series.item.Armor;
import net.spell_engine.api.item.SpellItemData;
import net.spell_power.api.SpellSchools;

import java.util.ArrayList;
import java.util.IdentityHashMap;
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

    public static Identifier billporon_passive = new Identifier(MOD_ID, "billporon");

    private static Armor.ItemSettingsTweaker commonSettings(Identifier equipmentSetId) {
        return Armor.ItemSettingsTweaker.standard(itemSettings -> {
            itemSettings.rarity(Rarity.RARE);
            SpellItemData.defaults(itemSettings).equipmentSet(equipmentSetId);
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

    /// 1.20.1 armor materials are plain `ArmorMaterial` objects: no registry entry, no layer list.
    /// `Armor.material`'s `id` doubles as the (single) 1.21 `ArmorMaterial.Layer` id.
    public static ArmorMaterial material(String name,
                                         int protectionHead, int protectionChest, int protectionLegs, int protectionFeet,
                                         int enchantability, SoundEvent equipSound, Supplier<Ingredient> repairIngredient) {
        return Armor.material(
                new Identifier(MOD_ID, name),
                Map.of(
                        ArmorItem.Type.HELMET, protectionHead,
                        ArmorItem.Type.CHESTPLATE, protectionChest,
                        ArmorItem.Type.LEGGINGS, protectionLegs,
                        ArmorItem.Type.BOOTS, protectionFeet),
                enchantability, equipSound, repairIngredient,
                0, 0
        );
    }

    public static ArmorMaterial material_oriene = material(
            "oriene",
            1, 3, 3, 1,
            9,
            SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, ORIENE_INGREDIENTS);

    public static ArmorMaterial material_phasleb = material(
            "phasleb",
            2, 4, 4, 2,
            11,
            SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, PHASLEB_INGREDIENTS);
    public static ArmorMaterial material_aken = material(
            "aken",
            2, 4, 4, 2,
            20,
            SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, () -> { return Ingredient.ofItems(Items.NETHERITE_INGOT); });
    public static ArmorMaterial material_billporon = material(
            "billporon",
            2, 4, 4, 2,
            20,
            SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, () -> { return Ingredient.ofItems(Items.NETHERITE_INGOT); });


    public static final ArrayList<Armor.Entry> entries = new ArrayList<>();
    private static Armor.Entry create(ArmorMaterial material, Identifier id, int durability,
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

    public static final Map<Armor.Entry, RegistryKey<ItemGroup>> groupOverrides = new IdentityHashMap<>();

    private static Armor.Entry groupKey(Armor.Entry entry, RegistryKey<ItemGroup> key) {
        groupOverrides.put(entry, key);
        return entry;
    }

    public static final Armor.Entry orieneArmorSet =
            create(
                    material_oriene,
                    new Identifier(MOD_ID, "oriene"),
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
                    .translatedName("Oriene Band", "Oriene Suit", "Oriene Pants", "Oriene Boots");

    public static final Armor.Entry phaslebArmorSet =
            create(material_phasleb,
                    new Identifier(MOD_ID, "phasleb"),
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
                    .translatedName("Phasleb Headdress", "Phasleb Suit", "Phasleb Pants", "Phasleb Boots");

    public static final Armor.Entry akenArmorSet =
            create(material_aken,
                    new Identifier(MOD_ID, "aken"),
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
                    .translatedName("Aken Headdress", "Aken Suit", "Aken Pants", "Aken Boots");

    public static Armor.Entry billporonArmorSet;


    public static void register(Map<String, ArmorSetConfig> configs) {
        itemsToRegister(configs).forEach((id, item) -> Registry.register(Registries.ITEM, id, item));
    }

    /// Every armor piece keyed by the id it registers under. Creation only - nothing is written here, so a
    /// loader that registers items itself (Forge, through the helper `RegisterEvent` hands out) iterates
    /// this instead of calling {@link #register}. The Armory-flavoured Billporon set has to be appended
    /// *before* `Armor.itemsToRegister` sees the list, which is why this wrapper exists.
    public static Map<Identifier, Item> itemsToRegister(Map<String, ArmorSetConfig> configs) {
        createOptionalEntries();
        return Armor.itemsToRegister(configs, entries, ForcemasterGroup.FORCEMASTER_KEY);
    }

    private static boolean optionalEntriesCreated = false;

    private static void createOptionalEntries() {
        if (optionalEntriesCreated) { return; }
        optionalEntriesCreated = true;
        billporonArmorSet = groupKey(create(
                material_billporon,
                new Identifier(MOD_ID, "billporon"),
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
        ).translatedName("Billporon Headdress", "Billporon Suit", "Billporon Pants", "Billporon Boots"), MRPGCItemGroups.ARMORY_KEY);
    }
}