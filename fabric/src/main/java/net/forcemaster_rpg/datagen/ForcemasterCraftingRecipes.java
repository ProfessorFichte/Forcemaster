package net.forcemaster_rpg.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.forcemaster_rpg.item.armor.Armors;
import net.forcemaster_rpg.item.weapons.WeaponsRegister;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

import static net.forcemaster_rpg.ForcemasterClassMod.MOD_ID;

public class ForcemasterCraftingRecipes extends FabricRecipeProvider {

    public ForcemasterCraftingRecipes(FabricDataOutput output) {
        super(output);
    }

    private static Item getOrFallback(Identifier id, Item fallback) {
        var item = Registries.ITEM.get(id);
        return item != null && item != Items.AIR ? item : fallback;
    }

    @Override
    public String getName() {
        return "Crafting Recipes (" + MOD_ID + ")";
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, WeaponsRegister.wooden_knuckle.item())
                .pattern("W  ")
                .pattern("WW ")
                .pattern("WW ")
                .input('W', ItemTags.LOGS)
                .criterion(hasItem(Items.OAK_LOG), conditionsFromItem(Items.OAK_LOG))
                .offerTo(exporter, new Identifier(MOD_ID, "wooden_knuckle"));
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, WeaponsRegister.stone_knuckle.item())
                .pattern("S  ")
                .pattern("SS ")
                .pattern("SS ")
                .input('S', ItemTags.STONE_TOOL_MATERIALS)
                .criterion(hasItem(Items.COBBLESTONE), conditionsFromItem(Items.COBBLESTONE))
                .offerTo(exporter, new Identifier(MOD_ID, "stone_knuckle"));
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, WeaponsRegister.iron_knuckle.item())
                .pattern("I  ")
                .pattern("IG ")
                .pattern("II ")
                .input('I', Items.IRON_INGOT)
                .input('G', Items.GOLD_INGOT)
                .criterion(hasItem(Items.IRON_INGOT), conditionsFromItem(Items.IRON_INGOT))
                .offerTo(exporter, new Identifier(MOD_ID, "iron_knuckle"));
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, WeaponsRegister.golden_knuckle.item())
                .pattern("G  ")
                .pattern("GG ")
                .pattern("GG ")
                .input('G', Items.GOLD_INGOT)
                .criterion(hasItem(Items.GOLD_INGOT), conditionsFromItem(Items.GOLD_INGOT))
                .offerTo(exporter, new Identifier(MOD_ID, "golden_knuckle"));
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, WeaponsRegister.diamond_knuckle.item())
                .pattern("T  ")
                .pattern("RW ")
                .pattern("RR ")
                .input('T', Items.GOLD_INGOT)
                .input('W', Items.AMETHYST_SHARD)
                .input('R', Items.DIAMOND)
                .criterion(hasItem(Items.DIAMOND), conditionsFromItem(Items.DIAMOND))
                .offerTo(exporter, new Identifier(MOD_ID, "diamond_knuckle"));

        createConditionalShapedRecipe(exporter, "aeternium_knuckle",
                RecipeCategory.COMBAT,
                "forcemaster_rpg:aeternium_knuckle",
                new String[]{"B  ", "BB ", "BB "},
                'B', "betterend:aeternium_ingot",
                "betterend");

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, (Item) Armors.orieneArmorSet.armorSet().head)
                .pattern("RCR")
                .pattern("R R")
                .input('R', Items.LEATHER)
                .input('C', Items.COBBLESTONE)
                .criterion(hasItem(Items.LEATHER), conditionsFromItem(Items.LEATHER))
                .offerTo(exporter, new Identifier(MOD_ID, "oriene_head"));
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, (Item) Armors.orieneArmorSet.armorSet().chest)
                .pattern("C C")
                .pattern("RRR")
                .pattern("WWW")
                .input('W', ItemTags.WOOL)
                .input('R', Items.LEATHER)
                .input('C', Items.COBBLESTONE)
                .criterion(hasItem(Items.LEATHER), conditionsFromItem(Items.LEATHER))
                .offerTo(exporter, new Identifier(MOD_ID, "oriene_chest"));
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, (Item) Armors.orieneArmorSet.armorSet().legs)
                .pattern("CRC")
                .pattern("R R")
                .pattern("W W")
                .input('W', ItemTags.WOOL)
                .input('R', Items.LEATHER)
                .input('C', Items.COBBLESTONE)
                .criterion(hasItem(Items.LEATHER), conditionsFromItem(Items.LEATHER))
                .offerTo(exporter, new Identifier(MOD_ID, "oriene_legs"));
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, (Item) Armors.orieneArmorSet.armorSet().feet)
                .pattern("W W")
                .pattern("R R")
                .input('W', Items.LEATHER)
                .input('R', Items.COBBLESTONE)
                .criterion(hasItem(Items.LEATHER), conditionsFromItem(Items.LEATHER))
                .offerTo(exporter, new Identifier(MOD_ID, "oriene_feet"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, (Item) Armors.phaslebArmorSet.armorSet().head)
                .pattern("RRR")
                .pattern("WTW")
                .input('W', Items.GOLD_INGOT)
                .input('R', Items.LEATHER)
                .input('T', Items.AMETHYST_SHARD)
                .criterion(hasItem(Items.LEATHER), conditionsFromItem(Items.LEATHER))
                .offerTo(exporter, new Identifier(MOD_ID, "phasleb_head"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, (Item) Armors.phaslebArmorSet.armorSet().chest)
                .pattern("R R")
                .pattern("WTW")
                .pattern("RRR")
                .input('W', Items.GOLD_INGOT)
                .input('R', Items.LEATHER)
                .input('T', Items.AMETHYST_SHARD)
                .criterion(hasItem(Items.LEATHER), conditionsFromItem(Items.LEATHER))
                .offerTo(exporter, new Identifier(MOD_ID, "phasleb_chest"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, (Item) Armors.phaslebArmorSet.armorSet().legs)
                .pattern("WTW")
                .pattern("R R")
                .pattern("W W")
                .input('W', Items.GOLD_INGOT)
                .input('R', Items.LEATHER)
                .input('T', Items.AMETHYST_SHARD)
                .criterion(hasItem(Items.LEATHER), conditionsFromItem(Items.LEATHER))
                .offerTo(exporter, new Identifier(MOD_ID, "phasleb_legs"));
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, (Item) Armors.phaslebArmorSet.armorSet().feet)
                .pattern("W W")
                .pattern("R R")
                .input('W', Items.GOLD_INGOT)
                .input('R', Items.LEATHER)
                .criterion(hasItem(Items.LEATHER), conditionsFromItem(Items.LEATHER))
                .offerTo(exporter, new Identifier(MOD_ID, "phasleb_feet"));

        var forcemasterBook = getOrFallback(new Identifier(MOD_ID, "forcemaster_spell_book"), Items.WRITTEN_BOOK);
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, forcemasterBook)
                .input(Items.LEATHER)
                .input(Items.AMETHYST_SHARD)
                .input(Items.BOOK)
                .input(Items.LAPIS_LAZULI)
                .criterion(hasItem(Items.BOOK), conditionsFromItem(Items.BOOK))
                .offerTo(exporter);
    }

    private void createConditionalShapedRecipe(Consumer<RecipeJsonProvider> exporter, String name, RecipeCategory category,
                                               String resultId, String[] pattern,
                                               char key, String ingredientId, String requiredMod) {
    }
}
