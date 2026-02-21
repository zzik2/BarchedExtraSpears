package zzik2.barched.extra.spears.neoforge.wrapper.impl;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import zzik2.barched.extra.spears.wrapper.ICreativeTabOutput;

public class NeoForgeCreativeTabOutput implements ICreativeTabOutput {

    private final BuildCreativeModeTabContentsEvent entries;

    public NeoForgeCreativeTabOutput(BuildCreativeModeTabContentsEvent event) {
        this.entries = event;
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
        entries.insertBefore(existStack, newStack, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
    }

    @Override
    public void after(ItemStack existStack, ItemStack newStack) {
        entries.insertAfter(existStack, newStack, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
    }
}
