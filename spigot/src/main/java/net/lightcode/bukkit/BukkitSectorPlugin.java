package net.lightcode.bukkit;

import net.lightcode.NetworkService;
import net.lightcode.bukkit.listener.redis.PacketPlayerSendMessageListener;
import net.lightcode.bukkit.listener.redis.PacketSectorConfigurationResponseListener;
import net.lightcode.bukkit.listener.redis.PacketSectorInformationUpdateListener;
import net.lightcode.bukkit.listener.redis.PacketUserSynchronizeDataListener;
import net.lightcode.bukkit.region.service.BukkitSectorRegionService;
import net.lightcode.bukkit.runnable.SectorInformationUpdateRunnable;
import net.lightcode.bukkit.user.service.UserService;
import net.lightcode.NmsService;
import net.lightcode.bukkit.command.ChannelCommand;
import net.lightcode.bukkit.listener.*;
import net.lightcode.configuration.impl.DatabaseConfiguration;
import net.lightcode.configuration.impl.MessagesConfiguration;
import net.lightcode.configuration.impl.SectorConfiguration;
import net.lightcode.configuration.service.ConfigurationService;
import net.lightcode.bukkit.helper.ChatHelper;
import net.lightcode.bukkit.helper.NmsHelper;
import net.lightcode.packet.impl.SectorConfigurationRequestPacket;
import net.lightcode.bukkit.runnable.ActionBarNotificationRunnable;
import net.lightcode.bukkit.runnable.ScoreboardUpdateRunnable;
import net.lightcode.bukkit.runnable.SectorBorderUpdateRunnable;
import net.lightcode.bukkit.scoreboard.ScoreboardPlayerService;
import net.lightcode.bukkit.scoreboard.SpawnScoreboard;
import net.lightcode.sector.service.SectorService;
import net.lightcode.sector.type.SectorType;
import net.lightcode.bukkit.transfer.PlayerTransferService;
import net.lightcode.updater.UpdaterService;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

public final class BukkitSectorPlugin extends JavaPlugin {

    private BukkitLogger logger;

    private NetworkService networkService;
    private PlayerTransferService transferService;

    private SectorService sectorService;
    private UserService userService;
    private ScoreboardPlayerService scoreboardPlayerService;
    private BukkitSectorRegionService bukkitSectorRegionService;

    private MessagesConfiguration messagesConfiguration;
    private DatabaseConfiguration databaseConfiguration;
    private SectorConfiguration sectorConfiguration;

    private NmsService nmsService;

    @Override
    public void onLoad() {
        this.logger = new BukkitLogger(Logger.getLogger("opensectors-spigot"));

        ConfigurationService configurationService = new ConfigurationService(this.getDataFolder().getAbsolutePath());

        this.messagesConfiguration = configurationService.loadConfiguration(MessagesConfiguration.class);
        this.sectorConfiguration = configurationService.loadConfiguration(SectorConfiguration.class);
        this.databaseConfiguration = configurationService.loadConfiguration(DatabaseConfiguration.class);
        this.logger.fine("Configurations loaded: Sector, Database, Messages.");

        this.logger.log("OpenSectors loading...");
    }

    @Override
    public void onEnable() {
        String text = "\n\n" +
                " ██████╗ ██████╗ ███████╗███╗   ██╗███████╗███████╗ ██████╗████████╗ ██████╗ ██████╗ ███████╗\n" +
                "██╔═══██╗██╔══██╗██╔════╝████╗  ██║██╔════╝██╔════╝██╔════╝╚══██╔══╝██╔═══██╗██╔══██╗██╔════╝\n" +
                "██║   ██║██████╔╝█████╗  ██╔██╗ ██║███████╗█████╗  ██║        ██║   ██║   ██║██████╔╝███████╗\n" +
                "██║   ██║██╔═══╝ ██╔══╝  ██║╚██╗██║╚════██║██╔══╝  ██║        ██║   ██║   ██║██╔══██╗╚════██║\n" +
                "╚██████╔╝██║     ███████╗██║ ╚████║███████║███████╗╚██████╗   ██║   ╚██████╔╝██║  ██║███████║\n" +
                " ╚═════╝ ╚═╝     ╚══════╝╚═╝  ╚═══╝╚══════╝╚══════╝ ╚═════╝   ╚═╝    ╚═════╝ ╚═╝  ╚═╝╚══════╝\n";
        this.logger.log(text);

        this.nmsService = NmsHelper.findNmsService(this);
        this.logger.log("NMS service found: " + (this.nmsService != null));

        if (this.nmsService == null) {
            this.getServer().shutdown();
            return;
        }

        this.sectorService = new SectorService(sectorConfiguration.currentSector());
        this.bukkitSectorRegionService = new BukkitSectorRegionService(this);

        this.transferService = new PlayerTransferService(this);
        this.userService = new UserService();

        this.networkService = new NetworkService(databaseConfiguration.redisHost(), databaseConfiguration.redisPort(), databaseConfiguration.redisPassword());
        this.networkService.setPacketSender(this.sectorService.currentSectorId());
        this.logger.log("NetworkService started with sender: " + this.sectorService.currentSectorId());

        this.networkService.subscribe(this.sectorService.currentSectorId(), new PacketSectorConfigurationResponseListener(this));
        this.networkService.subscribe(this.sectorService.currentSectorId(), new PacketUserSynchronizeDataListener(this.networkService, this.userService));
        this.networkService.subscribe("sectors", new PacketSectorInformationUpdateListener(this.sectorService));
        this.networkService.subscribe("sectors", new PacketPlayerSendMessageListener(this));
        this.logger.log("Messenger subscriptions done.");

        this.checkForUpdates();

        this.initListeners();
        this.logger.fine("Bukkit listeners registered");

        new SectorInformationUpdateRunnable(this).runTaskTimerAsynchronously(this, 50L, 50L);
        new ActionBarNotificationRunnable(this).runTaskTimer(this, 1L, 1L);
        new SectorBorderUpdateRunnable(this).runTaskTimer(this, 65L, 65L);

        this.networkService.publish("bridge", new SectorConfigurationRequestPacket());
        this.logger.log("Sector configuration request packet published to bridge.");

        this.logger.fine("Plugin enabled successfully.");
    }

    @Override
    public void onDisable() {
        this.logger.log("Plugin disabling...");

        if (this.sectorService.currentSector().sectorType().equals(SectorType.SPAWN)) {
            this.logger.log("Current sector is SPAWN. Handling player transfer or kick.");

            this.sectorService.findAvailableSpawnSector().ifPresentOrElse(sector -> {
                this.logger.fine("Available spawn sector found: " + sector.id());

                for (Player player : this.getServer().getOnlinePlayers()) {
                    this.userService.find(player.getUniqueId())
                            .ifPresent(user -> this.transferService.connect(player, user, sector));
                }
            }, () -> {
                this.logger.warning("No available spawn sector found! Kicking all players.");

                for (Player player : this.getServer().getOnlinePlayers()) {
                    player.kickPlayer(ChatHelper.colored(this.messagesConfiguration.spawnSectorNotFoundMessage()));
                }
            });
        }

        if (this.networkService == null) return;

        this.networkService.shutdown();

        this.logger.fine("NetworkService shutdown completed.");
        this.logger.fine("Plugin disabled successfully.");
    }

    public void registerSector() {
        this.logger.log("Registering sector...");

        if (this.sectorService.currentSector() == null) {
            this.logger.severe("Current sector is null! Shutting down server.");

            this.getServer().shutdown();
            return;
        }

        if (!this.sectorService.currentSector().sectorType().equals(SectorType.SPAWN)) {
            this.logger.log("Current sector is not SPAWN. Skipping scoreboard setup.");
            return;
        }

        this.scoreboardPlayerService = new ScoreboardPlayerService(new SpawnScoreboard(this.sectorService, this.messagesConfiguration));

        this.getServer().getPluginManager().registerEvents(new PlayerScoreboardListener(this.scoreboardPlayerService), this);

        new ScoreboardUpdateRunnable(this).runTaskTimer(this, 50L, 50L);

        this.logger.fine("Scoreboard system successfully initialized");

        this.getCommand("channel").setExecutor(new ChannelCommand(this.userService, this.sectorService, this.transferService, this.messagesConfiguration));

        this.logger.fine("OpenSectors successfully initialized and ready to use.");
    }

    public PlayerTransferService transferService() {
        return this.transferService;
    }

    public UserService userService() {
        return this.userService;
    }

    public NetworkService messengerService() {
        return this.networkService;
    }

    public SectorService sectorService() {
        return this.sectorService;
    }

    public BukkitSectorRegionService bukkitSectorRegionService() {
        return this.bukkitSectorRegionService;
    }

    private void checkForUpdates() {
        UpdaterService updaterService = new UpdaterService(this.getDescription().getVersion(),this.getLogger());

        updaterService.check(newestVersion -> this.logger.log(Level.WARNING, List.of(
                "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~",
                "⚠️ A new version of OpenSectors! ⚠️",
                "Current version: " + this.getDescription().getVersion(),
                "Available version: " + newestVersion,
                "",
                "Download at: https://github.com/fajzu1/OpenSectors",
                "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
        )));
    }

    private void initListeners() {
        Stream.of(
                new GuiInteractListener(),
                new PlayerJoinListener(this),
                new PlayerRespawnListener(this),
                new PlayerDeathListener(this),
                new PlayerSectorInteractListener(this),
                new PlayerLoginListener(this),
                new PlayerChatListener(this.networkService),
                new PlayerQuitListener(this.userService),
                new PlayerMoveListener(this.sectorService, this.userService, this.bukkitSectorRegionService, this.transferService, this.messagesConfiguration),
                new PlayerTeleportListener(this.sectorService, this.userService, this.bukkitSectorRegionService, this.transferService, this.messagesConfiguration)
        ).forEach(listener -> {
            this.getServer().getPluginManager().registerEvents(listener, this);
            this.logger.fine("Registered listener: " + listener.getClass().getSimpleName());
        });

        this.logger.log("All event listeners initialized.");
    }

    public MessagesConfiguration messagesConfiguration() {
        return this.messagesConfiguration;
    }

    public NmsService nmsService() {
        return this.nmsService;
    }

    public ScoreboardPlayerService scoreboardPlayerService() {
        return this.scoreboardPlayerService;
    }

    public BukkitLogger logger() {
        return this.logger;
    }
}
