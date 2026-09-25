package zzik2.barched.extra.spears.compat.impl;

import com.khazoda.bronze.material.BronzeToolMaterial;
import com.khazoda.bronze.registry.MainRegistry;
import com.khazoda.bronze.registry.TabRegistry;
import zzik2.barched.extra.spears.compat.ICompatMod;
import zzik2.barched.extra.spears.data.RegistryData;
import zzik2.barched.extra.spears.data.creativetab.TabData;
import zzik2.barched.extra.spears.data.items.SpearAttributeData;
import zzik2.barched.extra.spears.data.items.SpearData;
import zzik2.barched.extra.spears.data.material.MaterialData;

import java.util.List;

public class BronzeCompat implements ICompatMod {

    @Override
    public String getModID() {
        return "bronze";
    }

    @Override
    public List<RegistryData> getDefaultMaterials() {
        return List.of(
                new RegistryData(
                        new MaterialData<>(() -> BronzeToolMaterial.INSTANCE, "bronze"),
                        new SpearData<>(new SpearAttributeData(
                                1.00F,
                                1.0125F,
                                0.55F,
                                2.75F,
                                7.75F,
                                6.625F,
                                5.1F,
                                10.625F,
                                4.6F
                        ), null),
                        new TabData<>(TabRegistry.BRONZE_TAB, null, MainRegistry.BRONZE_SWORD)
                )
        );
    }
}
