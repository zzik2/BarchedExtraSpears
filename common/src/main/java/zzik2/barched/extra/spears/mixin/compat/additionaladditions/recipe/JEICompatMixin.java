package zzik2.barched.extra.spears.mixin.compat.additionaladditions.recipe;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.item.Item;
import one.dqu.additionaladditions.misc.JEICompat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import zzik2.barched.Barched;
import zzik2.barched.extra.spears.compat.CompatMods;
import zzik2.barched.extra.spears.registry.RegisterFactory;

import java.util.HashMap;
import java.util.Map;

@Mixin(JEICompat.class)
public class JEICompatMixin {

    @ModifyVariable(method = "roseGoldTransmuteRecipe", at = @At("STORE"), ordinal = 0, require = 0)
    private Map<Item, Item> barchedES$modifyRoseGoldTransmuteRecipe(Map<Item, Item> original) {
        Map<Item, Item> extended = new HashMap<>(original);
        RegistrySupplier<Item> ROSE_GOLD_SPEAR = RegisterFactory.getRegisteredSpear(CompatMods.ADDITIONALADDITIONS.getCompatMod().getModID(), "rose_gold");
        if (ROSE_GOLD_SPEAR != null) extended.put(Barched.Items.IRON_SPEAR, ROSE_GOLD_SPEAR.get());
        return extended;
    }
}
