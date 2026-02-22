package zzik2.barched.extra.spears.data.material;

import org.jetbrains.annotations.Nullable;

public record MaterialData<A, B>(A material, B materialName, @Nullable Integer uses, @Nullable Float attackDamage) {

    public MaterialData(A material, B materialName) {
        this(material, materialName, null, null);
    }
}
