package io.github.miracrypto.mixin;

import io.github.miracrypto.CyanMCTweaks;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.feature.OreFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(OreFeature.class)
public class OreFeatureMixin {
    @Inject(method = "shouldNotDiscard", at = @At("HEAD"), cancellable = true)
    private static void onShouldNotDiscard(Random random, float chance, CallbackInfoReturnable<Boolean> cir) {
        if (CyanMCTweaks.CONFIG.isIgnoreAirExposureChecks()) {
            cir.setReturnValue(true);
        }
    }
}
