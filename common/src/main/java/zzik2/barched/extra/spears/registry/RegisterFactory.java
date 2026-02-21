package zzik2.barched.extra.spears.registry;

import dev.architectury.platform.Platform;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;
import zzik2.barched.bridge.item.Item$PropertiesBridge;
import zzik2.barched.extra.spears.BarchedES;
import zzik2.barched.extra.spears.compat.CompatMods;
import zzik2.barched.extra.spears.compat.ICompatMod;
import zzik2.barched.extra.spears.data.RegistryData;
import zzik2.barched.extra.spears.data.creativetab.TabData;
import zzik2.barched.extra.spears.data.items.SpearAttributeData;
import zzik2.barched.extra.spears.data.items.SpearData;
import zzik2.barched.extra.spears.data.items.SpearItemFactory;
import zzik2.barched.extra.spears.data.material.MaterialData;
import zzik2.barched.extra.spears.wrapper.ICreativeTabOutput;
import zzik2.barched.minecraft.world.item.SpearItem;
import zzik2.zreflex.enumeration.ZEnumTool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public class RegisterFactory {

    // ModID to Spears
    @SuppressWarnings("all")
    public static final Map<String, List<RegistrySupplier<Item>>> MODID_TO_SPEARS = new HashMap<>();

    // Item to RegistryData
    @SuppressWarnings("all")
    public static final Map<RegistrySupplier<Item>, RegistryData> ITEM_TO_REGISTRYDATA = new HashMap<>();

    private RegisterFactory() {}

    public static void init() {
        for (CompatMods compatMod : CompatMods.values()) {
            ICompatMod iCompatMod = compatMod.getCompatMod();
            String modID = iCompatMod.getModID();
            boolean isModLoaded = Platform.isModLoaded(modID);
            boolean isEnabled = compatMod.isEnabled();

            if (!isEnabled) {
                BarchedES.LOGGER.info("Mod found. but register Spears is disabled: {}", modID);
                continue;
            }

            if (!isModLoaded) {
                BarchedES.LOGGER.warn("Mod not found. Ignored: {}", modID);
                continue;
            }

            BarchedES.LOGGER.info("Mod found. Trying register Spears for: {}", modID);
            DeferredRegister<Item> ITEMS = DeferredRegister.create(modID, Registries.ITEM);
            int materialCount = 0;

            for (RegistryData registryData : iCompatMod.getMaterials()) {
                materialCount++;
                MaterialData<Tier, String> materialData = registryData.materialData();
                SpearData<SpearAttributeData, SpearItemFactory> spearData = registryData.spearData();
                TabData tabData = registryData.tabData();

                Tier material = materialData.material();
                String materialName = materialData.materialName();
                SpearAttributeData spearAttributeData = spearData.spearAttributeData();
                SpearItemFactory spearItemFactory = spearData.spearItemFactory() != null ? spearData.spearItemFactory() : SpearItem::new;

                Tiers TIER = ZEnumTool.addConstant(Tiers.class, modID.toUpperCase() + "$" + materialName.toUpperCase(),
                        new Class<?>[]{TagKey.class, int.class, float.class, float.class, int.class, Supplier.class},
                        material.getIncorrectBlocksForDrops(),
                        material.getUses(),
                        material.getSpeed(),
                        material.getAttackDamageBonus(),
                        material.getEnchantmentValue(),
                        (Supplier<Ingredient>) material::getRepairIngredient
                );

                RegistrySupplier<Item> item = ITEMS.register(materialName + "_spear", () -> spearItemFactory.create(
                        material,
                        ((Item$PropertiesBridge) new Item.Properties()).spear(
                                TIER,
                                spearAttributeData.swingSeconds(),
                                spearAttributeData.kineticDamageMultiplier(),
                                spearAttributeData.delaySeconds(),
                                spearAttributeData.damageCondDurationSeconds(),
                                spearAttributeData.damageCondMinSpeed(),
                                spearAttributeData.knockbackCondDurationSeconds(),
                                spearAttributeData.knockbackCondMinSpeed(),
                                spearAttributeData.dismountCondDurationSeconds(),
                                spearAttributeData.dismountCondMinRelativeSpeed()
                        )
                ));

                MODID_TO_SPEARS.computeIfAbsent(modID, k -> new ArrayList<>()).add(item);
                ITEM_TO_REGISTRYDATA.put(item, registryData);
            }

            ITEMS.register();
            BarchedES.LOGGER.info("Successfully registered Spears to {}. Found material count: {}", modID, materialCount);
        }
    }

    public static void registerItemsToInvTab(CreativeModeTab creativeModeTab, ICreativeTabOutput output) {
        ResourceLocation currentResourceLocation = BuiltInRegistries.CREATIVE_MODE_TAB.getKey(creativeModeTab);
        if (currentResourceLocation == null) return;
        AtomicBoolean log = new AtomicBoolean(false);

        ITEM_TO_REGISTRYDATA.forEach((key, value) -> {
            TabData tabData = value.tabData();
            if (tabData == null) return;
            CreativeModeTab targetTab = tabData.tabSupplier().get();
            if (targetTab == null) return;

            ResourceLocation targetResourceLocation = BuiltInRegistries.CREATIVE_MODE_TAB.getKey(targetTab);
            if (targetResourceLocation == null) return;

            String currentNamespace = currentResourceLocation.getNamespace();
            String currentPath = currentResourceLocation.getPath();

            String targetNamespace = targetResourceLocation.getNamespace();
            String targetPath = targetResourceLocation.getPath();

            if (currentNamespace.equals(targetNamespace) && currentPath.equals(targetPath)) {
                if (!log.get()) {
                    log.set(true);
                    BarchedES.LOGGER.info("Register Spears to CreativeMode Tab: {}", currentResourceLocation);
                }

                Item item = key.get();
                Item afterAdd = tabData.afterAddSupplier().get();
                Item beforeAdd = tabData.beforeAddSupplier().get();

                if (afterAdd != null && item != null) output.after(new ItemStack(afterAdd), new ItemStack(item));
                if (beforeAdd != null && item != null) output.before(new ItemStack(beforeAdd), new ItemStack(item));
                if (afterAdd == null && beforeAdd == null && item != null) output.accept(item);
            }
        });
    }


    public static @Nullable String getMaterialNameByItem(RegistrySupplier<Item> item) {
        if (ITEM_TO_REGISTRYDATA.containsKey(item)) {
            return ITEM_TO_REGISTRYDATA.get(item).materialData().materialName();
        }
        return null;
    }
}
