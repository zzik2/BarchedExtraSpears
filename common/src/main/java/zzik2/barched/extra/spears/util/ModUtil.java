package zzik2.barched.extra.spears.util;

import dev.architectury.platform.Platform;
import zzik2.zreflex.reflection.ZReflectionTool;

import java.util.Objects;

public class ModUtil {

    private static final IModUtil INSTANCE = load();

    private static IModUtil load() {
        String modUtilClazz;
        if (Platform.isFabric()) {
            modUtilClazz = "zzik2.barched.extra.spears.fabric.util.impl.FabricModUtil";
        } else {
            modUtilClazz = "zzik2.barched.extra.spears.neoforge.util.impl.NeoForgeModUtil";
        }
        try {
            Class<?> clazz = Class.forName(modUtilClazz);
            return (IModUtil) ZReflectionTool.newInstance(clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isLoaded(String modID) {
        Objects.requireNonNull(INSTANCE);
        return INSTANCE.isLoaded(modID);
    }
}
