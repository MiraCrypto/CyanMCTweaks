package io.github.miracrypto.mixin;

import io.github.miracrypto.CyanMCTweaks;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerManager.class)
public class PlayerManagerMixin {
    @Inject(method = "onPlayerConnect", at = @At("TAIL"))
    private void onOnPlayerConnect(ClientConnection connection, ServerPlayerEntity player, CallbackInfo ci) {
        String motd = CyanMCTweaks.CONFIG.getMotd();
        if (motd != null && !motd.isBlank()) {
            MiniMessage mm = MiniMessage.miniMessage();
            player.sendMessage(mm.deserialize(motd));
        }
    }
}
