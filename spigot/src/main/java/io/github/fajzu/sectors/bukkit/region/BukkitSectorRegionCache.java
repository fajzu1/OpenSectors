package io.github.fajzu.sectors.bukkit.region;

import com.google.inject.Singleton;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public final class BukkitSectorRegionCache {

    private final Map<String, BukkitSectorRegion> regions = new ConcurrentHashMap<>();

    public void create(final @NotNull String name,
                       final @NotNull BukkitSectorRegion bukkitSectorRegion) {
        this.regions.put(name, bukkitSectorRegion);
    }

    public BukkitSectorRegion find(final @NotNull String name) {
        return this.regions.get(name);
    }

    public Map<String, BukkitSectorRegion> regions() {
        return this.regions;
    }
}
