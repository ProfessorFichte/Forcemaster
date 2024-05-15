package net.forcemaster_rpg.item.weapons;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.ToolMaterial;
import net.more_rpg_classes.entity.attribute.MRPGCEntityAttributes;

import java.util.UUID;

public class BloodyKnuckleItem extends KnuckleItem{
    public BloodyKnuckleItem(ToolMaterial material, Settings settings) {
        super(material, settings);
    }

    private Multimap<EntityAttribute, EntityAttributeModifier> attributes;
    public void setAttributes(Multimap<EntityAttribute, EntityAttributeModifier> attributes) {
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        builder.putAll(attributes);
        builder.put(MRPGCEntityAttributes.LIFESTEAL_MODIFIER,new EntityAttributeModifier(UUID.fromString("8816f6a7-2ef7-4a2d-bff3-80b133cb4919"),
                "lifesteal",0.1, EntityAttributeModifier.Operation.MULTIPLY_BASE));
        builder.put(MRPGCEntityAttributes.ARCANE_FUSE_MODIFIER,new EntityAttributeModifier(UUID.fromString("8816f6a7-2ef7-4a2d-bff3-80b133cb4919"),
                "arcane_fuse",0.05, EntityAttributeModifier.Operation.MULTIPLY_BASE));


        this.attributes = builder.build();
    }
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {

        if (this.attributes == null) {
            return super.getAttributeModifiers(slot);
        } else {
            return slot == EquipmentSlot.MAINHAND ? this.attributes : super.getAttributeModifiers(slot);
        }
    }
}
