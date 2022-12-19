package io.github.miracrypto.mixin;

import io.github.miracrypto.CyanMCTweaks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.loot.function.LootingEnchantLootFunction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LootingEnchantLootFunction.class)
public class LootingEnchantLootFunctionMixin {
	@Redirect(method = "process(Lnet/minecraft/item/ItemStack;Lnet/minecraft/loot/context/LootContext;)Lnet/minecraft/item/ItemStack;", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;getLooting(Lnet/minecraft/entity/LivingEntity;)I"))
	private int onGetLooting(LivingEntity entity) {
		return EnchantmentHelper.getLooting(entity) + CyanMCTweaks.CONFIG.getLootingLevelBonus();
	}
}
