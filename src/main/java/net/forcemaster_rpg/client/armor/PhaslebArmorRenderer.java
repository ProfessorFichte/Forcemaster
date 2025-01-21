package net.forcemaster_rpg.client.armor;

import mod.azure.azurelibarmor.rewrite.render.armor.AzArmorRenderer;
import mod.azure.azurelibarmor.rewrite.render.armor.AzArmorRendererConfig;
import net.minecraft.util.Identifier;

import static net.forcemaster_rpg.ForcemasterClassMod.MOD_ID;

public class PhaslebArmorRenderer extends AzArmorRenderer {
    public static PhaslebArmorRenderer phasleb() {
        return new PhaslebArmorRenderer("phasleb_armor", "phasleb_armor");
    }

    public PhaslebArmorRenderer(String modelName, String textureName) {
        super(AzArmorRendererConfig.builder(
                Identifier.of(MOD_ID, "geo/" + modelName + ".geo.json"),
                Identifier.of(MOD_ID, "textures/armor/" + textureName + ".png")
        ).build());
    }
}
