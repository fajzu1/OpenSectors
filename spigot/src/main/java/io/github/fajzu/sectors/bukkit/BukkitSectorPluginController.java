package io.github.fajzu.sectors.bukkit;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import io.github.fajzu.shared.Schedule;
import io.github.fajzu.shared.configuration.internal.MessagesConfiguration;
import io.github.fajzu.shared.network.NetworkService;
import io.github.fajzu.shared.network.packet.PacketTopic;
import io.github.fajzu.shared.network.packet.internal.SectorConfigurationRequestPacket;
import io.github.fajzu.shared.scanner.ClassScanner;
import io.github.fajzu.shared.sector.*;
import io.github.fajzu.shared.updater.UpdaterService;
import io.github.fajzu.nms.api.NmsService;
import io.github.fajzu.sectors.bukkit.helper.ChatHelper;
import io.github.fajzu.sectors.bukkit.profile.Profile;
import io.github.fajzu.sectors.bukkit.profile.ProfileCache;
import io.github.fajzu.sectors.bukkit.transfer.PlayerTransferService;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
public final class BukkitSectorPluginController implements SectorInitializationModule {

    private final Plugin plugin;

    private final Injector injector;

    private final Logger logger;
    private final NetworkService networkService;
    private final PlayerTransferService transferService;
    private final SectorService sectorService;
    private final ProfileCache profileService;
    private final MessagesConfiguration messagesConfiguration;
    private final NmsService nmsService;
    private final ClassScanner classScanner;

    @Inject
    public BukkitSectorPluginController(final @NotNull Plugin plugin,
                                        final @NotNull Injector injector,
                                        final @NotNull Logger logger,
                                        final @NotNull NetworkService networkService,
                                        final @NotNull PlayerTransferService transferService,
                                        final @NotNull SectorService sectorService,
                                        final @NotNull ProfileCache profileService,
                                        final @NotNull MessagesConfiguration messagesConfiguration,
                                        final @NotNull NmsService nmsService,
                                        final @NotNull ClassScanner classScanner) {
        this.plugin = plugin;
        this.injector = injector;
        this.logger = logger;
        this.networkService = networkService;
        this.transferService = transferService;
        this.sectorService = sectorService;
        this.profileService = profileService;
        this.messagesConfiguration = messagesConfiguration;
        this.nmsService = nmsService;
        this.classScanner = classScanner;
    }


    @Override
    public void initialize() {
        this.logger.info("NMS service found: " + (this.nmsService != null));
        this.checkForUpdates();

        this.classScanner.findClasses(getClass().getPackageName(), Listener.class).forEach(listenerClass -> {
            final SectorInitialize initialize = listenerClass.getAnnotation(SectorInitialize.class);

            if (initialize == null) {
                this.logger.severe("Registering listener failed: Listener don't have @Initialize above class: " + listenerClass.getName());
                return;
            }

            final SectorType sectorType = this.sectorService.currentSector().sectorType();
            if (Arrays.stream(initialize.type()).noneMatch(type -> type.equals(sectorType))) {
                return;
            }

            this.plugin.getServer().getPluginManager().registerEvents(this.injector.getInstance(listenerClass), this.plugin);
        });

        this.classScanner.findClasses(getClass().getPackageName(), Runnable.class).forEach(runnableClass -> {
            final SectorInitialize initialize = runnableClass.getAnnotation(SectorInitialize.class);

            if (initialize == null) {
                this.logger.severe("Registering runnable failed: Runnable (" + runnableClass.getSimpleName() + ") don't have @Initialize above class");
                return;
            }

            final SectorType sectorType = this.sectorService.currentSector().sectorType();
            if (Arrays.stream(initialize.type()).noneMatch(type -> type.equals(sectorType))) {
                return;
            }

            final Schedule schedule = runnableClass.getAnnotation(Schedule.class);
            if (schedule == null) {
                this.logger.severe(
                    "Class without annotation @Schedule"
                );
                return;
            }

            if (schedule.async()) {
                this.plugin.getServer().getScheduler().runTaskTimerAsynchronously(
                    this.plugin,
                    this.injector.getInstance(runnableClass),
                    schedule.delay(),
                    schedule.period()
                );
                return;
            }

            this.plugin.getServer().getScheduler().runTaskTimer(
                this.plugin,
                this.injector.getInstance(runnableClass),
                schedule.delay(),
                schedule.period()
            );
        });

        this.classScanner.findAnnotatedClasses(PacketTopic.class, getClass().getPackageName()).forEach(listenerClass -> {
            final PacketTopic topic = listenerClass.getAnnotation(PacketTopic.class);

            if (topic == null) {
                this.logger.severe("Registering nats listener failed: Class (" + listenerClass.getSimpleName() + ") don't have @PacketTopic above class");
                return;
            }

            this.networkService.subscribe(
                topic.value(),
                this.injector.getInstance(listenerClass)
            );
        });

        this.networkService.publish("bridge", new SectorConfigurationRequestPacket());
        this.logger.info("Plugin enabled successfully.");
    }

    @Override
    public void shutdown() {
        this.logger.info("Plugin disabling...");

        // todo: move to proxy side handling disabling spawns
        if (this.sectorService.currentSector().sectorType() == SectorType.SPAWN) {
            this.logger.info("Current sector is SPAWN. Handling player transfer or kick.");

            final Sector sector = this.sectorService.find(SectorType.SPAWN);

            if (sector == null) {
                this.logger.warning("No available spawn sector found! Kicking all players.");

                for (final Player player : this.plugin.getServer().getOnlinePlayers()) {
                    player.kick(ChatHelper.colored(this.messagesConfiguration.spawnSectorNotFoundMessage()));
                }
                return;
            }

            this.logger.info("Available spawn sector found: " + sector.id());

            for (final Player player : this.plugin.getServer().getOnlinePlayers()) {
                final Profile profile = this.profileService.find(player.getUniqueId());

                this.transferService.connect(player, profile, sector, true);
            }
        }

        this.networkService.shutdown();
    }

    private void checkForUpdates() {
        final UpdaterService updaterService = new UpdaterService(this.plugin.getDescription().getVersion(), this.plugin.getLogger());

        updaterService.check(newestVersion -> this.logger.log(Level.WARNING,
            "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
            "⚠️ A new version of OpenSectors! ⚠️\n" +
            "Current version: " + this.plugin.getDescription().getVersion() + "\n" +
            "Available version: " + newestVersion + "\n\n" +
            "Download at: https://github.com/fajzu1/OpenSectors\n" +
            "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"));
    }
}
