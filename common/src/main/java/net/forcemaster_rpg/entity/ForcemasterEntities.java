package net.forcemaster_rpg.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static net.forcemaster_rpg.ForcemasterClassMod.MOD_ID;

public class ForcemasterEntities {

    public static void registerEntities() {
        NenSphereBeamEntity.ENTITY_TYPE = Registry.register(
                Registries.ENTITY_TYPE,
                Identifier.of(MOD_ID, "nen_sphere_beam"),
                FabricEntityTypeBuilder.<NenSphereBeamEntity>create(SpawnGroup.MISC, NenSphereBeamEntity::new)
                        .dimensions(EntityDimensions.fixed(0F, 0F))
                        .trackRangeBlocks(64)
                        .trackedUpdateRate(20)
                        .build()
        );
    }
}
