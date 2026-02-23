package zzik2.barched.extra.spears.compat;

import zzik2.barched.extra.spears.compat.impl.*;

@SuppressWarnings("all")
public enum CompatMods {

    BRONZE(new BronzeCompat(), true, "com.khazoda.bronze.BronzeCommon"),
    PARADISE_LOST(new ParadiseLostCompat(), true, "net.id.paradiselost.ParadiseLost"),
    PROGRESSION_REBORN(new ProgressionRebornCompat(), true, "net.legacy.progression_reborn.ProgressionReborn"),
    DRAGONLOOT(new DragonLootCompat(), true, "net.dragonloot.DragonLootMain"),
    AETHER(new AetherCompat(), true, "com.aetherteam.aether.Aether"),
    ADDITIONALADDITIONS(new AdditionalAdditionsCompat(), true, "one.dqu.additionaladditions.AdditionalAdditions"),
    DEEPERDARKER(new DeeperDarkerCompat(), true, "com.kyanite.deeperdarker.DeeperDarker"),
    ENDERITEMOD(new EnderiteModCompat(), true, "net.enderitemc.enderitemod.EnderiteMod")
    ;

    private final ICompatMod compatMod;
    private final boolean enabled;
    private final String mainClass;

    CompatMods(ICompatMod compatMod) {
        this(compatMod, true);
    }

    CompatMods(ICompatMod compatMod, boolean enabled) {
        this(compatMod, enabled, "");
    }

    CompatMods(ICompatMod compatMod, boolean enabled, String mainClass) {
        this.compatMod = compatMod;
        this.enabled = enabled;
        this.mainClass = mainClass;
    }

    public ICompatMod getCompatMod() {
        return compatMod;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public String getMainClass() {
        return mainClass;
    }

    public boolean isLoaded() {
        if (mainClass.isEmpty()) {
            return false;
        }
        try {
            Class.forName(mainClass);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
