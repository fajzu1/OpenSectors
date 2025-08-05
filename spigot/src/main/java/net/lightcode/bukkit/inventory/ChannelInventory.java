package net.lightcode.bukkit.inventory;

import net.lightcode.bukkit.helper.ChatHelper;
import net.lightcode.bukkit.helper.CustomHeadHelper;
import net.lightcode.bukkit.user.service.UserService;
import net.lightcode.configuration.impl.MessagesConfiguration;
import net.lightcode.bukkit.inventory.api.GuiWindow;
import net.lightcode.bukkit.inventory.api.builder.ItemBuilder;
import net.lightcode.sector.Sector;
import net.lightcode.sector.service.SectorService;
import net.lightcode.sector.type.SectorType;
import net.lightcode.bukkit.transfer.PlayerTransferService;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class ChannelInventory {

    private final Player player;
    private final GuiWindow guiWindow;

    public ChannelInventory(Player player, SectorService sectorService, UserService userService, MessagesConfiguration messagesConfiguration, PlayerTransferService playerTransferService) {
        this.player = player;

        this.guiWindow = new GuiWindow("&7Kanaly...", 1);

        int i = 0;

        for (Sector sector : sectorService.sectors().values()) {
            if (!sector.sectorType().equals(SectorType.SPAWN)) continue;

            ItemStack itemStack = (sector.isOnline() ? CustomHeadHelper.create("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGEyZjljNzYxZmMxMzFkYmViZDA3M2IwYjFkZDdkMWJhZWExOTFjZTlkMzNjNDljM2FjYTk0NDhiMWI2YjY4NCJ9fX0=") : CustomHeadHelper.create("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWIwZTA3NjMyMmZjOWFmNzk1OTJlYjg1MmNhOGM3YzQ1YmIyYzNjZWFiYzNjMGU4YTZhMWUwNGI0Y2UzZDM0YiJ9fX0="));
            ItemBuilder sectorItem = new ItemBuilder(itemStack).name("&7Sektor &a" + sector.id());

            sectorItem.lore(sector.isOnline() ?
                    Arrays.asList(
                            "",
                            ChatHelper.colored("&7Online: &a" + sector.players()),
                            ChatHelper.colored("&7TPS: &a" + ChatHelper.formatTps(sector.tps())),
                            "")
                    :
                    Arrays.asList(
                            "",
                            ChatHelper.colored("&cSektor jest offline")));

            sectorItem.lore(sector.id().equals(sectorService.currentSectorId()) ? "&eZnajdujesz sie na tym kanale" : "&eKliknij aby polaczyc sie z kanalem");

            this.guiWindow.setItem(i, sectorItem.build(), (event -> {
                if (sectorService.currentSector().id().equals(sector.id())) {
                    player.sendMessage(ChatHelper.colored(messagesConfiguration.playerAlreadyConnectedMessage()));
                    return;
                }

                if (!sector.isOnline()) {
                    player.sendMessage(ChatHelper.colored(messagesConfiguration.sectorIsOfflineMessage()));
                    return;
                }

                userService.find(player.getUniqueId()).ifPresent(user -> playerTransferService.connect(player, user, sector));
            }));
            i++;
        }

    }

    public void openInventory() {
        this.guiWindow.open(this.player);
    }

}