package wutdahack.bedbenefitsfabric;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.server.network.ServerPlayerEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import wutdahack.bedbenefitsfabric.config.BBConfig;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class BedBenefitsFabric implements ModInitializer {

	public static Logger LOGGER = LogManager.getFormatterLogger("bedbenefitsfabric");
	public BBConfig config;
	private static BedBenefitsFabric instance;

	public static BedBenefitsFabric getInstance() {
		return instance;
	}

	@Override
	public void onInitialize() {
		loadConfig();
		instance = this;
		applyBenefits();

	}

	// config code based bedrockify config code
	// https://github.com/juancarloscp52/BedrockIfy/blob/1.17.x/src/main/java/me/juancarloscp52/bedrockify/Bedrockify.java

	public void loadConfig() {
		File configFile = new File(FabricLoader.getInstance().getConfigDir().toFile(), "bedbenefitsfabric.json");
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		if (configFile.exists()) {
			try {
				FileReader fileReader = new FileReader(configFile);
				config = gson.fromJson(fileReader, BBConfig.class);
				fileReader.close();
			} catch (IOException e) {
				LOGGER.warn("could not load bed benefits config options: " + e.getLocalizedMessage());
			}
		} else {
			config = new BBConfig();
			saveConfig();
		}
	}

	public void saveConfig() {
		File configFile = new File(FabricLoader.getInstance().getConfigDir().toFile(), "bedbenefitsfabric.json");
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		if (!configFile.getParentFile().exists()) {
			configFile.getParentFile().mkdir();
		}
		try {
			FileWriter fileWriter = new FileWriter(configFile);
			fileWriter.write(gson.toJson(config));
			fileWriter.close();
		} catch (IOException e) {
			LOGGER.warn("could not save bed benefits config options: " + e.getLocalizedMessage());
		}
	}





	public void applyBenefits() {
		EntitySleepEvents.STOP_SLEEPING.register(
			(
				(entity, sleepingPos) -> {

					if (entity.world.isClient) {
						return;
					}

					if (entity instanceof ServerPlayerEntity) {

						ServerPlayerEntity playerEntity = (ServerPlayerEntity) entity;

						if (playerEntity.networkHandler == null) {
							return;
						}

						if (this.config.shouldRestoreHealth()) {
							if (this.config.shouldRestoreFullHealth()) {
								playerEntity.heal(playerEntity.getMaxHealth() - playerEntity.getHealth());
							} else {
								playerEntity.heal(this.config.getHealAmount());
							}
						}

						if (this.config.shouldClearBadEffects() || this.config.shouldClearGoodEffects()) {
							this.clearEffects(playerEntity, this.config.shouldClearBadEffects(), this.config.shouldClearGoodEffects());
						}
					}
				}
			)
		);
	}

	// taken from the bookshelf code in the bookshelf library
	public void clearEffects(LivingEntity entity, boolean removeNegative, boolean removePositive) {

		final Set<StatusEffect> toClear = new HashSet<>();

		for (final StatusEffectInstance effect : entity.getStatusEffects()) {

			final boolean isGood = effect.getEffectType().isBeneficial();

			if (isGood && removePositive || !isGood && removeNegative) {

				toClear.add(effect.getEffectType());
			}
		}

		for (final StatusEffect effect : toClear) {

			entity.removeStatusEffect(effect);
		}
	}
}
