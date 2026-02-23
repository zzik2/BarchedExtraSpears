package zzik2.barched.extra.spears.neoforge;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import zzik2.barched.extra.spears.BarchedES;
import net.neoforged.fml.common.Mod;
import zzik2.barched.extra.spears.neoforge.wrapper.impl.NeoForgeCreativeTabOutput;
import zzik2.barched.extra.spears.registry.RegisterFactory;

@Mod(BarchedES.MOD_ID)
public final class BarchedESNeoForge {

    public BarchedESNeoForge(IEventBus eventBus) {
        BarchedES.init();

        eventBus.addListener(BuildCreativeModeTabContentsEvent.class, event -> {
            RegisterFactory.registerItemsToInvTab(event.getTab(), new NeoForgeCreativeTabOutput(event));
        });
    }
}
