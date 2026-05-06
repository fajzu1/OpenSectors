package io.github.fajzu.shared.sector;

import org.jetbrains.annotations.NotNull;

public final class SectorFactory {
    public static Sector create(
        final @NotNull String id,
        final @NotNull SectorType type,
        final int minX,
        final int maxX,
        final int minZ,
        final int maxZ) {
        return new Sector(
            id,
            type,
            minX,
            maxX,
            minZ,
            maxZ
        );
    }
}
