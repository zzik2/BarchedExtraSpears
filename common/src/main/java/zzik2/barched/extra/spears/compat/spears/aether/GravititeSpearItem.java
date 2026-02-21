package zzik2.barched.extra.spears.compat.spears.aether;

import com.aetherteam.aether.item.combat.abilities.weapon.GravititeWeapon;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import zzik2.barched.minecraft.world.item.SpearItem;

public class GravititeSpearItem extends SpearItem implements GravititeWeapon {

    public GravititeSpearItem(Tier tier, Properties properties) {
        super(tier, properties);
    }

    @Override
    public boolean hurtEnemy(ItemStack itemStack, LivingEntity target, LivingEntity attacker) {
        this.launchEntity(target, attacker);
        return super.hurtEnemy(itemStack, target, attacker);
    }
}
