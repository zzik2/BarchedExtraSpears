package zzik2.barched.extra.spears.data.items;

import org.jetbrains.annotations.Nullable;

public record SpearData<A, B>(A spearAttributeData, @Nullable B spearItemFactory) {
}
