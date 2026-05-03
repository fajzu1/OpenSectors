package io.github.fajzu.sectors.bukkit.profile;

import java.util.UUID;

public class Profile {

    private final String name;
    private final UUID uuid;

    private String serializedData, gameMode;
    private byte[] serializedLocation;
    private int heldSlot;
    private long redirectTime, transferCooldown;

    public Profile(final String name,
                   final UUID uuid) {
        this.name = name;
        this.uuid = uuid;
    }

    /*public void loadData(final Player player,
                         final Plugin plugin) {
        if (this.serializedData == null || this.serializedLocation == null || this.gameMode == null) {
            player.kick(ChatHelper.colored(plugin.messagesConfiguration().playerDataNotFoundMessage()));
            return;
        }

        final PlayerLoadDataEvent event = new PlayerLoadDataEvent(player, this);
        plugin.getServer().getPluginManager().callEvent(event);

        if (event.isCancelled()) {
            return;
        }

        final Location location = (Location) SerializeHelper.deserialize(this.serializedLocation);

        player.teleport(location);
        player.getInventory().setHeldItemSlot(this.heldSlot);
        player.setGameMode(GameMode.valueOf(this.gameMode));

        final Object nbtCompound = plugin.nmsService().nbtConverter().convertStringToNBTCompound(this.serializedData);

        plugin.nmsService().data().loadData(player, nbtCompound);
    }

    public void saveData(final Player player,
                         final BukkitSectorPluginController plugin) {
        final PlayerSaveDataEvent event = new PlayerSaveDataEvent(player, this);
        plugin.getServer().getPluginManager().callEvent(event);

        if (event.isCancelled()) {
            return;
        }

        this.serializedLocation = SerializeHelper.serialize(player.getLocation());
        this.serializedData = plugin.nmsService().data().saveData(player);

        this.gameMode = player.getGameMode().name();
        this.heldSlot = player.getInventory().getHeldItemSlot();
    }*/

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
