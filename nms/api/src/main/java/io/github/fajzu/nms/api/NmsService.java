package io.github.fajzu.nms.api;

import io.github.fajzu.nms.api.actionbar.Actionbar;
import io.github.fajzu.nms.api.border.Border;
import io.github.fajzu.nms.api.converter.NbtConverter;
import io.github.fajzu.nms.api.data.Data;
import io.github.fajzu.nms.api.server.MinecraftServer;
import org.bukkit.entity.Player;

public interface NmsService {

    Actionbar actionBar();

    NbtConverter<Object> nbtConverter();

    Data<Player, Object> data();

    MinecraftServer minecraftServer();

    Border border();
}
