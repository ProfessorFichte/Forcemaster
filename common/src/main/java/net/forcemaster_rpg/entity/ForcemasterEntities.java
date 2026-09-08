package net.forcemaster_rpg.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static net.forcemaster_rpg.ForcemasterClassMod.MOD_ID;

public class ForcemasterEntities {

    public static void registerEntities() {
        NenSphereBeamEntity.ENTITY_TYPE = Registry.register(
                Registries.ENTITY_TYPE,
                new Identifier(MOD_ID, "nen_sphere_beam"),
                EntityType.Builder.<NenSphereBeamEntity>create(NenSphereBeamEntity::new, SpawnGroup.MISC)
                        .setDimensions(0F, 0F)
                        .maxTrackingRange(64)
                        .trackingTickInterval(20)
                        .build("nen_sphere_beam")
        );
    }
}
