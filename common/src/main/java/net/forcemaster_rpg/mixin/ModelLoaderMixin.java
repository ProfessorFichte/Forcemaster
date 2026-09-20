package net.forcemaster_rpg.mixin;

import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


import static net.forcemaster_rpg.ForcemasterClassMod.MOD_ID;

@Mixin(ModelLoader.class)
public abstract class ModelLoaderMixin {
    @Shadow protected abstract void addModel(ModelIdentifier id);
        @Unique
        private boolean forcemasterModelsAdded;

        @Inject(method = "addModel", at = @At("TAIL"))
        private void addItemModel(ModelIdentifier id, CallbackInfo ci) {
            if (this.forcemasterModelsAdded || !ModelLoader.MISSING_ID.equals(id)) {
                return;
            }
            this.forcemasterModelsAdded = true;
            this.addModel(new ModelIdentifier(new Identifier(MOD_ID, "wooden_knuckle_model"), "inventory"));
            this.addModel(new ModelIdentifier(new Identifier(MOD_ID, "stone_knuckle_model"), "inventory"));
            this.addModel(new ModelIdentifier(new Identifier(MOD_ID, "golden_knuckle_model"), "inventory"));
            this.addModel(new ModelIdentifier(new Identifier(MOD_ID, "iron_knuckle_model"), "inventory"));
            this.addModel(new ModelIdentifier(new Identifier(MOD_ID, "diamond_knuckle_model"), "inventory"));
            this.addModel(new ModelIdentifier(new Identifier(MOD_ID, "netherite_knuckle_model"), "inventory"));
            this.addModel(new ModelIdentifier(new Identifier(MOD_ID, "guardian_knuckle_model"), "inventory"));
            this.addModel(new ModelIdentifier(new Identifier(MOD_ID, "legendary_golden_knuckle_model"), "inventory"));
            this.addModel(new ModelIdentifier(new Identifier(MOD_ID, "bloody_knuckle_model"), "inventory"));
            this.addModel(new ModelIdentifier(new Identifier(MOD_ID, "ruby_knuckle_model"), "inventory"));
            this.addModel(new ModelIdentifier(new Identifier(MOD_ID, "aeternium_knuckle_model"), "inventory"));
            this.addModel(new ModelIdentifier(new Identifier(MOD_ID, "aether_knuckle_model"), "inventory"));
            this.addModel(new ModelIdentifier(new Identifier(MOD_ID, "wither_knuckle_model"), "inventory"));
            this.addModel(new ModelIdentifier(new Identifier(MOD_ID, "glacial_knuckle_model"), "inventory"));
            this.addModel(new ModelIdentifier(new Identifier(MOD_ID, "elder_guardian_knuckle_model"), "inventory"));
            this.addModel(new ModelIdentifier(new Identifier(MOD_ID, "ender_dragon_knuckle_model"), "inventory"));
            this.addModel(new ModelIdentifier(new Identifier(MOD_ID, "unique_knuckle_0_model"), "inventory"));
            this.addModel(new ModelIdentifier(new Identifier(MOD_ID, "unique_knuckle_1_model"), "inventory"));
        }
}
