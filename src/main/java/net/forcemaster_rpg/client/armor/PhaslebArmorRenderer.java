package net.forcemaster_rpg.client.armor;

import mod.azure.azurelibarmor.common.api.client.renderer.GeoArmorRenderer;
import net.forcemaster_rpg.item.armor.PhaslebArmor;

public class PhaslebArmorRenderer extends GeoArmorRenderer<PhaslebArmor> {
    public PhaslebArmorRenderer() {
        super(new PhaslebArmorModel());
    }
}
