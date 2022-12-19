package io.github.miracrypto.config;

import lombok.Data;

import java.util.List;


@Data
public class ModConfig {
    private int lootingLevelBonus = 0;
    private int fortuneLevelBonus = 0;
    private boolean infiniteTotems = false;
    private List<String> infiniteTotemsInclude;
    private List<String> infiniteTotemsBiomes;
    private int itemEntityDespawnAge = 0;
    private int wardenDarknessDuration = 0;
    private float damageScaleWarden = 1f;
    private float damageScaleMob = 1f;
    private float damageScalePlayer = 1f;
    private float damageScaleEnvironment = 1f;
    private float damageScale = 1f;
    private int lootChestExtraRolls = 0;
    private boolean deathDropsGlowing = false;
    private boolean ignoreAirExposureChecks = false;
    private float oreExpDropsExtraChance = 0f;
    private float mobExpDropsMultiplier = 1f;
    private String motd;
}
