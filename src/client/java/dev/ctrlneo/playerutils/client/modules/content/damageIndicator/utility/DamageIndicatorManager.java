package dev.ctrlneo.playerutils.client.modules.content.damageIndicator.utility;

import dev.ctrlneo.playerutils.client.PlayerUtilsClient;
import dev.ctrlneo.playerutils.client.config.PlayerUtilsConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class DamageIndicatorManager {
    public static final DamageIndicatorManager INSTANCE = new DamageIndicatorManager();

    private final CopyOnWriteArrayList<DamageIndicator> indicators = new CopyOnWriteArrayList<>();

    public void addDamageIndicator(Vec3d damageSourcePos, float damage) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        Vec3d playerPos = client.player.getPos();
        Vec3d direction = damageSourcePos.subtract(playerPos).normalize();
        Vec3d damageSourceProcessed = client.player.getPos().add(damageSourcePos.subtract(client.player.getPos()));

        indicators.add(new DamageIndicator(true, damageSourceProcessed, damage, System.currentTimeMillis()));
        PlayerUtilsClient.LOGGER.info("Added DMG Indicator");
    }

    public void addDamageIndicator(float damage) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        indicators.add(new DamageIndicator(false, null, damage, System.currentTimeMillis()));
    }

    public void tick() {
        long currentTime = System.currentTimeMillis();
        indicators.removeIf(indicator -> currentTime - indicator.getCreationTime() > ((PlayerUtilsConfig.get().damageIndicatorDuration + 0.5F) * 1000F)); // Remove after 2 seconds
    }

    public CopyOnWriteArrayList<DamageIndicator> getIndicators() {
        return indicators;
    }

    private static float calculateFinalAngle(Vec3d playerForward, Vec3d playerPosition, Vec3d damagePosition) {
        float playerAngle = (float) (Math.atan2(playerForward.x, playerForward.z) * 180.0F / Math.PI);
        Vec2f enemyVec = new Vec2f((float)damagePosition.x, (float)damagePosition.z)
                .add(new Vec2f((float)playerPosition.x, (float)playerPosition.z).negate());
        float enemyAngle = (float) (Math.atan2(enemyVec.x, enemyVec.y) * 180.0F / Math.PI);
        float finalAngle = enemyAngle - playerAngle;
        return -finalAngle;
    }
}
