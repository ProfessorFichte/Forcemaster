package com.forcemaster.fabric.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.forcemaster_rpg.client.ForcemasterClient;
import net.forcemaster_rpg.client.entity.NenSphereBeamRenderer;
import net.forcemaster_rpg.entity.NenSphereBeamEntity;

public final class FabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ForcemasterClient.init();
        EntityRendererRegistry.register(NenSphereBeamEntity.ENTITY_TYPE, NenSphereBeamRenderer::new);
        ForcemasterClient.registerParticleAppearances((type, factory) ->
                ParticleFactoryRegistry.getInstance().register(type, factory::apply));
    }
}
