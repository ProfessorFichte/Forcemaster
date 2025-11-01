package net.forcemaster_rpg.util;

import net.forcemaster_rpg.ForcemasterClassMod;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public class ForcemasterTags {
    public static final TagKey<Item> KNUCKLES = registerItemTags("knuckles");
    public static final TagKey<Item> FIST_WEAPON = registerItemTags("fist_weapons");
    public static final TagKey<EntityType<?>> KNOCK_UP_IMMUNE = registerEntityTags("knock_up_immune");
    public static final TagKey<EntityType<?>> STUN_IMMUNE = registerEntityTags("stun_immune");

    private static TagKey<Item> registerItemTags(String id) {
        return TagKey.of(RegistryKeys.ITEM, ForcemasterClassMod.id(id));
    }
    private static TagKey<EntityType<?>> registerEntityTags(String id) {
        return TagKey.of(RegistryKeys.ENTITY_TYPE, ForcemasterClassMod.id(id));

    }
}
