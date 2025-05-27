package dev.ctrlneo.playerutils.client.foundation;

import com.google.gson.GsonBuilder;
import dev.ctrlneo.playerutils.client.utility.Reference;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.fabricmc.loader.api.FabricLoader;

public class ModConfig {
    public static ConfigClassHandler<ModConfig> HANDLER = ConfigClassHandler.<ModConfig>createBuilder(ModConfig.class)
            .id(Reference.of("playerutils_config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(FabricLoader.getInstance().getConfigDir().resolve("playerutils.json5"))
                    .setJson5(true)
                    .build())
            .build();
}
