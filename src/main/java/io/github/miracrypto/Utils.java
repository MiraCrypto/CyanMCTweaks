package io.github.miracrypto;

import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.biome.Biome;

import java.util.List;

public class Utils {
    public boolean shouldHaveInfiniteTotem(LivingEntity entity) {
        if (!(entity instanceof ServerPlayerEntity playerEntity)) {
            return false;
        }
        return CyanMCTweaks.CONFIG.isInfiniteTotems() || infiniteTotemsCheckPlayer(playerEntity) || infiniteTotemsCheckBiome(playerEntity);
    }

    public boolean infiniteTotemsCheckPlayer(ServerPlayerEntity playerEntity) {
        List<String> infiniteTotemsInclude = CyanMCTweaks.CONFIG.getInfiniteTotemsInclude();
        if (infiniteTotemsInclude == null) {
            return false;
        }

        return infiniteTotemsInclude.contains(playerEntity.getName().getString());
    }

    public boolean infiniteTotemsCheckBiome(ServerPlayerEntity playerEntity) {
        List<String> biomes = CyanMCTweaks.CONFIG.getInfiniteTotemsBiomes();
        if (biomes == null) {
            return false;
        }
        RegistryEntry<Biome> currentBiome = playerEntity.getWorld().getBiome(playerEntity.getBlockPos());
        for (String id: biomes) {
            if (currentBiome.matchesId(new Identifier(id))) {
                return true;
            }
        }
        return false;
    }
}
