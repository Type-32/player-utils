package dev.ctrlneo.playerutils.client;

import dev.ctrlneo.playerutils.client.content.boatfly.BoatflyContent;
import net.fabricmc.api.ClientModInitializer;

public class PlayerUtilsClient implements ClientModInitializer {

    public BoatflyContent boatflyContent = new BoatflyContent();
    @Override
    public void onInitializeClient() {
        boatflyContent.onClientInitialize();
    }
}
