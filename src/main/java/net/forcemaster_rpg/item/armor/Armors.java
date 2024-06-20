package net.forcemaster_rpg.item.armor;

import net.forcemaster_rpg.ForcemasterClassMod;
import net.forcemaster_rpg.item.ForcemasterGroup;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvents;

import net.spell_engine.api.item.ItemConfig;
import net.spell_engine.api.item.armor.Armor;
import net.spell_power.api.SpellPowerMechanics;
import net.spell_power.api.SpellSchools;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class Armors {
    private static final Supplier<Ingredient> ORIENE_INGREDIENTS = () -> Ingredient.ofItems(
            Items.WHITE_WOOL, Items.ORANGE_WOOL, Items.MAGENTA_WOOL, Items.LIGHT_BLUE_WOOL, Items.YELLOW_WOOL,
            Items.LIME_WOOL, Items.PINK_WOOL, Items.GRAY_WOOL, Items.LIGHT_GRAY_WOOL, Items.CYAN_WOOL, Items.PURPLE_WOOL,
            Items.BLUE_WOOL, Items.BROWN_WOOL, Items.GREEN_WOOL, Items.RED_WOOL, Items.BLACK_WOOL, Items.LEATHER
    );
    private static final Supplier<Ingredient> PHASLEB_INGREDIENTS = () -> Ingredient.ofItems(
            Items.LEATHER, Items.GOLD_INGOT, Items.AMETHYST_SHARD
    );


    private static final float orieneRobeSpellPower = 1.5F;
    private static final float orieneHaste = 0.01F;
    private static final float orieneHasteSuit = 0.025F;
    private static final float phaslebRobeSpellPower = 0.15F;
    private static final float phaslebHaste = 0.025F;
    private static final float phaslebHasteSuit = 0.05F;


    public static final ArrayList<Armor.Entry> entries = new ArrayList<>();
    private static Armor.Entry create(Armor.CustomMaterial material, ItemConfig.ArmorSet defaults) {
        return new Armor.Entry(material, null, defaults);
    }


    public static final Armor.Set orieneArmorSet =
            create(
                    new Armor.CustomMaterial(
                            "oriene",
                            10,
                            9,
                            SoundEvents.ITEM_ARMOR_EQUIP_LEATHER,
                            ORIENE_INGREDIENTS
                    ),
                    ItemConfig.ArmorSet.with(
                            new ItemConfig.ArmorSet.Piece(2)
                                    .addAll(List.of(
                                            ItemConfig.Attribute.bonus(SpellSchools.ARCANE.id, orieneRobeSpellPower),
                                            ItemConfig.Attribute.multiply(SpellPowerMechanics.HASTE.id, orieneHaste)
                                    )),
                            new ItemConfig.ArmorSet.Piece(3)
                                    .addAll(List.of(
                                            ItemConfig.Attribute.bonus(SpellSchools.ARCANE.id, orieneRobeSpellPower),
                                            ItemConfig.Attribute.multiply(SpellPowerMechanics.HASTE.id, orieneHasteSuit)
                                    )),
                            new ItemConfig.ArmorSet.Piece(2)
                                    .addAll(List.of(
                                            ItemConfig.Attribute.bonus(SpellSchools.ARCANE.id, orieneRobeSpellPower),
                                            ItemConfig.Attribute.multiply(SpellPowerMechanics.HASTE.id, orieneHaste)
                                    )),
                            new ItemConfig.ArmorSet.Piece(1)
                                    .addAll(List.of(
                                            ItemConfig.Attribute.bonus(SpellSchools.ARCANE.id, orieneRobeSpellPower),
                                            ItemConfig.Attribute.multiply(SpellPowerMechanics.HASTE.id, orieneHaste)
                                    ))
                    ))   .bundle(material -> new Armor.Set<>(ForcemasterClassMod.MOD_ID,
                            new OrieneArmor(material, ArmorItem.Type.HELMET, new Item.Settings()),
                            new OrieneArmor(material, ArmorItem.Type.CHESTPLATE, new Item.Settings()),
                            new OrieneArmor(material, ArmorItem.Type.LEGGINGS, new Item.Settings()),
                            new OrieneArmor(material, ArmorItem.Type.BOOTS, new Item.Settings())
                    ))
                    .put(entries).armorSet().allowSpellPowerEnchanting(true);

    public static final Armor.Set phaslebArmorSet =
            create(
                    new Armor.CustomMaterial(
                            "phasleb",
                            20,
                            11,
                            SoundEvents.ITEM_ARMOR_EQUIP_LEATHER,
                            PHASLEB_INGREDIENTS
                    ),
                    ItemConfig.ArmorSet.with(
                            new ItemConfig.ArmorSet.Piece(2)
                                    .addAll(List.of(
                                            ItemConfig.Attribute.multiply(SpellPowerMechanics.HASTE.id, phaslebHaste),
                                            ItemConfig.Attribute.multiply(SpellSchools.ARCANE.id, phaslebRobeSpellPower)
                                    )),
                            new ItemConfig.ArmorSet.Piece(4)
                                    .addAll(List.of(
                                            ItemConfig.Attribute.multiply(SpellPowerMechanics.HASTE.id, phaslebHasteSuit),
                                            ItemConfig.Attribute.multiply(SpellSchools.ARCANE.id, phaslebRobeSpellPower)
                                    )),
                            new ItemConfig.ArmorSet.Piece(3)
                                    .addAll(List.of(
                                            ItemConfig.Attribute.multiply(SpellPowerMechanics.HASTE.id, phaslebHaste),
                                            ItemConfig.Attribute.multiply(SpellSchools.ARCANE.id, phaslebRobeSpellPower)
                                    )),
                            new ItemConfig.ArmorSet.Piece(2)
                                    .addAll(List.of(
                                            ItemConfig.Attribute.multiply(SpellPowerMechanics.HASTE.id, phaslebHaste),
                                            ItemConfig.Attribute.multiply(SpellSchools.ARCANE.id, phaslebRobeSpellPower)
                                    ))
                    ))   .bundle(material -> new Armor.Set<>(ForcemasterClassMod.MOD_ID,
                            new PhaslebArmor(material, ArmorItem.Type.HELMET, new Item.Settings()),
                            new PhaslebArmor(material, ArmorItem.Type.CHESTPLATE, new Item.Settings()),
                            new PhaslebArmor(material, ArmorItem.Type.LEGGINGS, new Item.Settings()),
                            new PhaslebArmor(material, ArmorItem.Type.BOOTS, new Item.Settings())
                    ))
                    .put(entries).armorSet().allowSpellPowerEnchanting(true);



    public static void register(Map<String, ItemConfig.ArmorSet> configs) {
        Armor.register(configs, entries, ForcemasterGroup.FORCEMASTER_KEY);
    }
}