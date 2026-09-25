package zzik2.barched.extra.spears.compat.spears.aether;

import com.aetherteam.aether.item.combat.abilities.weapon.HolystoneWeapon;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import zzik2.barched.minecraft.world.item.SpearItem;

public class HolystoneSpearItem extends SpearItem implements HolystoneWeapon {

    public HolystoneSpearItem(Tier tier, Properties properties) {
        super(tier, properties);
    }

    @Override
    public boolean hurtEnemy(ItemStack itemStack, LivingEntity target, LivingEntity attacker) {
        this.dropAmbrosium(target, attacker);
        return super.hurtEnemy(itemStack, target, attacker);
    }
}
