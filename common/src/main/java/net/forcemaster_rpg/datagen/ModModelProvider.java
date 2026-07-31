package net.forcemaster_rpg.datagen;

import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.forcemaster_rpg.item.armor.Armors;
import net.forcemaster_rpg.item.weapons.WeaponsRegister;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import static net.forcemaster_rpg.ForcemasterClassMod.MOD_ID;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        // No block models needed
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        Armors.entries.forEach(entry -> {
            for (var piece: entry.armorSet().pieces()) {
                itemModelGenerator.register((Item) piece, Models.GENERATED);
            }
        });
        for (var entry : WeaponsRegister.entries) {
            Item item = entry.item();
            if (item == null) continue;

            Identifier itemId = Registries.ITEM.getId(item);
            String name = itemId.getPath();
            generateInventoryModel(itemModelGenerator, itemId, name);
            if (name.contains("wooden") || name.contains("stone") || name.contains("golden") || name.contains("iron")
                    || name.contains("diamond") || name.contains("netherite")) {
                generateOverworldModel(itemModelGenerator, itemId, name);
            }
        }
    }

    private void generateInventoryModel(ItemModelGenerator gen, Identifier itemId, String name) {
        Identifier modelId = Identifier.of(itemId.getNamespace(), "item/" + name);

        JsonObject json = new JsonObject();
        json.addProperty("parent", "minecraft:item/generated");

        JsonObject textures = new JsonObject();
        textures.addProperty("layer0", MOD_ID + ":item/" + name + "_inventory");
        json.add("textures", textures);

        gen.writer.accept(modelId, () -> json);
    }

    private void generateOverworldModel(ItemModelGenerator gen, Identifier itemId, String name) {
        Identifier modelId = Identifier.of(itemId.getNamespace(), "item/" + name + "_model");

        JsonObject json = new JsonObject();
        json.addProperty("parent", MOD_ID + ":item/knuckle_model");

        JsonObject textures = new JsonObject();
        textures.addProperty("2", MOD_ID + ":item/" + name + "_texture");
        textures.addProperty("particle", MOD_ID + ":item/" + name + "_texture");
        json.add("textures", textures);

        gen.writer.accept(modelId, () -> json);
    }
}
