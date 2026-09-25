package zzik2.barched.extra.spears.neoforge.util.impl;

import net.neoforged.fml.loading.FMLLoader;
import zzik2.barched.extra.spears.util.IModUtil;

public class NeoForgeModUtil implements IModUtil {

    @Override
    public boolean isLoaded(String modID) {
        return FMLLoader.getLoadingModList().getModFileById(modID) != null;
    }
}
