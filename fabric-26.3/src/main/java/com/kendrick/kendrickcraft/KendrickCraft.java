package com.kendrick.kendrickcraft;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Fabric entry point. Minecraft 26.3 is unobfuscated, so this subproject
 * uses Fabric Loom's non-remapping plugin and the official game names.
 */
public final class KendrickCraft implements ModInitializer {
    public static final String MOD_ID = "kendrickcraft";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("KendrickCraft Fabric 26.3 loaded.");
    }
}
