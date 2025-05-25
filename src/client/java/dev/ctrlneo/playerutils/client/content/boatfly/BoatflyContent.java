package dev.ctrlneo.playerutils.client.content.boatfly;

import dev.ctrlneo.playerutils.client.content.UtilityContent;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.input.KeyCodes;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.Window;
import net.minecraft.entity.Entity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class BoatflyContent extends UtilityContent {

    public static final String CONTENT_CATEGORY = "Boatfly";

    public static final KeyBinding TOGGLE_BOATFLY = new KeyBinding("playerutils.keybinds.boatfly.toggle", InputUtil.GLFW_KEY_X, CONTENT_CATEGORY);
    public static final KeyBinding TOGGLE_BOATFLY_HOVER = new KeyBinding("playerutils.keybinds.boatfly.hover.toggle", InputUtil.GLFW_KEY_P, CONTENT_CATEGORY);

    public static boolean boatflyEnabled = false;
    public static boolean boatflyHoveredEnabled = false;

    private final MinecraftClient MC = MinecraftClient.getInstance();

    private double startTickPos = 0;

    @Override
    public void onClientInitialize() {
        super.onClientInitialize();

        KeyBindingHelper.registerKeyBinding(TOGGLE_BOATFLY);
        KeyBindingHelper.registerKeyBinding(TOGGLE_BOATFLY_HOVER);

        ClientTickEvents.END_CLIENT_TICK.register((ClientTickEvents.EndTick)(client) -> {
            Window window = client.getWindow();

            if (MC.player == null) return;

            if (TOGGLE_BOATFLY.wasPressed()) {
                boatflyEnabled = !boatflyEnabled;
                if (boatflyEnabled) {
                    MC.player.sendMessage(Text.translatable("playerutils.msg.boatfly.enabled"), false);
                } else {
                    MC.player.sendMessage(Text.translatable("playerutils.msg.boatfly.disabled"), false);
                }
            }

            if (TOGGLE_BOATFLY_HOVER.wasPressed()) {
                boatflyHoveredEnabled = !boatflyHoveredEnabled;
                if (boatflyHoveredEnabled) {
                    MC.player.sendMessage(Text.translatable("playerutils.msg.boatfly.hover.enabled"), false);
                } else {
                    MC.player.sendMessage(Text.translatable("playerutils.msg.boatfly.hover.disabled"), false);
                }
            }

            if (boatflyEnabled && MC.player.hasVehicle()) {
                if (!MC.player.isMainPlayer()) return;

                Entity vehicle = MC.player.getControllingVehicle();

                Vec3d velocity = vehicle.getVelocity();

                if (boatflyHoveredEnabled) {
                    double motionY = MC.options.jumpKey.isPressed() ? 0.3 : MC.options.sprintKey.isPressed() ? -0.3 : MathHelper.sin(MC.player.age / 20F) / 40;
                    vehicle.setNoGravity(true);
                    vehicle.setVelocity(new Vec3d(velocity.x, motionY, velocity.z));
                } else {
                    if (MC.options.jumpKey.isPressed())
                        vehicle.setVelocity(new Vec3d(velocity.x, 0.3, velocity.z));
                }
            }
        });
    }

    public void applyBoatfly(boolean enabled) {
        if (!enabled) return;


    }
}
