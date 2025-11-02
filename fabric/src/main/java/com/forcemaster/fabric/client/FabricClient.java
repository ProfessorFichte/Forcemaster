package com.forcemaster.fabric.client;

import net.fabricmc.api.ClientModInitializer;
import net.forcemaster_rpg.client.ForcemasterClient;

public final class FabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ForcemasterClient.init();
        ForcemasterClient.registerParticleAppearances();
    }
}
