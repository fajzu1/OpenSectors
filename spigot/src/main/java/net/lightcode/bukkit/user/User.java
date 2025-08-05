package net.lightcode.bukkit.user;

import net.lightcode.bukkit.BukkitSectorPlugin;
import net.lightcode.bukkit.event.PlayerLoadDataEvent;
import net.lightcode.bukkit.event.PlayerSaveDataEvent;
import net.lightcode.bukkit.helper.ChatHelper;
import net.lightcode.bukkit.helper.SerializeHelper;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.function.Consumer;

public class User {

    private final String name;
    private final UUID uuid;

    private String serializedData, serializedLocation, gameMode;

    private int heldSlot;
    private long redirectTime,transferCooldown;

    public User(String name,UUID uuid) {
        this.name = name;
        this.uuid = uuid;
    }

    public void loadData(Player player, BukkitSectorPlugin plugin) {
        if (this.serializedData == null || this.serializedLocation == null || this.gameMode == null) {
            player.kickPlayer(ChatHelper.colored(plugin.messagesConfiguration().playerDataNotFoundMessage()));
            return;
        }

        PlayerLoadDataEvent event = new PlayerLoadDataEvent(player, this);
        plugin.getServer().getPluginManager().callEvent(event);

        if (event.isCancelled()) return;

        try {
            player.teleport(SerializeHelper.deserializeLocation(this.serializedLocation));
            player.getInventory().setHeldItemSlot(heldSlot);
            player.setGameMode(GameMode.valueOf(gameMode));

            Object nbtCompound = plugin.nmsService().nbtConverter().convertStringToNBTCompound(this.serializedData);

            plugin.nmsService().data().loadData(player, nbtCompound);
        } catch (Exception exception) {
            player.kickPlayer(ChatHelper.colored(plugin.messagesConfiguration().playerDataNotFoundMessage()));
            plugin.getLogger().severe("Failed to load player data for " + player.getName());
        }
    }

    public void saveData(Player player, BukkitSectorPlugin plugin) {
        PlayerSaveDataEvent event = new PlayerSaveDataEvent(player, this);
        plugin.getServer().getPluginManager().callEvent(event);

        if (event.isCancelled()) return;

        this.serializedLocation = SerializeHelper.serializeLocation(player.getLocation());
        this.serializedData = plugin.nmsService().data().saveData(player);

        this.gameMode = player.getGameMode().name();
        this.heldSlot = player.getInventory().getHeldItemSlot();
    }

    public long redirectTime() {
        return this.redirectTime;
    }

    public boolean isRedirecting() {
        return this.redirectTime + 5000L > System.currentTimeMillis();
    }

    public void setRedirecting(final boolean redirecting) {
        this.redirectTime = redirecting ? System.currentTimeMillis() : 0L;
    }

    public boolean isTransferCooldown() {
        return this.transferCooldown + 5000L > System.currentTimeMillis();
    }

    public void setTransferCooldown(final boolean transfer) {
        this.transferCooldown = transfer ? System.currentTimeMillis() : 0L;
    }

    public String name() {
        return this.name;
    }

    public UUID uuid() {
        return this.uuid;
    }

}
