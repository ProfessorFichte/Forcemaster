package net.forcemaster_rpg.mixin;

import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.render.model.BlockStatesLoader;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Map;

import static net.forcemaster_rpg.ForcemasterClassMod.MOD_ID;

@Mixin(ModelLoader.class)
public abstract class ModelLoaderMixin {
    @Shadow protected abstract void loadItemModel(ModelIdentifier id);
        @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/model/ModelLoader;loadItemModel(Lnet/minecraft/client/util/ModelIdentifier;)V", ordinal = 1, shift = At.Shift.AFTER))
        public void addItemModel(BlockColors blockColors, Profiler profiler, Map<Identifier, JsonUnbakedModel> jsonUnbakedModels, Map<Identifier, List<BlockStatesLoader.SourceTrackedData>> blockStates, CallbackInfo ci) {
            this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MOD_ID, "wooden_knuckle_model")));
            this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MOD_ID, "stone_knuckle_model")));
            this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MOD_ID, "golden_knuckle_model")));
            this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MOD_ID, "iron_knuckle_model")));
            this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MOD_ID, "diamond_knuckle_model")));
            this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MOD_ID, "netherite_knuckle_model")));
            this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MOD_ID, "guardian_knuckle_model")));
            this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MOD_ID, "legendary_golden_knuckle_model")));
            this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MOD_ID, "bloody_knuckle_model")));
            this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MOD_ID, "ruby_knuckle_model")));
            this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MOD_ID, "aeternium_knuckle_model")));
            this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MOD_ID, "aether_knuckle_model")));
            this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MOD_ID, "wither_knuckle_model")));
            this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MOD_ID, "glacial_knuckle_model")));
            this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MOD_ID, "elder_guardian_knuckle_model")));
            this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MOD_ID, "ender_dragon_knuckle_model")));
        }
}
