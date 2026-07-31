package net.forcemaster_rpg.datagen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.util.Identifier;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static net.forcemaster_rpg.ForcemasterClassMod.MOD_ID;

public class ConditionalCraftingRecipes implements DataProvider {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    protected final FabricDataOutput output;
    private final List<ConditionalRecipeData> recipes = new ArrayList<>();

    public ConditionalCraftingRecipes(FabricDataOutput output) {
        this.output = output;
    }

    public void generate() {
        createShapedRecipe(
                "aeternium_knuckle",
                Identifier.of(MOD_ID, "aeternium_knuckle"),
                new String[]{"B  ", "BB ", "BB "},
                'B', "betterend:aeternium_ingot",
                "betterend"
        );
    }

    public void createShapedRecipe(
            String name,
            Identifier result,
            String[] pattern,
            char key,
            String ingredient,
            String requiredMod
    ) {
        recipes.add(new ConditionalRecipeData(
                name,
                "minecraft:crafting_shaped",
                result,
                pattern,
                key,
                ingredient,
                requiredMod
        ));
    }

    @Override
    public CompletableFuture<?> run(DataWriter writer) {
        generate();

        return CompletableFuture.allOf(recipes.stream().map(recipeData -> {
            JsonObject recipe = buildRecipeJson(recipeData);
            Path path = output.getResolver(net.minecraft.data.DataOutput.OutputType.DATA_PACK, "recipe")
                    .resolveJson(Identifier.of(MOD_ID, recipeData.name));

            return DataProvider.writeToPath(writer, recipe, path);
        }).toArray(CompletableFuture[]::new));
    }

    private JsonObject buildRecipeJson(ConditionalRecipeData data) {
        JsonObject recipe = new JsonObject();
        if (data.requiredMod != null && !data.requiredMod.isEmpty()) {
            JsonArray fabricLoadConditions = new JsonArray();
            JsonObject fabricCondition = new JsonObject();
            fabricCondition.addProperty("condition", "fabric:all_mods_loaded");
            JsonArray modValues = new JsonArray();
            modValues.add(data.requiredMod);
            fabricCondition.add("values", modValues);
            fabricLoadConditions.add(fabricCondition);
            recipe.add("fabric:load_conditions", fabricLoadConditions);

            JsonArray neoforgeConditions = new JsonArray();
            JsonObject neoforgeCondition = new JsonObject();
            neoforgeCondition.addProperty("type", "neoforge:mod_loaded");
            neoforgeCondition.addProperty("modid", data.requiredMod);
            neoforgeConditions.add(neoforgeCondition);
            recipe.add("neoforge:conditions", neoforgeConditions);
        }

        recipe.addProperty("type", data.type);

        if (data.pattern != null) {
            JsonArray patternArray = new JsonArray();
            for (String row : data.pattern) {
                patternArray.add(row);
            }
            recipe.add("pattern", patternArray);
        }

        JsonObject keyObj = new JsonObject();
        JsonObject ingredientObj = new JsonObject();
        ingredientObj.addProperty("item", data.ingredient);
        keyObj.add(String.valueOf(data.key), ingredientObj);
        recipe.add("key", keyObj);

        JsonObject resultObj = new JsonObject();
        resultObj.addProperty("id", data.result.toString());
        recipe.add("result", resultObj);

        return recipe;
    }

    @Override
    public String getName() {
        return "Conditional Crafting Recipes (" + MOD_ID + ")";
    }

    private record ConditionalRecipeData(
            String name,
            String type,
            Identifier result,
            String[] pattern,
            char key,
            String ingredient,
            String requiredMod
    ) {}
}
