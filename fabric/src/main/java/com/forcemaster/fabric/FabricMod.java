package com.forcemaster.fabric;

import net.fabricmc.api.ModInitializer;
import net.forcemaster_rpg.ForcemasterClassMod;

public final class FabricMod implements ModInitializer {
    @Override
    public void onInitialize() {
        ForcemasterClassMod.init();
        ForcemasterClassMod.registerItems();
        ForcemasterClassMod.registerSounds();
        ForcemasterClassMod.registerEffects();
        ForcemasterClassMod.registerParticles();
    }
}
