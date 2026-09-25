package zzik2.barched.extra.spears.compat.impl;

import net.id.paradiselost.items.ParadiseLostItemGroups;
import net.id.paradiselost.items.ParadiseLostItems;
import net.id.paradiselost.items.tools.ParadiseLostToolMaterials;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import zzik2.barched.extra.spears.compat.ICompatMod;
import zzik2.barched.extra.spears.data.RegistryData;
import zzik2.barched.extra.spears.data.creativetab.TabData;
import zzik2.barched.extra.spears.data.items.SpearAttributeData;
import zzik2.barched.extra.spears.data.items.SpearData;
import zzik2.barched.extra.spears.data.material.MaterialData;
import zzik2.zreflex.reflection.ZReflectionTool;

import java.util.List;
import java.util.function.Supplier;

public class ParadiseLostCompat implements ICompatMod {

    @Override
    public String getModID() {
        return "paradise_lost";
    }

    @Override
    public List<RegistryData> getDefaultMaterials() {
        Supplier<Tier> OLVITE = () -> ZReflectionTool.getStaticFieldValue(ParadiseLostToolMaterials.class, "OLVITE");
        Supplier<Tier> SURTRUM = () -> ZReflectionTool.getStaticFieldValue(ParadiseLostToolMaterials.class, "SURTRUM");
        Supplier<Tier> GLAZED_GOLD = () -> ZReflectionTool.getStaticFieldValue(ParadiseLostToolMaterials.class, "GLAZED_GOLD");
        Supplier<Item> OLVITE_SWORD = () -> ZReflectionTool.getStaticFieldValue(ParadiseLostItems.class, "OLVITE_SWORD");
        Supplier<Item> SURTRUM_SWORD = () -> ZReflectionTool.getStaticFieldValue(ParadiseLostItems.class, "SURTRUM_SWORD");
        Supplier<Item> GLAZED_GOLD_SWORD = () -> ZReflectionTool.getStaticFieldValue(ParadiseLostItems.class, "GLAZED_GOLD_SWORD");
        ResourceKey<CreativeModeTab> tabKey = ZReflectionTool.getStaticFieldValue(ParadiseLostItemGroups.class, "PARADISE_EQUIPMENT");
        Supplier<CreativeModeTab> TAB = () -> BuiltInRegistries.CREATIVE_MODE_TAB.get(tabKey);

        return List.of(
                //same with iron
                new RegistryData(
                        new MaterialData<>(OLVITE, "olvite"),
                        new SpearData<>(new SpearAttributeData(
                                0.95F,
                                0.95F,
                                0.60F,
                                2.5F,
                                8.0F,
                                6.75F,
                                5.1F,
                                11.25F,
                                4.6F
                        ), null),
                        new TabData<>(TAB, null, OLVITE_SWORD)
                ),
                //1.2x weaker than netherite
                new RegistryData(
                        new MaterialData<>(SURTRUM, "surtrum"),
                        new SpearData<>(new SpearAttributeData(
                                1.38F,
                                1.0F,
                                0.48F,
                                2.0833F,
                                8.4F,
                                4.5833F,
                                6.12F,
                                7.2917F,
                                5.52F
                        ), null),
                        new TabData<>(TAB, null, SURTRUM_SWORD)
                ),
                //same with iron
                new RegistryData(
                        new MaterialData<>(GLAZED_GOLD, "glazed_gold"),
                        new SpearData<>(new SpearAttributeData(
                                0.95F,
                                0.95F,
                                0.60F,
                                2.5F,
                                8.0F,
                                6.75F,
                                5.1F,
                                11.25F,
                                4.6F
                        ), null),
                        new TabData<>(TAB, null, GLAZED_GOLD_SWORD)
                )
        );
    }
}
