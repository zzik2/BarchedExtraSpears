package zzik2.barched.extra.spears.data.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import zzik2.barched.minecraft.world.item.SpearItem;

@FunctionalInterface
public interface SpearItemFactory {
    SpearItem create(Tier tier, Item.Properties properties);
}
