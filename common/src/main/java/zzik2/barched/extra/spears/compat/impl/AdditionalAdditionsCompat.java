package zzik2.barched.extra.spears.compat.impl;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import one.dqu.additionaladditions.registry.AAItems;
import zzik2.barched.Barched;
import zzik2.barched.extra.spears.compat.ICompatMod;
import zzik2.barched.extra.spears.compat.spears.additionaladditions.config.Config;
import zzik2.barched.extra.spears.data.RegistryData;
import zzik2.barched.extra.spears.data.creativetab.TabData;
import zzik2.barched.extra.spears.data.items.SpearAttributeData;
import zzik2.barched.extra.spears.data.items.SpearData;
import zzik2.barched.extra.spears.data.material.MaterialData;

import java.util.List;
import java.util.function.Supplier;

public class AdditionalAdditionsCompat implements ICompatMod {

    @Override
    public String getModID() {
        return "additionaladditions";
    }

    @Override
    public List<RegistryData> getDefaultMaterials() {
        //fix static init
        try {
            Class.forName("one.dqu.additionaladditions.config.Config");
            Class.forName("one.dqu.additionaladditions.item.RoseGoldToolMaterial");
            Class.forName("one.dqu.additionaladditions.registry.AAItems");
        } catch (Exception ignored) {}

        Supplier<CreativeModeTab> TAB = () -> BuiltInRegistries.CREATIVE_MODE_TAB.get(CreativeModeTabs.COMBAT);
        Supplier<Item> GOLDEN_SPEAR = () -> Barched.Items.GOLDEN_SPEAR;

        int uses = Config.ROSE_GOLD_SPEAR.get().durability();
        float damage = Config.ROSE_GOLD_SPEAR.get().attackDamage();

        return List.of(
                //1.25x stronger than iron
                new RegistryData(
                        new MaterialData<>(AAItems.ROSE_GOLD_TOOL_MATERIAL, "rose_gold", uses, damage),
                        new SpearData<>(new SpearAttributeData(
                                0.86F,
                                1.1875F,
                                0.48F,
                                3.125F,
                                6.4F,
                                8.4375F,
                                4.08F,
                                14.0625F,
                                3.68F
                        ), null),
                        new TabData<>(TAB, null, GOLDEN_SPEAR)
                )
        );
    }
}
