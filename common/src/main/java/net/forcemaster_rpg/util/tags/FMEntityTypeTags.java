package net.forcemaster_rpg.util.tags;

import net.forcemaster_rpg.ForcemasterClassMod;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public class FMEntityTypeTags {

    public static final TagKey<EntityType<?>> KNOCK_UP_IMMUNE = register("knock_up_immune");

    private static TagKey<EntityType<?>> register(String id) {
        return TagKey.of(RegistryKeys.ENTITY_TYPE, ForcemasterClassMod.id(id));

    }
}
