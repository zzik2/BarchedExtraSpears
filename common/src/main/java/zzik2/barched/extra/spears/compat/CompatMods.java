package zzik2.barched.extra.spears.compat;

import zzik2.barched.extra.spears.compat.impl.BronzeCompat;
import zzik2.barched.extra.spears.compat.impl.ParadiseLostCompat;

@SuppressWarnings("all")
public enum CompatMods {

    BRONZE(new BronzeCompat()),
    PARADISE_LOST(new ParadiseLostCompat()),
    ;

    private final ICompatMod compatMod;
    private final boolean enabled;

    CompatMods(ICompatMod compatMod) {
        this(compatMod, true);
    }

    CompatMods(ICompatMod compatMod, boolean enabled) {
        this.compatMod = compatMod;
        this.enabled = enabled;
    }

    public ICompatMod getCompatMod() {
        return compatMod;
    }

    public boolean isEnabled() {
        return enabled;
    }
}
