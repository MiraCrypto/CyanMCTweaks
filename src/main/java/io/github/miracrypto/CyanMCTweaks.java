package io.github.miracrypto;

import io.github.miracrypto.config.ModConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.error.YAMLException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;

public class CyanMCTweaks implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("CyanMCTweaks");
	public static ModConfig CONFIG = new ModConfig();
	public static Utils utils = new Utils();

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello CyanMC 1.19 Mod!");
		reloadConfig();
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(CommandManager.literal("cyanmcreload").executes(context -> {
				if (reloadConfig()) {
					context.getSource().sendMessage(Text.literal("Configuration reloaded!"));
					return 0;
				} else {
					context.getSource().sendMessage(Text.literal("Configuration reload Failed!"));
					return 1;
				}
			}));
		});
	}

	public static boolean reloadConfig() {
		Yaml yaml = new Yaml(new Constructor(ModConfig.class));
		FabricLoader loader = FabricLoader.getInstance();
		Path configFile = loader.getConfigDir().resolve("cyanmctweaks.yml");
		try {
			FileInputStream configFileInputStream = new FileInputStream(configFile.toFile());
			ModConfig config = yaml.load(configFileInputStream);
			configFileInputStream.close();
			CONFIG = config;
		} catch (FileNotFoundException e) {
			LOGGER.error("cyanmctweaks.yml not found!");
			return false;
		} catch (IOException e) {
			LOGGER.error("IOException while trying to load cyanmctweaks.yml!", e);
			return false;
		} catch (YAMLException e) {
			LOGGER.error("YAML exception while trying to load config!", e);
			return false;
		}
		return true;
	}
}
