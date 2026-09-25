package zzik2.barched.extra.spears.fabric.wrapper.impl;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import zzik2.barched.extra.spears.wrapper.ICreativeTabOutput;

public class FabricCreativeTabOutput implements ICreativeTabOutput {

    private final FabricItemGroupEntries entries;

    public FabricCreativeTabOutput(FabricItemGroupEntries entries) {
        this.entries = entries;
    }

    @Override
    public void accept(ItemStack itemStack) {
        entries.accept(itemStack);
    }

    @Override
    public void accept(ItemLike itemLike) {
        entries.accept(itemLike);
    }

    @Override
    public void before(ItemStack existStack, ItemStack newStack) {
        entries.addBefore(existStack, newStack);
    }

    @Override
    public void after(ItemStack existStack, ItemStack newStack) {
        entries.addAfter(existStack, newStack);
    }
}
