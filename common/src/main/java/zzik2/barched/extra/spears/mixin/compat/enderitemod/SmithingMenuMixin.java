package zzik2.barched.extra.spears.mixin.compat.enderitemod;

import dev.architectury.registry.registries.RegistrySupplier;
import net.enderitemc.enderitemod.EnderiteMod;
import net.enderitemc.enderitemod.misc.EnderiteDataComponents;
import net.enderitemc.enderitemod.tools.EnderiteTools;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SmithingMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import zzik2.barched.extra.spears.compat.CompatMods;
import zzik2.barched.extra.spears.registry.RegisterFactory;

@Mixin(SmithingMenu.class)
public abstract class SmithingMenuMixin extends ItemCombinerMenu {

    public SmithingMenuMixin(@Nullable MenuType<?> menuType, int i, Inventory inventory, ContainerLevelAccess containerLevelAccess) {
        super(menuType, i, inventory, containerLevelAccess);
    }

    @Inject(method = "createResult", at = @At("TAIL"))
    private void barchedES$createResult(CallbackInfo ci) {
        ItemStack spear = this.inputSlots.getItem(1);
        ItemStack pearls1 = this.inputSlots.getItem(0);
        ItemStack pearls2 = this.inputSlots.getItem(2);

        RegistrySupplier<Item> ENDERITE_SPEAR = RegisterFactory.getRegisteredSpear(CompatMods.ENDERITEMOD, "enderite");
        if (ENDERITE_SPEAR == null) return;

        if (spear.is(ENDERITE_SPEAR.get()) && (pearls1.is(Items.ENDER_PEARL) || pearls1.isEmpty()) && (pearls2.is(Items.ENDER_PEARL))) {
            ItemStack newSpear = spear.copy();
            int pearls = pearls1.getCount() + pearls2.getCount();
            int teleport_charge = spear.getOrDefault(EnderiteDataComponents.TELEPORT_CHARGE.get(), 0);

            if (teleport_charge < EnderiteMod.CONFIG.tools.maxTeleportCharge) {
                teleport_charge = Math.min(teleport_charge + pearls, EnderiteMod.CONFIG.tools.maxTeleportCharge);
                newSpear.set(EnderiteDataComponents.TELEPORT_CHARGE.get(), teleport_charge);
                this.resultSlots.setItem(0, newSpear);
            } else {
                this.resultSlots.clearContent();
            }
        }
    }

    @Inject(method = "mayPickup", at = @At("TAIL"), cancellable = true)
    private void barchedES$mayPickup(Player player, boolean bl, CallbackInfoReturnable<Boolean> cir) {
        ItemStack inputStack = this.inputSlots.getItem(1);
        ItemStack stack = this.resultSlots.getItem(0);

        RegistrySupplier<Item> ENDERITE_SPEAR = RegisterFactory.getRegisteredSpear(CompatMods.ENDERITEMOD, "enderite");
        if (ENDERITE_SPEAR == null) return;

        if (stack.is(ENDERITE_SPEAR.get())) {
            cir.setReturnValue(inputStack.getOrDefault(EnderiteDataComponents.TELEPORT_CHARGE.get(), 0) < EnderiteMod.CONFIG.tools.maxTeleportCharge);
        }
    }

    @Inject(method = "onTake", at = @At("HEAD"))
    private void barchedES$onTake(Player player, ItemStack itemStack, CallbackInfo ci) {
        ItemStack spear = this.inputSlots.getItem(1);
        ItemStack pearls1 = this.inputSlots.getItem(0);
        ItemStack pearls2 = this.inputSlots.getItem(2);

        RegistrySupplier<Item> ENDERITE_SPEAR = RegisterFactory.getRegisteredSpear(CompatMods.ENDERITEMOD, "enderite");
        if (ENDERITE_SPEAR == null) return;

        if (spear.is(ENDERITE_SPEAR.get()) && (pearls1.is(Items.ENDER_PEARL) || pearls1.isEmpty()) && pearls2.is(Items.ENDER_PEARL)) {
            int amountToSubstract = pearls1.getCount() + pearls2.getCount();
            int tp_charge = spear.getOrDefault(EnderiteDataComponents.TELEPORT_CHARGE.get(), 0);
            int allowableSubstract = EnderiteMod.CONFIG.tools.maxTeleportCharge - tp_charge;
            amountToSubstract = Math.min(allowableSubstract, amountToSubstract);

            int half = amountToSubstract/2;
            int subtract1 = Math.min(half, pearls1.getCount());
            int subtract2 = Math.min(amountToSubstract - subtract1, pearls2.getCount());
            subtract1 = Math.min(amountToSubstract - subtract2, pearls1.getCount());
            this.barchedES$decrement(0, subtract1 - 1);
            this.barchedES$decrement(2, subtract2 - 1);
        }
    }

    @Unique
    private void barchedES$decrement(int i, int amount) {
        ItemStack itemStack = this.inputSlots.getItem(i);
        if (!itemStack.isEmpty()) {
            itemStack.shrink(amount);
            this.inputSlots.setItem(i, itemStack);
        }
    }
}
