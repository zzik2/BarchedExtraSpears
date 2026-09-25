package zzik2.barched.extra.spears.mixin;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.VanillaItemTagsProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import zzik2.barched.Barched;
import zzik2.barched.extra.spears.registry.RegisterFactory;

import java.util.concurrent.CompletableFuture;

@Mixin(value = VanillaItemTagsProvider.class, priority = 900)
public abstract class VanillaItemTagsProviderMixin extends ItemTagsProvider {

    public VanillaItemTagsProviderMixin(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> completableFuture, CompletableFuture<TagLookup<Block>> completableFuture2) {
        super(packOutput, completableFuture, completableFuture2);
    }

    @Inject(method = "addTags", at = @At("TAIL"))
    private void barchedES$addTags(HolderLookup.Provider provider, CallbackInfo ci) {
        for (String nameSpace : RegisterFactory.MODID_TO_SPEARS.keySet()) {
            for (RegistrySupplier<Item> item : RegisterFactory.MODID_TO_SPEARS.get(nameSpace)) {
                this.tag(Barched.ItemTags.SPEARS).add(item.get());
            }
        }
    }
}
