package zzik2.barched.extra.spears.compat.impl;

import net.legacy.progression_reborn.registry.PRTiers;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import zzik2.barched.Barched;
import zzik2.barched.extra.spears.compat.ICompatMod;
import zzik2.barched.extra.spears.data.RegistryData;
import zzik2.barched.extra.spears.data.creativetab.TabData;
import zzik2.barched.extra.spears.data.items.SpearAttributeData;
import zzik2.barched.extra.spears.data.items.SpearData;
import zzik2.barched.extra.spears.data.material.MaterialData;

import java.util.List;
import java.util.function.Supplier;

public class ProgressionRebornCompat implements ICompatMod {

    @Override
    public String getModID() {
        return "progression_reborn";
    }

    @Override
    public List<RegistryData> getDefaultMaterials() {
        Supplier<CreativeModeTab> TAB = () -> BuiltInRegistries.CREATIVE_MODE_TAB.get(CreativeModeTabs.COMBAT);
        Supplier<Item> GOLDEN_SPEAR = () -> Barched.Items.GOLDEN_SPEAR;
        return List.of(
                //1.2x stronger than iron
                new RegistryData(
                        new MaterialData<>(() -> PRTiers.ROSE, "rose"),
                        new SpearData<>(new SpearAttributeData(
                                0.7917F,
                                1.14F,
                                0.5F,
                                3.0F,
                                6.6667F,
                                8.1F,
                                4.25F,
                                13.5F,
                                3.8333F
                        ), null),
                        new TabData<>(TAB, null, GOLDEN_SPEAR)
                )
        );
    }
}
