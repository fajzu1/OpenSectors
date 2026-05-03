package io.github.fajzu.sectors.bukkit.inventory.internal;

import io.github.fajzu.sectors.bukkit.inventory.Window;
import io.github.fajzu.shared.configuration.internal.MessagesConfiguration;
import io.github.fajzu.shared.sector.Sector;
import io.github.fajzu.shared.sector.SectorService;
import io.github.fajzu.shared.sector.SectorType;
import io.github.fajzu.sectors.bukkit.helper.ChatHelper;
import io.github.fajzu.sectors.bukkit.inventory.builder.ItemBuilder;
import io.github.fajzu.sectors.bukkit.profile.Profile;
import io.github.fajzu.sectors.bukkit.profile.ProfileService;
import io.github.fajzu.sectors.bukkit.transfer.PlayerTransferService;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.google.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

public class ChannelInventory {

    private final Player player;
    private final Window guiWindow;

    @Inject
    public ChannelInventory(final Player player,
                            final SectorService sectorService,
                            final ProfileService profileService,
                            final MessagesConfiguration messagesConfiguration,
                            final PlayerTransferService playerTransferService) {
        this.player = player;
        this.guiWindow = new Window("&7Kanaly...", 1);

        int i = 0;
        for (final Sector sector : sectorService.sectors().values().stream()
                .filter(s -> s.sectorType().equals(SectorType.SPAWN))
                .collect(Collectors.toList())) {

            final ItemStack itemStack = (sector.statistics().isOnline() ?
                    CustomHeadHelper.create("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGEyZjljNzYxZmMxMzFkYmViZDA3M2IwYjFkZDdkMWJhZWExOTFjZTlkMzNjNDljM2FjYTk0NDhiMWI2YjY4NCJ9fX0=")
                    :
                    CustomHeadHelper.create("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWIwZTA3NjMyMmZjOWFmNzk1OTJlYjg1MmNhOGM3YzQ1YmIyYzNjZWFiYzNjMGU4YTZhMWUwNGI0Y2UzZDM0YiJ9fX0="));

            final ItemBuilder sectorItem = new ItemBuilder(itemStack)
                    .name("&7Sektor &a" + sector.id());

            sectorItem.lore(sector.statistics().isOnline() ?
                    List.of(
                            "",
                            "&7Online: &a" + sector.statistics().players(),
                            "&7TPS: &a" + ChatHelper.formatTps(sector.statistics().tps()),
                            "")
                    :
                    List.of(
                            "",
                            "&cSektor jest offline",
                            ""
                    ));

            sectorItem.lore(sector.id().equals(sectorService.currentSectorId()) ? "&eZnajdujesz sie na tym kanale" : "&eKliknij aby polaczyc sie z kanalem");

            this.guiWindow.setItem(i, sectorItem.build(), (event -> {
                if (sectorService.currentSector().id().equals(sector.id())) {
                    player.sendMessage(ChatHelper.colored(messagesConfiguration.playerAlreadyConnectedMessage()));
                    return;
                }

                if (!sector.statistics().isOnline()) {
                    player.sendMessage(ChatHelper.colored(messagesConfiguration.sectorIsOfflineMessage()));
                    return;
                }

                final Profile profile = profileService.find(player.getUniqueId());

                if (profile == null) {
                    player.kick(ChatHelper.colored(messagesConfiguration.playerDataNotFoundMessage()));
                    return;
                }

                playerTransferService.connect(player, profile, sector, true);
            }));

            i++;
        }
    }

    public void openInventory() {
        this.guiWindow.open(this.player);
    }
}
