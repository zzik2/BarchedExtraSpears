package zzik2.barched.extra.spears.mixin.compat.additionaladditions.config;

import one.dqu.additionaladditions.config.Config;
import one.dqu.additionaladditions.config.ConfigProperty;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import zzik2.barched.extra.spears.compat.spears.additionaladditions.config.SpearItemConfig;
import zzik2.zreflex.mixin.ModifyAccess;

@Mixin(Config.class)
public class ConfigMixin {

    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private static final ConfigProperty<SpearItemConfig> ROSE_GOLD_SPEAR = ConfigPropertyInvoker.init(
            "rose_gold/spear", SpearItemConfig.CODEC,
            new SpearItemConfig(4, 900)
    );
}
