package zzik2.barched.extra.spears.compat;

import net.minecraft.world.item.Tier;
import zzik2.barched.extra.spears.config.spears.SpearAttributeDataConfigManager;
import zzik2.barched.extra.spears.data.RegistryData;
import zzik2.barched.extra.spears.data.creativetab.TabData;
import zzik2.barched.extra.spears.data.items.SpearAttributeData;
import zzik2.barched.extra.spears.data.items.SpearData;
import zzik2.barched.extra.spears.data.items.SpearItemFactory;
import zzik2.barched.extra.spears.data.material.MaterialData;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@SuppressWarnings("all")
public interface ICompatMod {

    String getModID();

    /**
     * Returns a list of materials containing the underlying SpearAttributeData.
     * This value is used as the default when the config file is not present.
     */
    List<RegistryData> getDefaultMaterials();

    /**
     * Returns a list of materials that have SpearAttributeData loaded from config applied.
     */
    default List<RegistryData> getMaterials() {
        List<RegistryData> defaults = getDefaultMaterials();
        List<RegistryData> result = new ArrayList<>();
        for (RegistryData registryData : defaults) {
            MaterialData<Supplier<Tier>, String> materialData = registryData.materialData();
            SpearData<SpearAttributeData, SpearItemFactory> spearData = registryData.spearData();
            TabData tabData = registryData.tabData();
            SpearAttributeData configuredData = SpearAttributeDataConfigManager.loadOrCreate(getModID(), materialData.materialName(), spearData.spearAttributeData());
            result.add(new RegistryData(materialData, new SpearData<>(configuredData, spearData.spearItemFactory()), tabData));
        }
        return result;
    }
}
