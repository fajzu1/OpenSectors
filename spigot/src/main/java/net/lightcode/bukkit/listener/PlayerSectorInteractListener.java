package net.lightcode.bukkit.listener;

import net.lightcode.bukkit.BukkitSectorPlugin;
import net.lightcode.bukkit.helper.ChatHelper;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class PlayerSectorInteractListener implements Listener {

    private final BukkitSectorPlugin plugin;

    public PlayerSectorInteractListener(BukkitSectorPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    void onBlockBreak(BlockBreakEvent event) {
        final Player player = event.getPlayer();
        final Location location = event.getBlock().getLocation();

        if (this.plugin.bukkitSectorRegionService().distance(location) <= 30) {
            event.setCancelled(true);

            player.sendMessage(ChatHelper.colored(this.plugin.messagesConfiguration().cannotBreakBlockNearSectorMessage()));
            return;
        }

        this.plugin.userService().find(player.getUniqueId()).ifPresent(user -> {
            if (!user.isRedirecting()) return;

            event.setCancelled(true);
        });

    }

    @EventHandler
    void onBlockPlace(final BlockPlaceEvent event) {
        final Player player = event.getPlayer();
        final Location location = event.getBlock().getLocation();

        if (this.plugin.bukkitSectorRegionService().distance(location) <= 30) {
            event.setCancelled(true);

            player.sendMessage(ChatHelper.colored(this.plugin.messagesConfiguration().cannotPlaceBlockNearSectorMessage()));
            return;
        }

        this.plugin.userService().find(player.getUniqueId()).ifPresent(user -> {
            if (!user.isRedirecting()) return;

            event.setCancelled(true);
        });
    }

    @EventHandler
    void onBucketFill(PlayerBucketFillEvent event) {
        final Player player = event.getPlayer();
        final Location location = event.getBlockClicked().getLocation();

        if (event.getBlockClicked() == null) return;

        if (this.plugin.bukkitSectorRegionService().distance(location) <= 30) {
            event.setCancelled(true);
            return;
        }

        this.plugin.userService().find(player.getUniqueId()).ifPresent(user -> {
            if (!user.isRedirecting()) return;

            event.setCancelled(true);
        });
    }

    @EventHandler
    void onBucketEmpty(PlayerBucketEmptyEvent event) {
        final Player player = event.getPlayer();
        final Location location = event.getBlockClicked().getLocation();

        if (event.getBlockClicked() == null) return;

        if (this.plugin.bukkitSectorRegionService().distance(location) <= 30) {
            event.setCancelled(true);
            return;
        }

        this.plugin.userService().find(player.getUniqueId()).ifPresent(user -> {
            if (!user.isRedirecting()) return;

            event.setCancelled(true);
        });
    }


    @EventHandler
    void onBlockExplode(BlockExplodeEvent event) {
        final Location location = event.getBlock().getLocation();

        if (this.plugin.bukkitSectorRegionService().distance(location) <= 30) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    void onEntityExplode(EntityExplodeEvent event) {
        if (this.plugin.bukkitSectorRegionService().distance(event.getLocation()) <= 30) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    void onDropItem(PlayerDropItemEvent event) {
        final Player player = event.getPlayer();
        final Location location = player.getLocation();

        if (this.plugin.bukkitSectorRegionService().distance(location) <= 30) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    void onEntityDamage(final EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) return;

        this.plugin.userService().find(event.getEntity().getUniqueId()).ifPresent(user -> {
            if (!user.isRedirecting()) return;

            event.setCancelled(true);
        });
    }

    @EventHandler
    void onProjectileLaunch(final ProjectileLaunchEvent event) {
        if (!(event.getEntity().getShooter() instanceof Player)) return;

        this.plugin.userService().find(event.getEntity().getUniqueId()).ifPresent(user -> {
            if (!user.isRedirecting()) return;

            event.setCancelled(true);
        });
    }

    @EventHandler
    void onPickupItem(PlayerPickupItemEvent event) {
        this.plugin.userService().find(event.getPlayer().getUniqueId()).ifPresent(user -> {
            if (!user.isRedirecting()) return;

            event.setCancelled(true);
        });
    }

    @EventHandler
    void onOpen(final InventoryOpenEvent event) {
        this.plugin.userService().find(event.getPlayer().getUniqueId()).ifPresent(user -> {
            if (!user.isRedirecting()) return;

            event.setCancelled(true);
        });
    }

    @EventHandler
    void onClick(final InventoryClickEvent event) {
        this.plugin.userService().find(event.getWhoClicked().getUniqueId()).ifPresent(user -> {
            if (!user.isRedirecting()) return;

            event.setCancelled(true);
        });
    }

    @EventHandler
    void onDrag(final InventoryDragEvent event) {
        this.plugin.userService().find(event.getWhoClicked().getUniqueId()).ifPresent(user -> {
            if (!user.isRedirecting()) return;

            event.setCancelled(true);
        });
    }

    @EventHandler
    void onInteract(final InventoryInteractEvent event) {
        this.plugin.userService().find(event.getWhoClicked().getUniqueId()).ifPresent(user -> {
            if (!user.isRedirecting()) return;

            event.setCancelled(true);
        });
    }
}