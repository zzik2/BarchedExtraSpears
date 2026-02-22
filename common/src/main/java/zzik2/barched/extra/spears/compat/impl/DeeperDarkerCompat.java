package zzik2.barched.extra.spears.compat.impl;

import com.kyanite.deeperdarker.content.DDItems;
import com.kyanite.deeperdarker.util.DDCreativeTab;
import dev.architectury.platform.Platform;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import zzik2.barched.extra.spears.compat.ICompatMod;
import zzik2.barched.extra.spears.data.RegistryData;
import zzik2.barched.extra.spears.data.creativetab.TabData;
import zzik2.barched.extra.spears.data.items.SpearAttributeData;
import zzik2.barched.extra.spears.data.items.SpearData;
import zzik2.barched.extra.spears.data.material.MaterialData;
import zzik2.zreflex.reflection.ZReflectionTool;

import java.util.List;
import java.util.function.Supplier;

//this mod is not addon friendly...
public class DeeperDarkerCompat implements ICompatMod {

    @Override
    public String getModID() {
        return "deeperdarker";
    }

    @Override
    public List<RegistryData> getDefaultMaterials() {
        Supplier<? extends Item> WARDEN_SWORD;
        Supplier<CreativeModeTab> TAB;

        if (Platform.isFabric()) {
            WARDEN_SWORD = () -> DDItems.WARDEN_SWORD;
            TAB = () -> DDCreativeTab.DEEPER_AND_DARKER;
        } else {
            WARDEN_SWORD = ZReflectionTool.getStaticFieldValue(DDItems.class, "WARDEN_SWORD");
            TAB = ZReflectionTool.getStaticFieldValue(DDCreativeTab.class, "DEEPER_DARKER");
        }

        Supplier<Tier> WARDEN = () -> {
            return ((TieredItem) WARDEN_SWORD.get()).getTier();
        };

        return List.of(
                //1.20x stronger than netherite
                new RegistryData(
                        new MaterialData<>(WARDEN, "warden"),
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
                        new TabData<>(TAB, null, WARDEN_SWORD)
                )
        );
    }
}
