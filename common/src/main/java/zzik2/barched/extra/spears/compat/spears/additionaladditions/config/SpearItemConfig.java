package zzik2.barched.extra.spears.compat.spears.additionaladditions.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record SpearItemConfig(int attackDamage, int durability) {

    public static final Codec<SpearItemConfig> CODEC = RecordCodecBuilder.create(
            spearItemConfigInstance -> spearItemConfigInstance.group(
                    Codec.intRange(0, Integer.MAX_VALUE).fieldOf("attack_damage").forGetter(SpearItemConfig::attackDamage),
                    Codec.intRange(0, Integer.MAX_VALUE).fieldOf("durability").forGetter(SpearItemConfig::durability)
            ).apply(spearItemConfigInstance, SpearItemConfig::new)
    );
}
