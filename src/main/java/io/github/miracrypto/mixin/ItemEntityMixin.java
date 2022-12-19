package io.github.miracrypto.mixin;

import io.github.miracrypto.CyanMCTweaks;
import net.minecraft.entity.ItemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public class ItemEntityMixin {
    @Shadow private int itemAge;

    @ModifyConstant(method = "tick", constant = @Constant(intValue = 6000))
    private int itemDespawnAge(int value) {
        return getDespawnAge();
    }

    @Inject(method = "setDespawnImmediately", at = @At("TAIL"))
    private void onSetDespawnImmediately(CallbackInfo ci) {
        this.itemAge = getDespawnAge() - 1;
    }

    private int getDespawnAge() {
        int despawnAge = CyanMCTweaks.CONFIG.getItemEntityDespawnAge();
        if (despawnAge != 0) {
            return despawnAge;
        }
        return 6000;
    }
}
