package net.forcemaster_rpg.item.armor;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import mod.azure.azurelibarmor.animatable.GeoItem;
import mod.azure.azurelibarmor.animatable.client.RenderProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.ItemStack;
import net.forcemaster_rpg.client.armor.OrieneArmorRenderer;
import net.minecraft.util.Util;
import net.more_rpg_classes.entity.attribute.MRPGCEntityAttributes;
import net.spell_engine.api.item.armor.Armor;
import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;
import java.util.UUID;
import java.util.function.Consumer;

public class OrieneArmor extends ModArmorItem implements GeoItem {
    public OrieneArmor(Armor.CustomMaterial material, Type type, Settings settings) {
        super(material, type, settings);
    }

    @Override
    public void createRenderer(Consumer<Object> consumer) {
        consumer.accept(new RenderProvider() {
            private OrieneArmorRenderer renderer;

            @Override
            public @NotNull BipedEntityModel<LivingEntity> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, BipedEntityModel<LivingEntity> original) {
                if (renderer == null)
                    renderer = new OrieneArmorRenderer();

                renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);
                return renderer;
            }
        });
    }

    private Multimap<EntityAttribute, EntityAttributeModifier> attributes;
    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        if (attributes == null) {
            return super.getAttributeModifiers(slot);
        }
        return slot == this.type.getEquipmentSlot() ? this.attributes : super.getAttributeModifiers(slot);
    }
    private static final EnumMap MODIFIERS = (EnumMap) Util.make(new EnumMap(Type.class), (uuidMap) -> {
        uuidMap.put(Type.BOOTS, UUID.fromString("decdf170-2086-4e71-a9ff-df0980273a4c"));
        uuidMap.put(Type.LEGGINGS, UUID.fromString("d9a74547-786d-46f7-804c-b095277daa1a"));
        uuidMap.put(Type.CHESTPLATE, UUID.fromString("acda9a32-27fc-48a9-814b-8d2dfd542c06"));
        uuidMap.put(Type.HELMET, UUID.fromString("d1864a1f-dd84-4cd7-9fb2-75011f7e72e9"));
    });

    @Override
    public void setAttributes(Multimap<EntityAttribute, EntityAttributeModifier> attributes) {
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        builder.putAll(attributes);
        UUID uuid = (UUID)MODIFIERS.get(this.type);
        builder.put(MRPGCEntityAttributes.ARCANE_FUSE_MODIFIER,new EntityAttributeModifier(uuid,"arcane_fuse",0.025, EntityAttributeModifier.Operation.MULTIPLY_BASE));
        this.attributes = builder.build();
    }
}
