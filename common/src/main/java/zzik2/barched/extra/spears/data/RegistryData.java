package zzik2.barched.extra.spears.data;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import zzik2.barched.extra.spears.data.creativetab.TabData;
import zzik2.barched.extra.spears.data.items.SpearAttributeData;
import zzik2.barched.extra.spears.data.items.SpearData;
import zzik2.barched.extra.spears.data.items.SpearItemFactory;
import zzik2.barched.extra.spears.data.material.MaterialData;

import java.util.function.Supplier;

public record RegistryData(MaterialData<Supplier<Tier>, String> materialData, SpearData<SpearAttributeData, SpearItemFactory> spearData, TabData<? extends Item, ? extends Item> tabData) {
}
