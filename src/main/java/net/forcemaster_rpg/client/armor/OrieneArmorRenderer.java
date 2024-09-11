package net.forcemaster_rpg.client.armor;

import mod.azure.azurelibarmor.common.api.client.renderer.GeoArmorRenderer;
import net.forcemaster_rpg.item.armor.OrieneArmor;

public class OrieneArmorRenderer extends GeoArmorRenderer<OrieneArmor> {
    public OrieneArmorRenderer() {
        super(new OrieneArmorModel());
    }
}
