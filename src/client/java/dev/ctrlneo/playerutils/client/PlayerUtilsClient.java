package dev.ctrlneo.playerutils.client;

import dev.ctrlneo.playerutils.client.modules.content.boatfly.BoatflyModule;
import dev.ctrlneo.playerutils.client.modules.utility.ModuleManager;
import net.fabricmc.api.ClientModInitializer;

public class PlayerUtilsClient implements ClientModInitializer {

    public static final ModuleManager moduleManager = new ModuleManager();

    public final BoatflyModule boatflyModule = new BoatflyModule();

    @Override
    public void onInitializeClient() {
        moduleManager.registerModule(boatflyModule);

        moduleManager.initializeModules();
    }
}
