package dev.ctrlneo.playerutils.client.modules.content.boatfly;

import dev.ctrlneo.playerutils.client.modules.UtilityModule;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.Window;
import net.minecraft.entity.Entity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class BoatflyModule extends UtilityModule {

    public static final String CONTENT_CATEGORY = "Boatfly";

    public static final KeyBinding TOGGLE_BOATFLY = new KeyBinding("playerutils.keybinds.boatfly.toggle", InputUtil.GLFW_KEY_X, CONTENT_CATEGORY);
    public static final KeyBinding TOGGLE_BOATFLY_HOVER = new KeyBinding("playerutils.keybinds.boatfly.hover.toggle", InputUtil.GLFW_KEY_P, CONTENT_CATEGORY);

    public static boolean boatflyHoveredEnabled = false;

    private final MinecraftClient MC = MinecraftClient.getInstance();

    private double startTickPos = 0;

    private float boatSpeed = 0.5F;

    @Override
    public void initialize() {
        super.initialize();

        KeyBindingHelper.registerKeyBinding(TOGGLE_BOATFLY);
        KeyBindingHelper.registerKeyBinding(TOGGLE_BOATFLY_HOVER);

        ClientTickEvents.END_CLIENT_TICK.register((ClientTickEvents.EndTick)(client) -> {
            Window window = client.getWindow();

            if (MC.player == null) return;

            if (TOGGLE_BOATFLY.wasPressed()) {
                this.toggleState();
            }

            if (TOGGLE_BOATFLY_HOVER.wasPressed()) {
                boatflyHoveredEnabled = !boatflyHoveredEnabled;
                if (boatflyHoveredEnabled) {
                    MC.player.sendMessage(Text.translatable("playerutils.msg.boatfly.hover.enabled"), false);
                } else {
                    MC.player.sendMessage(Text.translatable("playerutils.msg.boatfly.hover.disabled"), false);
                }
            }

            if (enabled && MC.player.hasVehicle()) {
                if (!MC.player.isMainPlayer()) return;

                Entity vehicle = MC.player.getControllingVehicle();

                if (!(vehicle instanceof BoatEntity boat)) return;

                Vec3d velocity = vehicle.getVelocity();

                if (boatflyHoveredEnabled) {
                    double motionY = MC.options.jumpKey.isPressed() ? 0.3 : MC.options.sprintKey.isPressed() ? -0.3 : MathHelper.sin(MC.player.age / 20F) / 40;
                    vehicle.setNoGravity(true);
                    vehicle.setVelocity(new Vec3d(velocity.x, motionY, velocity.z));
                } else {
                    vehicle.setNoGravity(false);
                    if (MC.options.jumpKey.isPressed())
                        vehicle.setVelocity(new Vec3d(velocity.x, 0.3, velocity.z));
                }

                applyBoatfly(boat, boatSpeed);
            }
        });
    }

    @Override
    public String getModuleName() {
        return "";
    }

    @Override
    public String getModuleId() {
        return "";
    }

    @Override
    public void enableModule() {
        assert MC.player != null;
        MC.player.sendMessage(Text.translatable("playerutils.msg.boatfly.enabled"), false);
    }

    @Override
    public void disableModule() {
        assert MC.player != null;
        MC.player.sendMessage(Text.translatable("playerutils.msg.boatfly.disabled"), false);
    }

    public void applyBoatfly(BoatEntity boat, float speedMultiplier) {
        if (boat == null || !enabled) return;

        Vec3d velocity = boat.getVelocity();

        // Get the boat's forward direction vector
        Vec3d lookVec = boat.getRotationVec(1.0F);

        // Calculate horizontal velocity components based on boat's rotation
        double motionX = velocity.x;
        double motionZ = velocity.z;

        // Apply speed multiplier to horizontal velocity in the boat's forward direction
        if (MC.options.forwardKey.isPressed()) {
            motionX += lookVec.x * 0.1 * speedMultiplier;
            motionZ += lookVec.z * 0.1 * speedMultiplier;
        }
        if (MC.options.backKey.isPressed()) {
            motionX -= lookVec.x * 0.1 * speedMultiplier;
            motionZ -= lookVec.z * 0.1 * speedMultiplier;
        }

        // Optional: Add strafing
//        if (MC.options.leftKey.isPressed()) {
//            Vec3d strafeVec = lookVec.rotateY((float) Math.PI / 2);
//            motionX += strafeVec.x * 0.1 * speedMultiplier;
//            motionZ += strafeVec.z * 0.1 * speedMultiplier;
//        }
//        if (MC.options.rightKey.isPressed()) {
//            Vec3d strafeVec = lookVec.rotateY((float) -Math.PI / 2);
//            motionX += strafeVec.x * 0.1 * speedMultiplier;
//            motionZ += strafeVec.z * 0.1 * speedMultiplier;
//        }

        // Clamp the horizontal velocity to prevent excessive speed
        double maxSpeed = 2.0 * speedMultiplier;
        double horizontalSpeed = Math.sqrt(motionX * motionX + motionZ * motionZ);
        if (horizontalSpeed > maxSpeed) {
            motionX = (motionX / horizontalSpeed) * maxSpeed;
            motionZ = (motionZ / horizontalSpeed) * maxSpeed;
        }

        // Apply friction when no input
        if (!MC.options.forwardKey.isPressed() && !MC.options.backKey.isPressed() &&
                !MC.options.leftKey.isPressed() && !MC.options.rightKey.isPressed()) {
            motionX *= 0.9;
            motionZ *= 0.9;
        }

        // Set the new velocity
        boat.setVelocity(motionX, velocity.y, motionZ);
    }
}
