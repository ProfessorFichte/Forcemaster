package net.forcemaster_rpg.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;

import static net.forcemaster_rpg.ForcemasterClassMod.MOD_ID;

public class ForcemasterEntities {
    public static final Identifier NEN_SPHERE_BEAM_ID = new Identifier(MOD_ID, "nen_sphere_beam");

    public static void registerEntities() {
        entityTypesToRegister().forEach((id, type) -> Registry.register(Registries.ENTITY_TYPE, id, type));
    }

    public static Map<Identifier, EntityType<?>> entityTypesToRegister() {
        if (NenSphereBeamEntity.ENTITY_TYPE == null) {
            NenSphereBeamEntity.ENTITY_TYPE = EntityType.Builder
                    .<NenSphereBeamEntity>create(NenSphereBeamEntity::new, SpawnGroup.MISC)
                    .setDimensions(0F, 0F)
                    .maxTrackingRange(64)
                    .trackingTickInterval(20)
                    .build("nen_sphere_beam");
        }
        var types = new LinkedHashMap<Identifier, EntityType<?>>();
        if (!Registries.ENTITY_TYPE.containsId(NEN_SPHERE_BEAM_ID)) {
            types.put(NEN_SPHERE_BEAM_ID, NenSphereBeamEntity.ENTITY_TYPE);
        }
        return types;
    }
}
