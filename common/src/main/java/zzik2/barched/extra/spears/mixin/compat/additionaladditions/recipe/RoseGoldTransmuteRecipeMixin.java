package zzik2.barched.extra.spears.mixin.compat.additionaladditions.recipe;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.level.Level;
import one.dqu.additionaladditions.misc.RoseGoldTransmuteRecipe;
import one.dqu.additionaladditions.registry.AAItems;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import zzik2.barched.Barched;
import zzik2.barched.extra.spears.compat.CompatMods;
import zzik2.barched.extra.spears.registry.RegisterFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

//TODO: find better way than Overwrite
@Mixin(RoseGoldTransmuteRecipe.class)
public class RoseGoldTransmuteRecipeMixin {

    /**
     * @author
     * @reason
     */
    @Dynamic
    @Overwrite
    public boolean matches(CraftingInput recipeInput, Level level) {
        final Item[] ironEquipment = {
                Items.IRON_SWORD,
                Items.IRON_SHOVEL,
                Items.IRON_PICKAXE,
                Items.IRON_AXE,
                Items.IRON_HOE,
                Items.IRON_HELMET,
                Items.IRON_CHESTPLATE,
                Items.IRON_LEGGINGS,
                Items.IRON_BOOTS,
                Items.IRON_HORSE_ARMOR,
                Barched.Items.IRON_SPEAR
        };

        int roseGoldCount = 0;
        int ironEquipmentCount = 0;
        for (int i = 0; i < recipeInput.size(); i++) {
            ItemStack stack = recipeInput.getItem(i);
            if (!stack.isEmpty()) {
                if (stack.is(AAItems.ROSE_GOLD_INGOT.get())) {
                    roseGoldCount++;
                    continue;
                }

                if (Arrays.stream(ironEquipment).anyMatch(stack::is)) {
                    ironEquipmentCount++;
                    continue;
                }

                return false;
            }
        }

        return roseGoldCount == 1 && ironEquipmentCount == 1;
    }

    /**
     * @author
     * @reason
     */
    @Dynamic
    @Overwrite
    public ItemStack assemble(CraftingInput recipeInput, HolderLookup.Provider provider) {
        ItemStack itemStack = recipeInput.items().stream().filter(item -> !item.isEmpty() && !item.is(AAItems.ROSE_GOLD_INGOT.get())).findFirst().orElse(ItemStack.EMPTY);
        if (itemStack.isEmpty()) {
            return ItemStack.EMPTY;
        }

        final Map<Item, Item> transmuteMap = new HashMap<>();

        transmuteMap.put(Items.IRON_SWORD, AAItems.ROSE_GOLD_SWORD.get());
        transmuteMap.put(Items.IRON_SHOVEL, AAItems.ROSE_GOLD_SHOVEL.get());
        transmuteMap.put(Items.IRON_PICKAXE, AAItems.ROSE_GOLD_PICKAXE.get());
        transmuteMap.put(Items.IRON_AXE, AAItems.ROSE_GOLD_AXE.get());
        transmuteMap.put(Items.IRON_HOE, AAItems.ROSE_GOLD_HOE.get());
        transmuteMap.put(Items.IRON_HELMET, AAItems.ROSE_GOLD_HELMET.get());
        transmuteMap.put(Items.IRON_CHESTPLATE, AAItems.ROSE_GOLD_CHESTPLATE.get());
        transmuteMap.put(Items.IRON_LEGGINGS, AAItems.ROSE_GOLD_LEGGINGS.get());
        transmuteMap.put(Items.IRON_BOOTS, AAItems.ROSE_GOLD_BOOTS.get());
        transmuteMap.put(Items.IRON_HORSE_ARMOR, AAItems.ROSE_GOLD_HORSE_ARMOR.get());
        RegistrySupplier<Item> ROSE_GOLD_SPEAR = RegisterFactory.getRegisteredSpear(CompatMods.ADDITIONALADDITIONS.getCompatMod().getModID(), "rose_gold");
        if (ROSE_GOLD_SPEAR != null) transmuteMap.put(Barched.Items.IRON_SPEAR, ROSE_GOLD_SPEAR.get());

        Item resultItem = transmuteMap.get(itemStack.getItem());
        if (resultItem == null) {
            return ItemStack.EMPTY;
        }

        return itemStack.transmuteCopy(resultItem);
    }
}