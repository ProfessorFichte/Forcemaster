package com.forcemaster.forge.client;

import net.forcemaster_rpg.client.ForcemasterClient;
import net.forcemaster_rpg.client.entity.NenSphereBeamRenderer;
import net.forcemaster_rpg.entity.NenSphereBeamEntity;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public final class ForgeClient {
    private ForgeClient() { }

    public static void register(IEventBus modBus) {
        modBus.addListener(EventPriority.NORMAL, false, FMLClientSetupEvent.class, ForgeClient::onClientSetup);
        modBus.addListener(EventPriority.NORMAL, false, EntityRenderersEvent.RegisterRenderers.class,
                ForgeClient::registerEntityRenderers);
        modBus.addListener(EventPriority.NORMAL, false, RegisterParticleProvidersEvent.class,
                ForgeClient::registerParticleProviders);
    }

    private static void onClientSetup(FMLClientSetupEvent event) {
        ForcemasterClient.init();
    }

    private static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(NenSphereBeamEntity.ENTITY_TYPE, NenSphereBeamRenderer::new);
    }

    private static void registerParticleProviders(RegisterParticleProvidersEvent event) {
        ForcemasterClient.registerParticleAppearances((type, factory) ->
                event.registerSpriteSet(type, factory::apply));
    }
}
