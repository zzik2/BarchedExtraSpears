package zzik2.barched.extra.spears.compat;

@SuppressWarnings("all")
public enum CompatMods {

    ;

    private final ICompatMod compatMod;
    private final boolean enabled;

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
