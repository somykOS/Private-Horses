package net.somyk.privatehorses;

import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.loader.api.FabricLoader;
import net.somyk.privatehorses.util.ModConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PrivateHorses implements ModInitializer {

    public static final String MOD_ID = "private-horses";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final boolean polymer_loaded = FabricLoader.getInstance().isModLoaded("polymer-resource-pack");

	@Override
	public void onInitialize() {

		if(polymer_loaded) {
			if (PolymerResourcePackUtils.addModAssets(MOD_ID)) {
				LOGGER.info("[{}]: Successfully added mod assets.", MOD_ID);
			} else {
				LOGGER.error("[{}]: Failed to add mod assets.", MOD_ID);
			}
		} else ModConfig.registerConfigs();

	}

}