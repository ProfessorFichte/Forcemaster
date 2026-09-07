package com.forcemaster.neoforge.client;

import net.forcemaster_rpg.ForcemasterClassMod;
import net.forcemaster_rpg.client.ForcemasterClient;
import net.forcemaster_rpg.client.entity.NenSphereBeamRenderer;
import net.forcemaster_rpg.entity.NenSphereBeamEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

@EventBusSubscriber(modid = ForcemasterClassMod.MOD_ID, value = Dist.CLIENT)
public class NeoForgeClient {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        ForcemasterClient.init();
    }
    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(NenSphereBeamEntity.ENTITY_TYPE, NenSphereBeamRenderer::new);
    }
    @SubscribeEvent
    public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
        ForcemasterClient.registerParticleAppearances((type, factory) ->
                event.registerSpriteSet(type, factory::apply));
    }
}
