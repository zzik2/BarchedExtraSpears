package zzik2.barched.extra.spears.mixin.compat.additionaladditions.config;

import com.mojang.serialization.Codec;
import one.dqu.additionaladditions.config.ConfigProperty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ConfigProperty.class)
public interface ConfigPropertyInvoker<T> {

    @Invoker("<init>")
    static <T> ConfigProperty<T> init(String key, Codec<T>codec, T defaultValue) {
        return null;
    }
}
