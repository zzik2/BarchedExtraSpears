package zzik2.barched.extra.spears.wrapper;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public interface ICreativeTabOutput {

    void accept(ItemStack itemStack);

    void accept(ItemLike itemLike);

    void before(ItemStack existStack, ItemStack newStack);

    void after(ItemStack existStack, ItemStack newStack);
}
