package io.github.fajzu.sectors.bukkit.command;

import io.github.fajzu.shared.configuration.internal.MessagesConfiguration;
import io.github.fajzu.shared.sector.SectorService;
import io.github.fajzu.sectors.bukkit.inventory.internal.ChannelInventory;
import io.github.fajzu.sectors.bukkit.profile.ProfileService;
import io.github.fajzu.sectors.bukkit.transfer.PlayerTransferService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.google.inject.Inject;

public class ChannelCommand implements CommandExecutor {

    private final ProfileService profileService;
    private final SectorService sectorService;
    private final PlayerTransferService transferService;
    private final MessagesConfiguration messagesConfiguration;

    @Inject
    public ChannelCommand(final ProfileService profileService,
                          final SectorService sectorService,
                          final PlayerTransferService transferService,
                          final MessagesConfiguration messagesConfiguration) {
        this.profileService = profileService;
        this.sectorService = sectorService;
        this.transferService = transferService;
        this.messagesConfiguration = messagesConfiguration;
    }

    @Override
    public boolean onCommand(final CommandSender commandSender,
                             final Command command,
                             final String s,
                             final String[] strings) {
        final Player player = (Player) commandSender;
        final ChannelInventory channelInventory = new ChannelInventory(player, this.sectorService, this.profileService, this.messagesConfiguration, this.transferService);

        channelInventory.openInventory();
        return true;
    }
}
