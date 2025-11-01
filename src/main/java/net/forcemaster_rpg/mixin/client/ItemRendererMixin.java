package net.forcemaster_rpg.mixin.client;

import net.forcemaster_rpg.util.ForcemasterTags;
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

import static net.forcemaster_rpg.ForcemasterClassMod.MOD_ID;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {
    @ModifyVariable(method = "renderItem", at = @At(value = "HEAD"), argsOnly = true)
    public BakedModel useKnuckleModel(BakedModel value, ItemStack stack, ModelTransformationMode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if(stack.isIn(ForcemasterTags.KNUCKLES) && renderMode != ModelTransformationMode.GUI && renderMode != ModelTransformationMode.GROUND) {
            String name = stack.getTranslationKey();
            String name2 = name.toString().replace("item.forcemaster_rpg.","");
            return ((ItemRendererAccessor)this).forcemaster$getModels().getModelManager().getModel(new ModelIdentifier(MOD_ID, name2 + "_model","inventory"));
        }
        return value;
    }
}