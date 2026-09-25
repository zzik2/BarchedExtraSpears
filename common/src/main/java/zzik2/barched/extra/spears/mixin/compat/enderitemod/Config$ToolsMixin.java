package zzik2.barched.extra.spears.mixin.compat.enderitemod;

import net.enderitemc.enderitemod.config.Config;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import zzik2.zreflex.mixin.ModifyAccess;

@Mixin(Config.Tools.class)
public class Config$ToolsMixin {

    @ModifyAccess(access = Opcodes.ACC_PUBLIC)
    private float enderiteSpearAD = 6.0F;
}
