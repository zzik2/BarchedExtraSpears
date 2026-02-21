package zzik2.barched.extra.spears.compat.spears.aether;

import com.aetherteam.aether.item.combat.abilities.weapon.ZaniteWeapon;
import net.minecraft.world.item.Tier;
import zzik2.barched.minecraft.world.item.SpearItem;

public class ZaniteSpearItem extends SpearItem implements ZaniteWeapon {

    public ZaniteSpearItem(Tier tier, Properties properties) {
        super(tier, properties);
    }
}
