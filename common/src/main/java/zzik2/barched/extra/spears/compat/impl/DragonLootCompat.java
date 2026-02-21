package zzik2.barched.extra.spears.compat.impl;

import net.dragonloot.init.ItemInit;
import net.dragonloot.item.DragonToolMaterials;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import zzik2.barched.extra.spears.compat.ICompatMod;
import zzik2.barched.extra.spears.data.RegistryData;
import zzik2.barched.extra.spears.data.creativetab.TabData;
import zzik2.barched.extra.spears.data.items.SpearAttributeData;
import zzik2.barched.extra.spears.data.items.SpearData;
import zzik2.barched.extra.spears.data.material.MaterialData;

import java.util.List;
import java.util.function.Supplier;

public class DragonLootCompat implements ICompatMod {

    @Override
    public String getModID() {
        return "dragonloot";
    }

    @Override
    public List<RegistryData> getDefaultMaterials() {
        Supplier<Item> DRAGON_SWORD = () -> ItemInit.DRAGON_SWORD;
        Supplier<CreativeModeTab> TAB = () -> BuiltInRegistries.CREATIVE_MODE_TAB.get(ItemInit.DRAGON_ITEM_GROUP);
        return List.of(
                //1.125x stronger than netherite
                new RegistryData(
                        new MaterialData<>(DragonToolMaterials.DRAGON, "dragon"),
                        new SpearData<>(new SpearAttributeData(
                                0.9583F,
                                1.44F,
                                0.3333F,
                                3.0F,
                                5.8333F,
                                6.6F,
                                4.25F,
                                10.5F,
                                3.8333F
                        ), null),
                        new TabData<>(TAB, null, null)
                )
        );
    }
}
