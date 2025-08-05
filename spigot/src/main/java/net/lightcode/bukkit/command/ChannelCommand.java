package net.lightcode.bukkit.command;

import net.lightcode.bukkit.inventory.ChannelInventory;
import net.lightcode.bukkit.transfer.PlayerTransferService;
import net.lightcode.bukkit.user.service.UserService;
import net.lightcode.configuration.impl.MessagesConfiguration;
import net.lightcode.sector.service.SectorService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChannelCommand implements CommandExecutor {

    private final UserService userService;

    private final SectorService sectorService;

    private final PlayerTransferService transferService;

    private final MessagesConfiguration messagesConfiguration;

    public ChannelCommand(UserService userService, SectorService sectorService, PlayerTransferService transferService, MessagesConfiguration messagesConfiguration) {
        this.userService = userService;
        this.sectorService = sectorService;
        this.transferService = transferService;
        this.messagesConfiguration = messagesConfiguration;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player = (Player) commandSender;

        ChannelInventory channelInventory = new ChannelInventory(player, this.sectorService, this.userService, this.messagesConfiguration, this.transferService);

        channelInventory.openInventory();
        return true;
    }
}
