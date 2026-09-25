package zzik2.barched.extra.spears.compat.impl;

import com.kyanite.deeperdarker.content.DDItems;
import com.kyanite.deeperdarker.util.DDCreativeTab;
import dev.architectury.platform.Platform;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
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
        Supplier<? extends Item> SHARD;
        Supplier<CreativeModeTab> TAB;

        if (Platform.isFabric()) {
            WARDEN_SWORD = () -> DDItems.WARDEN_SWORD;
            SHARD = () -> DDItems.REINFORCED_ECHO_SHARD;
            TAB = () -> DDCreativeTab.DEEPER_AND_DARKER;
        } else {
            WARDEN_SWORD = ZReflectionTool.getStaticFieldValue(DDItems.class, "WARDEN_SWORD");
            SHARD = ZReflectionTool.getStaticFieldValue(DDItems.class, "REINFORCED_ECHO_SHARD");
            TAB = ZReflectionTool.getStaticFieldValue(DDCreativeTab.class, "DEEPER_DARKER");
        }

        TagKey<Block> WARDEN_TAG = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("deeperdarker", "incorrect_for_warden_tool"));

        //copy from WARDEN
        //TODO: find better way than this(related: neoforge NPE)
        Supplier<Tier> WARDEN_COPY = () -> {
            return new Tier() {
                @Override
                public int getUses() {
                    return 2519;
                }

                @Override
                public float getSpeed() {
                    return 10.0F;
                }

                @Override
                public float getAttackDamageBonus() {
                    return 5F;
                }

                @Override
                public TagKey<Block> getIncorrectBlocksForDrops() {
                    return WARDEN_TAG;
                }

                @Override
                public int getEnchantmentValue() {
                    return 18;
                }

                @Override
                public Ingredient getRepairIngredient() {
                    return Ingredient.of(SHARD.get());
                }
            };
        };

        return List.of(
                //1.20x stronger than netherite
                new RegistryData(
                        new MaterialData<>(WARDEN_COPY, "warden"),
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
