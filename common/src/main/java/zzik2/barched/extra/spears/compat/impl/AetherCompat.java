package zzik2.barched.extra.spears.compat.impl;

import com.aetherteam.aether.item.AetherCreativeTabs;
import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether.item.combat.AetherItemTiers;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import zzik2.barched.extra.spears.compat.ICompatMod;
import zzik2.barched.extra.spears.compat.spears.aether.GravititeSpearItem;
import zzik2.barched.extra.spears.compat.spears.aether.HolystoneSpearItem;
import zzik2.barched.extra.spears.compat.spears.aether.ZaniteSpearItem;
import zzik2.barched.extra.spears.data.RegistryData;
import zzik2.barched.extra.spears.data.creativetab.TabData;
import zzik2.barched.extra.spears.data.items.SpearAttributeData;
import zzik2.barched.extra.spears.data.items.SpearData;
import zzik2.barched.extra.spears.data.material.MaterialData;
import zzik2.zreflex.reflection.ZReflectionTool;

import java.util.List;
import java.util.function.Supplier;

public class AetherCompat implements ICompatMod {

    @Override
    public String getModID() {
        return "aether";
    }

    @Override
    public List<RegistryData> getDefaultMaterials() {
        Supplier<? extends Item> SKYROOT_SWORD = ZReflectionTool.getStaticFieldValue(AetherItems.class, "SKYROOT_SWORD");
        Supplier<? extends Item> GRAVITITE_SWORD = ZReflectionTool.getStaticFieldValue(AetherItems.class, "GRAVITITE_SWORD");
        Supplier<? extends Item> ZANITE_SWORD = ZReflectionTool.getStaticFieldValue(AetherItems.class, "ZANITE_SWORD");
        Supplier<? extends Item> HOLYSTONE_SWORD = ZReflectionTool.getStaticFieldValue(AetherItems.class, "HOLYSTONE_SWORD");
        Supplier<CreativeModeTab> TAB = ZReflectionTool.getStaticFieldValue(AetherCreativeTabs.class, "AETHER_EQUIPMENT_AND_UTILITIES");
        return List.of(
                //1.1x stronger than wood
                new RegistryData(
                        new MaterialData<>(() -> AetherItemTiers.SKYROOT, "skyroot"),
                        new SpearData<>(new SpearAttributeData(
                                0.75F,
                                0.77F,
                                0.7F,
                                4.5F,
                                12.7273F,
                                9.0F,
                                5.1F,
                                13.75F,
                                4.6F
                        ), null),
                        new TabData<>(TAB, null, SKYROOT_SWORD)
                ),
                //1.1x stronger than diamond
                new RegistryData(
                        new MaterialData<>(() -> AetherItemTiers.GRAVITITE, "gravitite"),
                        new SpearData<>(new SpearAttributeData(
                                0.9545F,
                                1.1825F,
                                0.4545F,
                                3.3F,
                                6.8182F,
                                7.15F,
                                4.6364F,
                                11.0F,
                                4.1818F
                        ), GravititeSpearItem::new),
                        new TabData<>(TAB, null, GRAVITITE_SWORD)
                ),
                //1.1x stronger than iron
                new RegistryData(
                        new MaterialData<>(() -> AetherItemTiers.ZANITE, "zanite"),
                        new SpearData<>(new SpearAttributeData(
                                0.8636F,
                                1.045F,
                                0.5455F,
                                2.75F,
                                7.2727F,
                                7.425F,
                                4.6364F,
                                12.375F,
                                4.1818F
                        ), ZaniteSpearItem::new),
                        new TabData<>(TAB, null, ZANITE_SWORD)
                ),
                //1.1x stronger than stone
                new RegistryData(
                        new MaterialData<>(() -> AetherItemTiers.HOLYSTONE, "holystone"),
                        new SpearData<>(new SpearAttributeData(
                                0.6818F,
                                0.902F,
                                0.6364F,
                                4.95F,
                                9.0909F,
                                9.9F,
                                4.6364F,
                                15.125F,
                                4.1818F
                        ), HolystoneSpearItem::new),
                        new TabData<>(TAB, null, HOLYSTONE_SWORD)
                )
        );
    }
}
