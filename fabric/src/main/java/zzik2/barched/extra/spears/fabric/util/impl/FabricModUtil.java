package zzik2.barched.extra.spears.fabric.util.impl;

import net.fabricmc.loader.impl.FabricLoaderImpl;
import zzik2.barched.extra.spears.util.IModUtil;

public class FabricModUtil implements IModUtil {

    @Override
    public boolean isLoaded(String modID) {
        return FabricLoaderImpl.INSTANCE.isModLoaded(modID);
    }
}
