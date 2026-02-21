package zzik2.barched.extra.spears.fabric.client;

import net.fabricmc.api.ClientModInitializer;
import zzik2.barched.extra.spears.BarchedESClient;

public final class BarchedESFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BarchedESClient.init();
    }
}
