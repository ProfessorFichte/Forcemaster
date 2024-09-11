package net.forcemaster_rpg.client.particle;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static net.forcemaster_rpg.ForcemasterClassMod.MOD_ID;
public class Particles {
    public static final SimpleParticleType ASAL_EXPLODE = FabricParticleTypes.simple();
    public static final SimpleParticleType BARQ_ESNA_FLAME = FabricParticleTypes.simple();
    public static final SimpleParticleType SONICHAND_VACUUM = FabricParticleTypes.simple();
    public static final SimpleParticleType PUNCH = FabricParticleTypes.simple();
    public static final SimpleParticleType SONIC_PUNCH = FabricParticleTypes.simple();
    public static final SimpleParticleType GROUND_PUNCH = FabricParticleTypes.simple();


    public static void register(){
        Registry.register(Registries.PARTICLE_TYPE, Identifier.of(MOD_ID, "asal_explode"), ASAL_EXPLODE);
        Registry.register(Registries.PARTICLE_TYPE, Identifier.of(MOD_ID, "barq_esna_flame"), BARQ_ESNA_FLAME);
        Registry.register(Registries.PARTICLE_TYPE, Identifier.of(MOD_ID, "sonichand_vacuum"), SONICHAND_VACUUM);
        Registry.register(Registries.PARTICLE_TYPE, Identifier.of(MOD_ID, "punch"), PUNCH);
        Registry.register(Registries.PARTICLE_TYPE, Identifier.of(MOD_ID, "sonic_punch"), SONIC_PUNCH);
        Registry.register(Registries.PARTICLE_TYPE, Identifier.of(MOD_ID, "ground_punch"), GROUND_PUNCH);
    }
}
