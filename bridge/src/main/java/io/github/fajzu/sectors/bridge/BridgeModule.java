package io.github.fajzu.sectors.bridge;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import io.github.fajzu.shared.configuration.ConfigurationService;
import io.github.fajzu.shared.configuration.internal.DatabaseConfiguration;
import io.github.fajzu.shared.configuration.internal.ProxyConfiguration;
import io.github.fajzu.shared.network.NetworkService;
import io.github.fajzu.shared.sector.Sector;
import io.github.fajzu.shared.sector.SectorService;

public class BridgeModule extends AbstractModule {

    @Provides
    @Singleton
    public ConfigurationService configurationService() {
        return new ConfigurationService("plugins/opensectors-bridge");
    }

    @Provides
    @Singleton
    public ProxyConfiguration proxyConfiguration(final ConfigurationService configurationService) {
        return configurationService.loadConfiguration(ProxyConfiguration.class);
    }

    @Provides
    @Singleton
    public DatabaseConfiguration databaseConfiguration(final ConfigurationService configurationService) {
        return configurationService.loadConfiguration(DatabaseConfiguration.class);
    }

    @Provides
    @Singleton
    public SectorService sectorService(final ProxyConfiguration proxyConfiguration) {
        final SectorService sectorService = new SectorService("bridge");

        proxyConfiguration.sectors().forEach((id, sectorWrapper) -> {
            sectorService.create(id, new Sector(
                    sectorWrapper.name(),
                    sectorWrapper.sectorType(),
                    sectorWrapper.minX(),
                    sectorWrapper.maxX(),
                    sectorWrapper.minZ(),
                    sectorWrapper.maxZ()
            ));
        });

        return sectorService;
    }

    @Provides
    @Singleton
    public NetworkService networkService(final DatabaseConfiguration databaseConfiguration) {
        return new NetworkService(
                databaseConfiguration.natsHost(),
                "bridge"
        );
    }
}
