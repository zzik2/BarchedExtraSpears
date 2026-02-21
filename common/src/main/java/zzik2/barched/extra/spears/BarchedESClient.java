package zzik2.barched.extra.spears;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import zzik2.barched.extra.spears.registry.RegisterFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class BarchedESClient {

    public static final Map<Item, ModelResourceLocation> SPEAR_MODEL_MAP = new HashMap<>();
    public static final Map<Item, ModelResourceLocation> SPEAR_IN_HAND_MODEL_MAP = new HashMap<>();

    public static void init() {
        for (String nameSpace : RegisterFactory.MODID_TO_SPEARS.keySet()) {
            for (RegistrySupplier<Item> item : RegisterFactory.MODID_TO_SPEARS.get(nameSpace)) {
                Objects.requireNonNull(item.get());
                String materialName = RegisterFactory.getMaterialNameByItem(item);
                Objects.requireNonNull(materialName);
                SPEAR_MODEL_MAP.put(item.get(), barchedES$spear(nameSpace, materialName));
                SPEAR_IN_HAND_MODEL_MAP.put(item.get(), barchedES$spear_in_hand(nameSpace, materialName));
            }
        }
    }

    public static ModelResourceLocation barchedES$spear(String nameSpace, String materialName) {
        return ModelResourceLocation.inventory(ResourceLocation.tryBuild(nameSpace, materialName + "_spear"));
    }

    public static ModelResourceLocation barchedES$spear_in_hand(String nameSpace, String materialName) {
        return ModelResourceLocation.inventory(ResourceLocation.tryBuild(nameSpace, materialName + "_spear_in_hand"));
    }
}
