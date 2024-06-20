package net.forcemaster_rpg.mixin;

import net.forcemaster_rpg.ForcemasterClassMod;
import net.forcemaster_rpg.item.weapons.WeaponsRegister;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {
    @ModifyVariable(method = "renderItem", at = @At(value = "HEAD"), argsOnly = true)
    public BakedModel useKnuckleModel(BakedModel value, ItemStack stack, ModelTransformationMode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (stack.isOf(WeaponsRegister.wooden_knuckle.item()) && renderMode != ModelTransformationMode.GUI) {
            return ((ItemRendererAccessor) this).forcemaster$getModels().getModelManager().getModel(new ModelIdentifier(ForcemasterClassMod.MOD_ID, "wooden_knuckle_model", "inventory"));
        }
        else if (stack.isOf(WeaponsRegister.stone_knuckle.item()) && renderMode != ModelTransformationMode.GUI) {
            return ((ItemRendererAccessor) this).forcemaster$getModels().getModelManager().getModel(new ModelIdentifier(ForcemasterClassMod.MOD_ID, "stone_knuckle_model", "inventory"));
        }
        else if (stack.isOf(WeaponsRegister.golden_knuckle.item()) && renderMode != ModelTransformationMode.GUI) {
            return ((ItemRendererAccessor) this).forcemaster$getModels().getModelManager().getModel(new ModelIdentifier(ForcemasterClassMod.MOD_ID, "golden_knuckle_model", "inventory"));
        }
        else if (stack.isOf(WeaponsRegister.iron_knuckle.item()) && renderMode != ModelTransformationMode.GUI) {
            return ((ItemRendererAccessor) this).forcemaster$getModels().getModelManager().getModel(new ModelIdentifier(ForcemasterClassMod.MOD_ID, "iron_knuckle_model", "inventory"));
        }
        else if (stack.isOf(WeaponsRegister.diamond_knuckle.item()) && renderMode != ModelTransformationMode.GUI) {
            return ((ItemRendererAccessor) this).forcemaster$getModels().getModelManager().getModel(new ModelIdentifier(ForcemasterClassMod.MOD_ID, "diamond_knuckle_model", "inventory"));
        }
        else if (stack.isOf(WeaponsRegister.netherite_knuckle.item()) && renderMode != ModelTransformationMode.GUI) {
            return ((ItemRendererAccessor) this).forcemaster$getModels().getModelManager().getModel(new ModelIdentifier(ForcemasterClassMod.MOD_ID, "netherite_knuckle_model", "inventory"));
        }
        else if (stack.isOf(WeaponsRegister.bloody_knuckle.item()) && renderMode != ModelTransformationMode.GUI) {
            return ((ItemRendererAccessor) this).forcemaster$getModels().getModelManager().getModel(new ModelIdentifier(ForcemasterClassMod.MOD_ID, "bloody_knuckle_model", "inventory"));
        }
        else if (stack.isOf(WeaponsRegister.guardian_knuckle.item()) && renderMode != ModelTransformationMode.GUI) {
            return ((ItemRendererAccessor) this).forcemaster$getModels().getModelManager().getModel(new ModelIdentifier(ForcemasterClassMod.MOD_ID, "guardian_knuckle_model", "inventory"));
        }
        else if (stack.isOf(WeaponsRegister.legendary_golden_knuckle.item()) && renderMode != ModelTransformationMode.GUI) {
            return ((ItemRendererAccessor) this).forcemaster$getModels().getModelManager().getModel(new ModelIdentifier(ForcemasterClassMod.MOD_ID, "legendary_golden_knuckle_model", "inventory"));
        }
        return value;
    }
}