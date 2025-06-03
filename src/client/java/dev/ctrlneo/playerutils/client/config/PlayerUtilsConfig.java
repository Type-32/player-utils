package dev.ctrlneo.playerutils.client.config;

import com.nimbusds.jose.shaded.gson.GsonBuilder;
import dev.ctrlneo.playerutils.client.PlayerUtilsClient;
import dev.ctrlneo.playerutils.client.utility.Reference;
import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.FloatFieldControllerBuilder;
import dev.isxander.yacl3.api.controller.IntegerFieldControllerBuilder;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class PlayerUtilsConfig {
    public static ConfigClassHandler<PlayerUtilsConfig> HANDLER = ConfigClassHandler.<PlayerUtilsConfig>createBuilder(PlayerUtilsConfig.class)
            .id(Reference.of("playerutils_config"))
            .serializer(playerUtilsConfigConfigClassHandler -> GsonConfigSerializerBuilder.create(playerUtilsConfigConfigClassHandler)
                    .setPath(FabricLoader.getInstance().getConfigDir().resolve(PlayerUtilsClient.CONFIG_FILE))
                    .setJson5(true)
                    .build())
            .build();

    public static PlayerUtilsConfig get() {
        return HANDLER.instance();
    }

    @SerialEntry
    public boolean boatflyEnabled = false;

    @SerialEntry
    public boolean boatflyHoverEnabled = false;

    @SerialEntry
    public float boatflySpeed = 0.5F;

    @SerialEntry
    public boolean damageIndicatorEnabled = true;

    @SerialEntry
    public boolean showDamageIndicatorNumber = true;

    @SerialEntry
    public int damageIndicatorDistance = 20;

    @SerialEntry
    public float damageIndicatorDuration = 2F;

    public static Screen createConfigScreen(Screen parent) {
        return YetAnotherConfigLib.createBuilder()
                .title(Text.translatable("playerutils"))
                .category(ConfigCategory.createBuilder()
                        .name(Text.translatable("playerutils.modules.boatfly.name"))
                        .option(Option.<Boolean>createBuilder()
                                .name(Text.literal("Module Enabled"))
                                .binding(false, () -> get().boatflyEnabled, newVal -> {
                                    get().boatflyEnabled = newVal;
                                    HANDLER.save();
                                })
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .option(Option.<Boolean>createBuilder()
                                .name(Text.literal("Hover In Mid-Air"))
                                .binding(false, () -> get().boatflyHoverEnabled, newVal -> {
                                    get().boatflyHoverEnabled = newVal;
                                    HANDLER.save();
                                })
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .option(Option.<Float>createBuilder()
                                .name(Text.literal("Boatfly Speed"))
                                .binding(0.5F, () -> get().boatflySpeed, newVal -> {
                                    get().boatflySpeed = newVal;
                                    HANDLER.save();
                                })
                                .controller(FloatFieldControllerBuilder::create)
                                .build())
                        .build())
                .category(ConfigCategory.createBuilder()
                        .name(Text.translatable("playerutils.modules.damage_indicator.name"))
                        .option(Option.<Boolean>createBuilder()
                                .name(Text.literal("Module Enabled"))
                                .binding(true, () -> get().damageIndicatorEnabled, newVal -> {
                                    get().damageIndicatorEnabled = newVal;
                                    HANDLER.save();
                                })
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .option(Option.<Boolean>createBuilder()
                                .name(Text.literal("Show Damage Number"))
                                .binding(true, () -> get().showDamageIndicatorNumber, newVal -> {
                                    get().showDamageIndicatorNumber = newVal;
                                    HANDLER.save();
                                })
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .option(Option.<Integer>createBuilder()
                                .name(Text.literal("Damage Indicator Distance from Center"))
                                .binding(20, () -> get().damageIndicatorDistance, newVal -> {
                                    get().damageIndicatorDistance = newVal;
                                    HANDLER.save();
                                })
                                .controller(IntegerFieldControllerBuilder::create)
                                .build())
                        .option(Option.<Float>createBuilder()
                                .name(Text.literal("Damage Indicator Show Duration"))
                                .binding(2F, () -> get().damageIndicatorDuration, newVal -> {
                                    get().damageIndicatorDuration = newVal;
                                    HANDLER.save();
                                })
                                .controller(FloatFieldControllerBuilder::create)
                                .build())
                        .build())
                .build()
                .generateScreen(parent);
    }
}
