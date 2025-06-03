package dev.ctrlneo.playerutils.client.modules.content.damageIndicator;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.ctrlneo.playerutils.client.modules.UtilityModule;
import dev.ctrlneo.playerutils.client.modules.content.damageIndicator.gui.DamageIndicatorRenderLayer;
import dev.ctrlneo.playerutils.client.modules.content.damageIndicator.utility.DamageIndicatorManager;
import dev.ctrlneo.playerutils.client.utility.Reference;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudLayerRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;

public class DamageIndicatorModule extends UtilityModule {
    public static final String MODULE_CATEGORY = "Damage Indicator";

    @Override
    public void initialize() {
        super.initialize();
        ClientTickEvents.END_CLIENT_TICK.register(this::onClientTick);
        HudLayerRegistrationCallback.EVENT.register(layeredDrawer -> {
            layeredDrawer.addLayer(new DamageIndicatorRenderLayer());
        });
    }

    public void onClientTick(MinecraftClient client) {
        DamageIndicatorManager.INSTANCE.tick();
    }

    @Override
    public String getModuleName() {
        return MODULE_CATEGORY;
    }

    @Override
    public String getModuleId() {
        return "damage_indicator";
    }
}
