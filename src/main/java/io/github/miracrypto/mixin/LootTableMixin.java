package io.github.miracrypto.mixin;

import io.github.miracrypto.CyanMCTweaks;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(LootTable.class)
public abstract class LootTableMixin {
    @Shadow public abstract ObjectArrayList<ItemStack> generateLoot(LootContext context);

    @Shadow protected abstract List<Integer> getFreeSlots(Inventory inventory, Random random);

    @Shadow protected abstract void shuffle(ObjectArrayList<ItemStack> drops, int freeSlots, Random random);

    @Inject(method = "supplyInventory", at = @At("HEAD"), cancellable = true)
    private void onSupplyInventory(Inventory inventory, LootContext context, CallbackInfo ci) {
        int extraRolls = CyanMCTweaks.CONFIG.getLootChestExtraRolls();
        if (extraRolls <= 0) {
            return;
        }

        ci.cancel();

        Random random = context.getRandom();
        List<Integer> freeSlotsList = null;
        for (int i = 0; i < 1 + extraRolls; i++) {
            ObjectArrayList<ItemStack> objectArrayList = this.generateLoot(context);
            if (freeSlotsList == null) {
                freeSlotsList = this.getFreeSlots(inventory, random);
            }
            this.shuffle(objectArrayList, freeSlotsList.size(), random);
            for (ItemStack itemStack : objectArrayList) {
                if (freeSlotsList.isEmpty()) {
                    return;
                }
                if (!itemStack.isEmpty()) {
                    inventory.setStack(freeSlotsList.remove(freeSlotsList.size() - 1), itemStack);
                }
            }
        }
    }
}
