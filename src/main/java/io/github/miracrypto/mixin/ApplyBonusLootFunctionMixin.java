package io.github.miracrypto.mixin;

import io.github.miracrypto.CyanMCTweaks;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ApplyBonusLootFunction.class)
public abstract class ApplyBonusLootFunctionMixin {

    @Redirect(method = "process(Lnet/minecraft/item/ItemStack;Lnet/minecraft/loot/context/LootContext;)Lnet/minecraft/item/ItemStack;", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;getLevel(Lnet/minecraft/enchantment/Enchantment;Lnet/minecraft/item/ItemStack;)I"))
    private int onGetLevel(Enchantment enchantment, ItemStack stack) {
        int level = EnchantmentHelper.getLevel(enchantment, stack);
        if (enchantment == Enchantments.FORTUNE) {
            level += CyanMCTweaks.CONFIG.getFortuneLevelBonus();
        }
        return level;
    }
}
