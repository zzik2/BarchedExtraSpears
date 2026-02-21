package zzik2.barched.extra.spears.mixin.client;

import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import zzik2.barched.extra.spears.BarchedESClient;

import java.util.Map;

@Mixin(value = ModelBakery.class, priority = 900)
public abstract class ModelBakeryMixin {

    @Shadow protected abstract void loadSpecialItemModelAndDependencies(ModelResourceLocation modelResourceLocation);

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiling/ProfilerFiller;popPush(Ljava/lang/String;)V", ordinal = 1))
    private void barchedES$init(BlockColors blockColors, ProfilerFiller profilerFiller, Map map, Map map2, CallbackInfo ci) {
        for (Item item : BarchedESClient.SPEAR_IN_HAND_MODEL_MAP.keySet()) {
            ModelResourceLocation model = BarchedESClient.SPEAR_IN_HAND_MODEL_MAP.get(item);
            this.loadSpecialItemModelAndDependencies(model);
        }
    }
}
