package zzik2.barched.extra.spears.data.creativetab;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public record TabData(Supplier<CreativeModeTab> tabSupplier, Supplier<Item> beforeAddSupplier, Supplier<Item> afterAddSupplier) {
}
