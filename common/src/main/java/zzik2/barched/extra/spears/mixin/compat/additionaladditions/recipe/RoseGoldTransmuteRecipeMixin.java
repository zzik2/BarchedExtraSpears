package zzik2.barched.extra.spears.mixin.compat.additionaladditions.recipe;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.item.Item;
import one.dqu.additionaladditions.misc.RoseGoldTransmuteRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import zzik2.barched.Barched;
import zzik2.barched.extra.spears.compat.CompatMods;
import zzik2.barched.extra.spears.registry.RegisterFactory;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Stream;

@Mixin(RoseGoldTransmuteRecipe.class)
public class RoseGoldTransmuteRecipeMixin {

    @Redirect(method = "matches(Lnet/minecraft/world/item/crafting/CraftingInput;Lnet/minecraft/world/level/Level;)Z", at = @At(value = "INVOKE", target = "Ljava/util/Arrays;stream([Ljava/lang/Object;)Ljava/util/stream/Stream;"))
    private <T> Stream<Item> barchedES$extendEquipment(T[] array) {
        Item[] extended = (Item[]) Arrays.copyOf(array, array.length + 1);
        extended[array.length] = Barched.Items.IRON_SPEAR;
        return Arrays.stream(extended);
    }

    @Redirect(method = "assemble(Lnet/minecraft/world/item/crafting/CraftingInput;Lnet/minecraft/core/HolderLookup$Provider;)Lnet/minecraft/world/item/ItemStack;", at = @At(value = "INVOKE", target = "Ljava/util/Map;get(Ljava/lang/Object;)Ljava/lang/Object;"))
    private Object barchedES$extendTransmute(Map<Item, Item> instance, Object key) {
        Item result = instance.get(key);
        if (result == null && key == Barched.Items.IRON_SPEAR) {
            RegistrySupplier<Item> spear = RegisterFactory.getRegisteredSpear(CompatMods.ADDITIONALADDITIONS, "rose_gold");
            return spear != null ? spear.get() : null;
        }
        return result;
    }
}