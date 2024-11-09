package net.forcemaster_rpg.mixin;

import net.spell_engine.api.item.Tiers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Tiers.class)
public class SpellEngineTiersMixin {

    @Inject(method = "unsafe(Ljava/lang/String;)I", at = @At("HEAD"), cancellable = true,remap=false)
            private static void forcemaster_tiers(String name, CallbackInfoReturnable<Integer> cir){
            if (name.contains("bloody")
                    || name.contains("guardian")
                    || name.contains("aken")
                    || name.contains("legendary_golden")) {
                cir.setReturnValue(4);
            }
    }


}
