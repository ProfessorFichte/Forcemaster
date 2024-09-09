package net.forcemaster_rpg.compat;

import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;

import static net.forcemaster_rpg.ForcemasterClassMod.MOD_ID;


public class CompatDatapackLoader {
    public static void register(){
        if(FabricLoader.getInstance().isModLoaded("betterend")) {
            ResourceManagerHelper.registerBuiltinResourcePack(new Identifier("betterend_forcemaster"),
                    FabricLoader.getInstance().getModContainer(MOD_ID).orElseThrow(), ResourcePackActivationType.ALWAYS_ENABLED);
        }
        if(FabricLoader.getInstance().isModLoaded("betternether")) {
            ResourceManagerHelper.registerBuiltinResourcePack(new Identifier("betternether_forcemaster"),
                    FabricLoader.getInstance().getModContainer(MOD_ID).orElseThrow(), ResourcePackActivationType.ALWAYS_ENABLED);
        }
    }
}
