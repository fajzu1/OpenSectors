package io.github.fajzu.shared.sector;

import com.google.inject.Singleton;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class SectorService {

    private final Map<String, Sector> sectors = new ConcurrentHashMap<>();

    private final String currentSector;

    public SectorService(final @NotNull String currentSector) {
        this.currentSector = currentSector;
    }

    public Sector find(final @NotNull String id) {
        return this.sectors.get(id);
    }

    public Sector find(final @NotNull SectorType type) {
        return this.sectors
            .values()
            .stream()
            .filter(sector -> sector.sectorType() == type)
            .filter(sector -> sector.statistics().isOnline())
            .findFirst()
            .orElse(null);
    }

    public void create(final @NotNull String id,
                       final @NotNull Sector sector) {
        this.sectors.put(id, sector);
    }

    public String currentSectorId() {
        return this.currentSector;
    }

    public Sector currentSector() {
        return this.sectors.get(currentSector);
    }

    public Map<String, Sector> sectors() {
        return this.sectors;
    }
}
