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
import org.jetbrains.annotations.NotNull;
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

            init(iCompatMod);
        }
    }

    public static void init(ICompatMod iCompatMod) {
        String modID = iCompatMod.getModID();
        BarchedES.LOGGER.info("Mod found. Trying register Spears for: {}", modID);
        DeferredRegister<Item> ITEMS = DeferredRegister.create(modID, Registries.ITEM);

        for (String clazz : iCompatMod.getPreInitClasses()) {
            try {
                Class.forName(clazz);
                BarchedES.LOGGER.info("Try pre initialize of {}: {}", modID, clazz);
            } catch (ClassNotFoundException e) {
                BarchedES.LOGGER.info("Can't pre initialize of {}: {}", modID, clazz);
            }
        }

        int materialCount = 0;

        for (RegistryData registryData : iCompatMod.getMaterials()) {
            materialCount++;
            MaterialData<Supplier<Tier>, String> materialData = registryData.materialData();
            SpearData<SpearAttributeData, SpearItemFactory> spearData = registryData.spearData();
            TabData tabData = registryData.tabData();

            Tier material = materialData.material().get();
            String materialName = materialData.materialName();
            SpearAttributeData spearAttributeData = spearData.spearAttributeData();
            SpearItemFactory spearItemFactory = spearData.spearItemFactory() != null ? spearData.spearItemFactory() : SpearItem::new;

            Tier tier = new Tier() {
                @Override
                public int getUses() {
                    return materialData.uses() != null ? materialData.uses().get() : material.getUses();
                }

                @Override
                public float getSpeed() {
                    return material.getSpeed();
                }

                @Override
                public float getAttackDamageBonus() {
                    return materialData.attackDamage() != null ? materialData.attackDamage().get() - 1.0F : material.getAttackDamageBonus();
                }

                @Override
                public @NotNull TagKey getIncorrectBlocksForDrops() {
                    return material.getIncorrectBlocksForDrops();
                }

                @Override
                public int getEnchantmentValue() {
                    return material.getEnchantmentValue();
                }

                @Override
                public @NotNull Ingredient getRepairIngredient() {
                    return material.getRepairIngredient();
                }
            };

            RegistrySupplier<Item> item = ITEMS.register(materialName + "_spear", () -> spearItemFactory.create(
                    tier,
                    ((Item$PropertiesBridge) new Item.Properties()).spear(
                            tier,
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

    public static void registerItemsToInvTab(CreativeModeTab creativeModeTab, ICreativeTabOutput output) {
        ResourceLocation currentResourceLocation = BuiltInRegistries.CREATIVE_MODE_TAB.getKey(creativeModeTab);
        if (currentResourceLocation == null) return;
        AtomicBoolean log = new AtomicBoolean(false);

        ITEM_TO_REGISTRYDATA.forEach((key, value) -> {
            TabData<? extends Item, ? extends Item> tabData = value.tabData();
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

                Item item = key != null ? key.get() : null;
                Item afterAdd = tabData.afterAddSupplier() != null ? tabData.afterAddSupplier().get() : null;
                Item beforeAdd = tabData.beforeAddSupplier() != null ? tabData.beforeAddSupplier().get() : null;

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

    public static @Nullable RegistrySupplier<Item> getRegisteredSpear(CompatMods compatMods, String materialName) {
        return getRegisteredSpear(compatMods.getCompatMod().getModID(), materialName);
    }

    public static @Nullable RegistrySupplier<Item> getRegisteredSpear(String modID, String materialName) {
        if (!MODID_TO_SPEARS.containsKey(modID)) return null;
        List<RegistrySupplier<Item>> spearsSupplier = MODID_TO_SPEARS.get(modID);
        RegistrySupplier<Item> returnValue = null;
        for (RegistrySupplier<Item> spearSupplier : spearsSupplier) {
            if (!ITEM_TO_REGISTRYDATA.containsKey(spearSupplier)) continue;
            RegistryData registryData = ITEM_TO_REGISTRYDATA.get(spearSupplier);
            if (registryData.materialData().materialName().equals(materialName)) {
                returnValue = spearSupplier;
                break;
            }
        }
        return returnValue;
    }
}
