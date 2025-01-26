package net.forcemaster_rpg.client.armor;

import mod.azure.azurelibarmor.model.GeoModel;
import net.forcemaster_rpg.ForcemasterClassMod;
import net.forcemaster_rpg.item.armor.AkenArmor;
import net.minecraft.util.Identifier;

public class AkenArmorModel extends GeoModel<AkenArmor> {
    @Override
    public Identifier getModelResource(AkenArmor object) {
        return new Identifier(ForcemasterClassMod.MOD_ID, "geo/aken_armor.geo.json");
    }

    @Override
    public Identifier getTextureResource(AkenArmor armor) {
        return new Identifier(ForcemasterClassMod.MOD_ID, "textures/armor/aken_armor.png");
    }

    @Override
    public Identifier getAnimationResource(AkenArmor animatable) {
        return null;
    }

}
