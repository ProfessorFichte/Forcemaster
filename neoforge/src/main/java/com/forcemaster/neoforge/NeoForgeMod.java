package com.forcemaster.neoforge;

import net.forcemaster_rpg.ForcemasterClassMod;
import net.forcemaster_rpg.client.particle.Particles;
import net.minecraft.registry.RegistryKeys;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.RegisterEvent;


@Mod(ForcemasterClassMod.MOD_ID)
public final class NeoForgeMod {
    public NeoForgeMod(IEventBus modBus) {
        ForcemasterClassMod.init();
        modBus.addListener(RegisterEvent.class, NeoForgeMod::register);
    }
    public static void register(RegisterEvent event) {
        event.register(RegistryKeys.SOUND_EVENT, reg -> {
            ForcemasterClassMod.registerSounds();
        });
        event.register(RegistryKeys.ITEM, reg -> {
            ForcemasterClassMod.registerItems();
        });
        event.register(RegistryKeys.STATUS_EFFECT, reg -> {
            ForcemasterClassMod.registerEffects();
        });
        event.register(RegistryKeys.PARTICLE_TYPE, reg -> {
            Particles.register();
        });
    }
}
