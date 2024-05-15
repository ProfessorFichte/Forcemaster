package net.forcemaster_rpg.client.armor;

import mod.azure.azurelibarmor.model.GeoModel;
import net.minecraft.util.Identifier;
import net.forcemaster_rpg.ForcemasterClassMod;
import net.forcemaster_rpg.item.armor.PhaslebArmor;

public class PhaslebArmorModel extends GeoModel<PhaslebArmor> {
    @Override
    public Identifier getModelResource(PhaslebArmor object) {
        return new Identifier(ForcemasterClassMod.MOD_ID, "geo/phasleb_armor.geo.json");
    }

    @Override
    public Identifier getTextureResource(PhaslebArmor armor) {
        return new Identifier(ForcemasterClassMod.MOD_ID, "textures/armor/phasleb_armor.png");
    }

    @Override
    public Identifier getAnimationResource(PhaslebArmor animatable) {
        return null;
    }

}
