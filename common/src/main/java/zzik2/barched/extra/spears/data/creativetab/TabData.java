package zzik2.barched.extra.spears.data.creativetab;

import net.minecraft.world.item.CreativeModeTab;

import java.util.function.Supplier;

public record TabData<A, B>(Supplier<CreativeModeTab> tabSupplier, Supplier<A> beforeAddSupplier, Supplier<B> afterAddSupplier) {
}
