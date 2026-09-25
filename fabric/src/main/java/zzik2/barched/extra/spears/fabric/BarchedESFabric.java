package zzik2.barched.extra.spears.fabric;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import zzik2.barched.extra.spears.BarchedES;
import net.fabricmc.api.ModInitializer;
import zzik2.barched.extra.spears.fabric.wrapper.impl.FabricCreativeTabOutput;
import zzik2.barched.extra.spears.registry.RegisterFactory;

public final class BarchedESFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        BarchedES.init();

        ItemGroupEvents.MODIFY_ENTRIES_ALL.register(((creativeModeTab, fabricItemGroupEntries) -> {
            RegisterFactory.registerItemsToInvTab(creativeModeTab, new FabricCreativeTabOutput(fabricItemGroupEntries));
        }));
    }
}
