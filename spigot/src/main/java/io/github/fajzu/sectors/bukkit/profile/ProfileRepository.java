package io.github.fajzu.sectors.bukkit.profile;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.github.fajzu.nms.api.NmsService;
import io.github.fajzu.sectors.bukkit.event.PlayerLoadDataEvent;
import io.github.fajzu.sectors.bukkit.event.PlayerSaveDataEvent;
import io.github.fajzu.sectors.bukkit.helper.ChatHelper;
import io.github.fajzu.sectors.bukkit.helper.SerializeHelper;
import io.github.fajzu.shared.configuration.ConfigurationService;
import io.github.fajzu.shared.configuration.internal.MessagesConfiguration;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

@Singleton
public final class ProfileRepository {

    private final Plugin plugin;
    private final NmsService nmsService;

    @Inject
    public ProfileRepository(final @NotNull Plugin plugin,
                             final @NotNull NmsService nmsService) {
        this.plugin = plugin;
        this.nmsService = nmsService;
    }

    public void loadData(final @NotNull Player player,
                         final @NotNull Profile profile) {
        final PlayerLoadDataEvent event = new PlayerLoadDataEvent(player, profile);
        this.plugin.getServer().getPluginManager().callEvent(event);

        if (event.isCancelled()) {
            Logger.getAnonymousLogger().info("PlayerLoadDataEvent " + profile.name() + " event cancelled");
            return;
        }

        final Location location = (Location) SerializeHelper.deserialize(profile.serializedLocation());

        player.teleportAsync(location).thenAccept(bool -> {
            player.getInventory().setHeldItemSlot(profile.heldSlot());
            player.setGameMode(GameMode.valueOf(profile.gameMode()));

            final Object nbtCompound = this.nmsService.nbtConverter().convertStringToNBTCompound(profile.serializedData());

            this.nmsService.data().loadData(player, nbtCompound);
        });
    }

    public void saveData(final @NotNull Player player,
                         final @NotNull Profile profile) {
        final PlayerSaveDataEvent event = new PlayerSaveDataEvent(player, profile);
        this.plugin.getServer().getPluginManager().callEvent(event);

        if (event.isCancelled()) {
            Logger.getAnonymousLogger().info("PlayerSaveDataEvent " + profile.name() + " event cancelled");
            return;
        }

        profile.serializedLocation(SerializeHelper.serialize(player.getLocation()));
        profile.serializedData(this.nmsService.data().saveData(player));
        profile.gameMode(player.getGameMode().name());
        profile.heldSlot(player.getInventory().getHeldItemSlot());
    }
}
