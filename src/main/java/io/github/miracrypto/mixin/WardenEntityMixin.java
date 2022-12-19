package io.github.miracrypto.mixin;

import io.github.miracrypto.CyanMCTweaks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WardenEntity.class)
public class WardenEntityMixin {
    @Inject(method = "addDarknessToClosePlayers", at = @At("HEAD"), cancellable = true)
    private static void onAddDarknessToClosePlayers(ServerWorld world, Vec3d pos, Entity entity, int range, CallbackInfo ci) {
        int wardenDarknessDuration = CyanMCTweaks.CONFIG.getWardenDarknessDuration();
        if (wardenDarknessDuration != 0) {
            StatusEffectInstance statusEffectInstance = new StatusEffectInstance(StatusEffects.DARKNESS, wardenDarknessDuration, 0, false, false);
            StatusEffectUtil.addEffectToPlayersWithinDistance(world, entity, pos, range, statusEffectInstance, 200);
            ci.cancel();
        }
    }
}
