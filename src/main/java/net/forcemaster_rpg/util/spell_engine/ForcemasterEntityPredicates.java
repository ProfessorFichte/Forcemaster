package net.forcemaster_rpg.util.spell_engine;

import net.forcemaster_rpg.ForcemasterClassMod;
import net.forcemaster_rpg.item.tag.ModItemTags;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class ForcemasterEntityPredicates {
    public record Input(Entity entity, Entity other, @Nullable String param) { }
    public record Entry(Identifier id, Predicate<Input> predicate) { }

    private static final Map<String, Entry> entries = new HashMap<>();
    public static Entry register(Identifier id, Predicate<Input> predicate) {
        var entry = new Entry(id, predicate);
        entries.put(id.toString(), entry);
        return entry;
    }

    public static Entry HAS_FIST_WEAPON_EQUIPED = register(Identifier.of(ForcemasterClassMod.MOD_ID, "has_fist_weapon_equiped"), args -> {
        if (args.entity instanceof LivingEntity livingEntity) {
            ItemStack stack = livingEntity.getEquippedStack(EquipmentSlot.MAINHAND);
            return stack.isIn(ModItemTags.FIST_WEAPON);
        }
        return false;
    });
}
