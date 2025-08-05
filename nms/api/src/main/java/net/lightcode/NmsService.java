package net.lightcode;

import net.lightcode.actionbar.Actionbar;
import net.lightcode.border.Border;
import net.lightcode.converter.NbtConverter;
import net.lightcode.data.Data;
import net.lightcode.server.MinecraftServer;
import org.bukkit.entity.Player;

public interface NmsService {

    Actionbar actionBar();

    NbtConverter<Object> nbtConverter();

    Data<Player,Object> data();

    MinecraftServer minecraftServer();

    Border border();
}
