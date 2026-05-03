package io.github.fajzu.sectors.bukkit;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import io.github.fajzu.nms.api.NmsService;
import io.github.fajzu.sectors.bukkit.helper.NmsHelper;
import io.github.fajzu.sectors.bukkit.scoreboard.ScoreboardPlayerService;
import io.github.fajzu.sectors.bukkit.scoreboard.SpawnScoreboard;
import io.github.fajzu.shared.configuration.ConfigurationService;
import io.github.fajzu.shared.configuration.internal.DatabaseConfiguration;
import io.github.fajzu.shared.configuration.internal.MessagesConfiguration;
import io.github.fajzu.shared.configuration.internal.SectorConfiguration;
import io.github.fajzu.shared.network.NetworkService;
import io.github.fajzu.shared.sector.SectorService;
import org.bukkit.plugin.Plugin;

import java.util.logging.Logger;

public class BukkitInjectorModule extends AbstractModule {

    private final Plugin plugin;

    public BukkitInjectorModule(final Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    protected void configure() {
        this.bind(Plugin.class).toInstance(this.plugin);
        this.bind(Logger.class).toInstance(this.plugin.getLogger());
    }

    @Provides
    @Singleton
    public ConfigurationService provideConfigurationService() {
        return new ConfigurationService(this.plugin.getDataFolder().getAbsolutePath());
    }

    @Provides
    @Singleton
    public MessagesConfiguration provideMessagesConfiguration(final ConfigurationService configurationService) {
        return configurationService.loadConfiguration(MessagesConfiguration.class);
    }

    @Provides
    @Singleton
    public SectorConfiguration provideSectorConfiguration(final ConfigurationService configurationService) {
        return configurationService.loadConfiguration(SectorConfiguration.class);
    }

    @Provides
    @Singleton
    public DatabaseConfiguration provideDatabaseConfiguration(final ConfigurationService configurationService) {
        return configurationService.loadConfiguration(DatabaseConfiguration.class);
    }

    @Provides
    @Singleton
    public SectorService provideSectorService(final SectorConfiguration sectorConfiguration) {
        return new SectorService(sectorConfiguration.currentSector());
    }

    @Provides
    @Singleton
    public NetworkService provideNetworkService(final DatabaseConfiguration databaseConfiguration,
                                                 final SectorService sectorService) {
        return new NetworkService(
                databaseConfiguration.natsHost(),
                sectorService.currentSectorId()
        );
    }

    @Provides
    @Singleton
    public NmsService provideNmsService() {
        return NmsHelper.findNmsService(this.plugin);
    }

    @Provides
    @Singleton
    public ScoreboardPlayerService provideScoreboardPlayerService(final SectorService sectorService,
                                                                   final MessagesConfiguration messagesConfiguration) {
        return new ScoreboardPlayerService(new SpawnScoreboard(sectorService, messagesConfiguration));
    }
}
