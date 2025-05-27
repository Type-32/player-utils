package dev.ctrlneo.playerutils.client.utility;

import net.minecraft.util.Identifier;

public class Reference {

    public static final String MODID = "playerutils";

    public static Identifier of(String... paths) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < paths.length; i++) {
            s.append(paths[i]);
            if (i < paths.length - 1) {
                 s.append("/");
            }
        }
        return Identifier.of(MODID, s.toString());
    }
}
