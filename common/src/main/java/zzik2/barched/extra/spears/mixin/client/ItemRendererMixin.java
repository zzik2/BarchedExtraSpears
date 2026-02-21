package zzik2.barched.extra.spears.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.ItemModelShaper;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import zzik2.barched.extra.spears.BarchedESClient;

@Mixin(value = ItemRenderer.class, priority = 900)
public class ItemRendererMixin {

    @Shadow @Final private ItemModelShaper itemModelShaper;

    @Unique private ItemDisplayContext barchedES$itemDisplayContext;
    @Unique private ItemStack barchedES$itemStack;

    @Inject(method = "render", at = @At("HEAD"))
    private void barchedES$captureSignature(ItemStack itemStack, ItemDisplayContext itemDisplayContext, boolean bl, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j, BakedModel bakedModel, CallbackInfo ci) {
        this.barchedES$itemStack = itemStack;
        this.barchedES$itemDisplayContext = itemDisplayContext;
    }

    @ModifyVariable(method = "render", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;pushPose()V", shift = At.Shift.AFTER, ordinal = 0), argsOnly = true, index = 8)
    private BakedModel barchedES$render(BakedModel value) {
        boolean bl2 = barchedES$itemDisplayContext == ItemDisplayContext.GUI || barchedES$itemDisplayContext == ItemDisplayContext.GROUND || barchedES$itemDisplayContext == ItemDisplayContext.FIXED;
        if (bl2) {
            if (BarchedESClient.SPEAR_MODEL_MAP.containsKey(barchedES$itemStack.getItem())) {
                ModelResourceLocation model = BarchedESClient.SPEAR_MODEL_MAP.get(barchedES$itemStack.getItem());
                return this.itemModelShaper.getModelManager().getModel(model);
            }
        }
        return value;
    }

    @WrapOperation(method = "getModel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemModelShaper;getItemModel(Lnet/minecraft/world/item/ItemStack;)Lnet/minecraft/client/resources/model/BakedModel;"))
    private BakedModel barchedES$getModel(ItemModelShaper instance, ItemStack bakedModel, Operation<BakedModel> original) {
        if (BarchedESClient.SPEAR_IN_HAND_MODEL_MAP.containsKey(bakedModel.getItem())) {
            ModelResourceLocation model = BarchedESClient.SPEAR_IN_HAND_MODEL_MAP.get(bakedModel.getItem());
            return this.itemModelShaper.getModelManager().getModel(model);
        }
        return original.call(instance, bakedModel);
    }
}
