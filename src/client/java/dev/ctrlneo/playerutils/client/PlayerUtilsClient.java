package dev.ctrlneo.playerutils.client;

import com.terraformersmc.modmenu.util.mod.fabric.FabricMod;
import dev.ctrlneo.playerutils.client.config.PlayerUtilsConfig;
import dev.ctrlneo.playerutils.client.modules.content.boatfly.BoatflyModule;
import dev.ctrlneo.playerutils.client.modules.content.damageIndicator.DamageIndicatorModule;
import dev.ctrlneo.playerutils.client.modules.utility.ModuleManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlayerUtilsClient implements ClientModInitializer {

    public static final String CONFIG_FILE = "playerutils.config.json";

    public static final ModuleManager moduleManager = new ModuleManager();

    public final BoatflyModule boatflyModule = new BoatflyModule();
    public final DamageIndicatorModule damageIndicatorModule = new DamageIndicatorModule();

    public static final Logger LOGGER = LoggerFactory.getLogger("Player Utils");

    @Override
    public void onInitializeClient() {
        PlayerUtilsConfig.HANDLER.load();
        moduleManager.registerModule(boatflyModule);
        moduleManager.registerModule(damageIndicatorModule);

        moduleManager.initializeModules();
    }
}
