package zzik2.barched.extra.spears.compat.impl;

import net.enderitemc.enderitemod.EnderiteMod;
import net.enderitemc.enderitemod.materials.EnderiteMaterial;
import net.enderitemc.enderitemod.tools.EnderiteTools;
import net.minecraft.world.item.Item;
import zzik2.barched.extra.spears.compat.ICompatMod;
import zzik2.barched.extra.spears.compat.spears.enderitemod.Config$Tools;
import zzik2.barched.extra.spears.compat.spears.enderitemod.EnderiteSpear;
import zzik2.barched.extra.spears.data.RegistryData;
import zzik2.barched.extra.spears.data.creativetab.TabData;
import zzik2.barched.extra.spears.data.items.SpearAttributeData;
import zzik2.barched.extra.spears.data.items.SpearData;
import zzik2.barched.extra.spears.data.material.MaterialData;

import java.util.ArrayList;
import java.util.List;

public class EnderiteModCompat implements ICompatMod {

    @Override
    public String getModID() {
        return "enderitemod";
    }

    @Override
    public List<RegistryData> getDefaultMaterials() {
        try {
            Class.forName("net.enderitemc.enderitemod.config.Config");
            Class.forName("net.enderitemc.enderitemod.config.ConfigLoader");
            Class.forName("net.enderitemc.enderitemod.EnderiteMod");
        } catch (Exception ignored) {}

        return List.of(
                //1.325x stronger than netherite
                new RegistryData(
                        new MaterialData<>(() -> EnderiteMaterial.ENDERITE, "enderite", null, Config$Tools.enderiteSpearAD),
                        new SpearData<>(new SpearAttributeData(
                                0.8679F,
                                1.59F,
                                0.3019F,
                                3.3125F,
                                5.283F,
                                7.2875F,
                                3.8491F,
                                11.5938F,
                                3.4717F
                        ), EnderiteSpear::new),
                        new TabData<>(EnderiteMod.ENDERITE_TAB, null, EnderiteTools.ENDERITE_SWORD)
                )
        );
    }

    @Override
    public List<String> getMixins() {
        List<String> mixins = new ArrayList<>();
        mixins.add("compat.enderitemod.Config$ToolsMixin");
        mixins.add("compat.enderitemod.SmithingMenuMixin");
        return mixins;
    }
}
