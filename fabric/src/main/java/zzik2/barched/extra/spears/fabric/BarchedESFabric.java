package zzik2.barched.extra.spears.fabric;

import zzik2.barched.extra.spears.BarchedES;
import net.fabricmc.api.ModInitializer;

public final class BarchedESFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        // Run our common setup.
        BarchedES.init();
    }
}
