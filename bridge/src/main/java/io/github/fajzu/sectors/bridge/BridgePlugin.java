package io.github.fajzu.sectors.bridge;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import io.github.fajzu.shared.configuration.ConfigurationService;
import io.github.fajzu.shared.configuration.internal.DatabaseConfiguration;
import io.github.fajzu.shared.configuration.internal.ProxyConfiguration;
import io.github.fajzu.shared.network.NetworkService;
import io.github.fajzu.shared.sector.SectorService;
import io.github.fajzu.sectors.bridge.listener.PlayerServerConnectListener;
import io.github.fajzu.sectors.bridge.listener.redis.PacketPlayerConnectSectorListener;
import io.github.fajzu.sectors.bridge.listener.redis.PacketSectorConfigurationRequestListener;
import io.github.fajzu.shared.updater.UpdaterService;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

@Plugin(
    id = "opensectors-bridge",
    name = "opensectors-bridge",
    version = "4.0",
    authors = "fajzu"
)
public class BridgePlugin {

    private final ProxyServer server;
    private final Logger logger;
    private final Injector injector;
    private final NetworkService networkService;
    private final SectorService sectorService;
    private final ConfigurationService configurationService;
    private final ProxyConfiguration proxyConfiguration;
    private final DatabaseConfiguration databaseConfiguration;

    @Inject
    public BridgePlugin(final @NotNull ProxyServer server,
                        final @NotNull Logger logger,
                        final @NotNull Injector injector,
                        final @NotNull NetworkService networkService,
                        final @NotNull SectorService sectorService,
                        final @NotNull ConfigurationService configurationService,
                        final @NotNull ProxyConfiguration proxyConfiguration,
                        final @NotNull DatabaseConfiguration databaseConfiguration) {
        this.server = server;
        this.logger = logger;
        this.networkService = networkService;
        this.sectorService = sectorService;
        this.configurationService = configurationService;
        this.proxyConfiguration = proxyConfiguration;
        this.databaseConfiguration = databaseConfiguration;
        this.injector = injector.createChildInjector(new BridgeModule());

        final String text = "\n\n" +
            " ██████╗ ██████╗ ███████╗███╗   ██╗███████╗███████╗ ██████╗████████╗ ██████╗ ██████╗ ███████╗\n" +
            "██╔═══██╗██╔══██╗██╔════╝████╗  ██║██╔════╝██╔════╝██╔════╝╚══██╔══╝██╔═══██╗██╔══██╗██╔════╝\n" +
            "██║   ██║██████╔╝█████╗  ██╔██╗ ██║███████╗█████╗  ██║        ██║   ██║   ██║██████╔╝███████╗\n" +
            "██║   ██║██╔═══╝ ██╔══╝  ██║╚██╗██║╚════██║██╔══╝  ██║        ██║   ██║   ██║██╔══██╗╚════██║\n" +
            "╚██████╔╝██║     ███████╗██║ ╚████║███████║███████╗╚██████╗   ██║   ╚██████╔╝██║  ██║███████║\n" +
            " ╚═════╝ ╚═╝     ╚══════╝╚═╝  ╚═══╝╚══════╝╚══════╝ ╚═════╝   ╚═╝    ╚═════╝ ╚═╝  ╚═╝╚══════╝\n";
        this.logger.info(text);
    }

    @Subscribe
    public void onProxyInitialization(final ProxyInitializeEvent event) {
        this.injector.injectMembers(this);

        this.logger.info("Loaded ProxyConfiguration: sectors count " + this.proxyConfiguration.sectors().size());

        // todo: implement classScanner to register events and packetHandlers

        this.checkForUpdates(BridgePlugin.class.getAnnotation(Plugin.class).version());

        this.logger.info("Bridge initialization complete!");
    }

    private void checkForUpdates(final @NotNull String currentVersion) {
        final UpdaterService updaterService = new UpdaterService(currentVersion, java.util.logging.Logger.getAnonymousLogger());

        updaterService.check(newestVersion -> this.logger.warn(
            "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
            "⚠️ A new version of OpenSectors! ⚠️\n" +
            "Current version: " + currentVersion + "\n" +
            "Available version: " + newestVersion + "\n\n" +
            "Download at: https://github.com/fajzu1/OpenSectors\n" +
            "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
        ));
    }

    public NetworkService networkService() {
        return this.networkService;
    }

    public SectorService sectorService() {
        return this.sectorService;
    }

    public ProxyServer server() {
        return this.server;
    }

    public Logger logger() {
        return this.logger;
    }
}
