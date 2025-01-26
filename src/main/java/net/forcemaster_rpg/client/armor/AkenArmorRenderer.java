package net.forcemaster_rpg.client.armor;

import mod.azure.azurelibarmor.renderer.GeoArmorRenderer;
import net.forcemaster_rpg.item.armor.AkenArmor;

public class AkenArmorRenderer extends GeoArmorRenderer<AkenArmor> {
    public AkenArmorRenderer() {
        super(new AkenArmorModel());
    }
}
