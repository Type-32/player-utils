package dev.ctrlneo.playerutils.client.modules.content.damageIndicator;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.ctrlneo.playerutils.client.modules.UtilityModule;
import dev.ctrlneo.playerutils.client.modules.content.damageIndicator.gui.DamageIndicatorRenderLayer;
import dev.ctrlneo.playerutils.client.modules.content.damageIndicator.utility.DamageIndicatorManager;
import dev.ctrlneo.playerutils.client.modules.content.listeners.event.PlayerDamagedEvent;
import dev.ctrlneo.playerutils.client.utility.Reference;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudLayerRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public class DamageIndicatorModule extends UtilityModule {
    public static final String MODULE_CATEGORY = "Damage Indicator";

    private MinecraftClient mc;

    @Override
    public void initialize() {
        super.initialize();

        mc = MinecraftClient.getInstance();

        ClientTickEvents.END_CLIENT_TICK.register(this::onClientTick);

        HudLayerRegistrationCallback.EVENT.register(layeredDrawer -> {
            layeredDrawer.addLayer(new DamageIndicatorRenderLayer());
        });

        PlayerDamagedEvent.EVENT.register((newHealth, oldHealth, damageSource) -> {
            if (mc.player == null) return;

            Vec3d sourcePos = null;
            double amount = oldHealth - newHealth;

            if (damageSource.getAttacker() != null && !damageSource.getAttacker().getUuid().equals(mc.player.getUuid())) {
                sourcePos = damageSource.getAttacker().getPos();
            }

            if (sourcePos != null) {
                DamageIndicatorManager.INSTANCE.addDamageIndicator(sourcePos, (float) amount);
            } else {
                DamageIndicatorManager.INSTANCE.addDamageIndicator((float) amount);
            }
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
