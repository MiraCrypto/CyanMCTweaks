package io.github.miracrypto.mixin;

import io.github.miracrypto.CyanMCTweaks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "dropItem(Lnet/minecraft/item/ItemStack;ZZ)Lnet/minecraft/entity/ItemEntity;", at = @At("TAIL"))
    private void onDropItem(ItemStack stack, boolean throwRandomly, boolean retainOwnership, CallbackInfoReturnable<ItemEntity> cir) {
        if (!CyanMCTweaks.CONFIG.isDeathDropsGlowing()) {
            return;
        }

        if (throwRandomly) {
            ItemEntity entity = cir.getReturnValue();
            entity.setGlowing(true);
        }
    }

    @ModifyVariable(method = "applyDamage", at = @At("HEAD"), argsOnly = true, ordinal = 0)
    private float onApplyDamage(float amount, DamageSource source) {
        if (this.isInvulnerableTo(source)) {
            return amount;
        }

        if (source.getAttacker() instanceof WardenEntity) {
            return amount * CyanMCTweaks.CONFIG.getDamageScaleWarden();
        }
        if (source.getAttacker() instanceof PlayerEntity) {
            return amount * CyanMCTweaks.CONFIG.getDamageScalePlayer();
        }

        if (source.isFromFalling() || source.isFire() || source.isExplosive() || source == DamageSource.DROWN) {
            return amount * CyanMCTweaks.CONFIG.getDamageScaleEnvironment();
        }

        if (source.isScaledWithDifficulty()) {
            return amount * CyanMCTweaks.CONFIG.getDamageScaleMob();
        }

        return amount * CyanMCTweaks.CONFIG.getDamageScale();
    }


}
