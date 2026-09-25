package zzik2.barched.extra.spears.data.material;

import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public record MaterialData<A, B>(A material, B materialName, @Nullable Supplier<Integer> uses, @Nullable Supplier<Float> attackDamage) {

    public MaterialData(A material, B materialName) {
        this(material, materialName, null, null);
    }
}
