package io.github.miracrypto.mixin;

import io.github.miracrypto.CyanMCTweaks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.OreBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.IntProvider;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(OreBlock.class)
public class OreBlockMixin extends Block {
    @Shadow @Final private IntProvider experienceDropped;

    public OreBlockMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "onStacksDropped", at = @At("TAIL"))
    private void onOnStacksDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack stack, boolean dropExperience, CallbackInfo ci) {
        float chance = CyanMCTweaks.CONFIG.getOreExpDropsExtraChance();
        if (chance > 0 && dropExperience) {
            for (float r = world.random.nextFloat(); r > chance; r = world.random.nextFloat()) {
                dropExperienceWhenMined(world, pos, stack, this.experienceDropped);
            }
        }
    }
}
