package net.forcemaster_rpg.client.armor;

import mod.azure.azurelibarmor.common.api.client.renderer.GeoArmorRenderer;
import net.forcemaster_rpg.item.armor.AkenArmor;
import net.forcemaster_rpg.item.armor.OrieneArmor;

public class AkenArmorRenderer extends GeoArmorRenderer<AkenArmor> {
    public AkenArmorRenderer() {
        super(new AkenArmorModel());
    }
}
