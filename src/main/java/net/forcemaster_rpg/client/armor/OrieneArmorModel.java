package net.forcemaster_rpg.client.armor;

import mod.azure.azurelibarmor.common.api.client.model.GeoModel;
import net.minecraft.util.Identifier;
import net.forcemaster_rpg.ForcemasterClassMod;
import net.forcemaster_rpg.item.armor.OrieneArmor;

public class OrieneArmorModel extends GeoModel<OrieneArmor> {
    @Override
    public Identifier getModelResource(OrieneArmor object) {
        return Identifier.of(ForcemasterClassMod.MOD_ID, "geo/oriene_armor.geo.json");
    }

    @Override
    public Identifier getTextureResource(OrieneArmor armor) {
        return Identifier.of(ForcemasterClassMod.MOD_ID, "textures/armor/oriene_armor.png");
    }

    @Override
    public Identifier getAnimationResource(OrieneArmor animatable) {
        return null;
    }
}