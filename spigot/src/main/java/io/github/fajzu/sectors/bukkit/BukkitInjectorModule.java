package io.github.fajzu.sectors.bukkit;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import io.github.fajzu.nms.api.NmsService;
import io.github.fajzu.sectors.bukkit.nms.NmsPlatformValidator;
import io.github.fajzu.sectors.bukkit.nms.NmsServiceProvider;
import io.github.fajzu.sectors.bukkit.nms.NmsServiceFactory;
import io.github.fajzu.sectors.bukkit.nms.NmsVersionDetector;
import io.github.fajzu.sectors.bukkit.scoreboard.ScoreboardPlayerService;
import io.github.fajzu.sectors.bukkit.scoreboard.SpawnScoreboard;
import io.github.fajzu.shared.configuration.ConfigurationService;
import io.github.fajzu.shared.configuration.internal.DatabaseConfiguration;
import io.github.fajzu.shared.configuration.internal.MessagesConfiguration;
import io.github.fajzu.shared.configuration.internal.SectorConfiguration;
import io.github.fajzu.shared.network.NetworkService;
import io.github.fajzu.shared.sector.SectorService;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

public class BukkitInjectorModule extends AbstractModule {

    private final Plugin plugin;

    public BukkitInjectorModule(final @NotNull Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    protected void configure() {
        this.bind(Plugin.class).toInstance(this.plugin);
        this.bind(Logger.class).toInstance(this.plugin.getLogger());
    }

    @Provides
    @Singleton
    public ConfigurationService configurationService() {
        return new ConfigurationService(this.plugin.getDataFolder().getAbsolutePath());
    }

    @Provides
    @Singleton
    public MessagesConfiguration messagesConfiguration(final ConfigurationService configurationService) {
        return configurationService.loadConfiguration(MessagesConfiguration.class);
    }

    @Provides
    @Singleton
    public SectorConfiguration sectorConfiguration(final ConfigurationService configurationService) {
        return configurationService.loadConfiguration(SectorConfiguration.class);
    }

    @Provides
    @Singleton
    public DatabaseConfiguration databaseConfiguration(final ConfigurationService configurationService) {
        return configurationService.loadConfiguration(DatabaseConfiguration.class);
    }

    @Provides
    @Singleton
    public SectorService sectorService(final SectorConfiguration sectorConfiguration) {
        return new SectorService(sectorConfiguration.currentSector());
    }

    @Provides
    @Singleton
    public NetworkService networkService(final DatabaseConfiguration databaseConfiguration,
                                                 final SectorService sectorService) {
        return new NetworkService(
                databaseConfiguration.natsHost(),
                sectorService.currentSectorId()
        );
    }

    @Provides
    @Singleton
    public NmsService nmsService(final NmsServiceProvider nmsServiceProvider) {
        return nmsServiceProvider.provide(this.plugin);
    }

    @Provides
    @Singleton
    public ScoreboardPlayerService scoreboardPlayerService(final SectorService sectorService,
                                                                   final MessagesConfiguration messagesConfiguration) {
        return new ScoreboardPlayerService(new SpawnScoreboard(sectorService, messagesConfiguration));
    }
}
