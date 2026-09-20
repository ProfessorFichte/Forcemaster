package net.forcemaster_rpg.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.forcemaster_rpg.item.armor.Armors;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static net.forcemaster_rpg.ForcemasterClassMod.MOD_ID;

public class ForcemasterVanillaAdvancementProvider extends FabricAdvancementProvider {

    public record Entry(
            Identifier id,
            String title,
            String description,
            @Nullable Identifier parent,
            Item iconItem,
            AdvancementFrame frame,
            boolean showToast,
            boolean announceToChat,
            boolean hidden,
            @Nullable String background,
            Item[] requiredItems,
            @Nullable Integer experienceReward
    ) {
        public String titleKey() {
            return "advancements." + id.getNamespace() + "." + id.getPath().replace("/", ".") + ".title";
        }

        public String descriptionKey() {
            return "advancements." + id.getNamespace() + "." + id.getPath().replace("/", ".") + ".description";
        }
    }

    public static final List<Entry> entries = new ArrayList<>();

    private static Entry addEntry(Entry entry) {
        entries.add(entry);
        return entry;
    }

    private static Identifier id(String path) {
        return new Identifier(MOD_ID, path);
    }

    public static void init() {
        var phasleb = Armors.phaslebArmorSet.armorSet();
        addEntry(new Entry(
                id("equipment/phasleb_armor_set"),
                "Master of the Force!",
                "Obtain the full Phasleb Armor Set",
                new Identifier("more_rpg_content", "root"),
                (Item) phasleb.chest,
                AdvancementFrame.GOAL,
                true, true, false, null,
                new Item[]{
                        (Item) phasleb.head,
                        (Item) phasleb.chest,
                        (Item) phasleb.legs,
                        (Item) phasleb.feet
                },
                null
        ));
    }

    public ForcemasterVanillaAdvancementProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateAdvancement(Consumer<Advancement> consumer) {
        for (Entry entry : entries) {
            generateAdvancementEntry(entry, consumer);
        }
    }

    private static Advancement parentStub(Identifier parentId) {
        return new Advancement(parentId, null, null, AdvancementRewards.NONE, Map.of(), new String[0][], false);
    }

    private void generateAdvancementEntry(Entry entry, Consumer<Advancement> consumer) {
        Item iconItem = entry.iconItem() != null ? entry.iconItem() : Items.BARRIER;

        var builder = Advancement.Builder.create()
                .display(
                        iconItem,
                        Text.translatable(entry.titleKey()),
                        Text.translatable(entry.descriptionKey()),
                        entry.background() != null ? Identifier.tryParse(entry.background()) : null,
                        entry.frame(),
                        entry.showToast(),
                        entry.announceToChat(),
                        entry.hidden()
                );

        builder.criterion("has_all_items", InventoryChangedCriterion.Conditions.items(entry.requiredItems()));

        if (entry.parent() != null) {
            builder = builder.parent(parentStub(entry.parent()));
        }

        if (entry.experienceReward() != null) {
            builder.rewards(AdvancementRewards.Builder.experience(entry.experienceReward()));
        }

        builder.build(consumer, entry.id().toString());
    }

    public static List<Entry> getEntries() {
        return entries;
    }
}
