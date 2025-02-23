package net.forcemaster_rpg.item.tag;

import net.forcemaster_rpg.ForcemasterClassMod;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public class ModItemTags {

    public static final TagKey<Item> KNUCKLES = register("knuckles");
    public static final TagKey<Item> FIST_WEAPON = register("fist_weapons");
    public static final TagKey<Item> FORCEMASTER_ARMOR = register("forcemaster_armor");

    private static TagKey<Item> register(String id) {
        return TagKey.of(RegistryKeys.ITEM, ForcemasterClassMod.id(id));
    }

}
