package io.github.fajzu.nms.v1_20R5;

import io.github.fajzu.nms.api.NmsService;
import io.github.fajzu.nms.api.actionbar.Actionbar;
import io.github.fajzu.nms.api.border.Border;
import io.github.fajzu.nms.api.converter.NbtConverter;
import io.github.fajzu.nms.api.data.Data;
import io.github.fajzu.nms.api.server.MinecraftServer;
import io.github.fajzu.nms.v1_20R5.actionbar.v1_20R5Actionbar;
import io.github.fajzu.nms.v1_20R5.border.v1_20R5Border;
import io.github.fajzu.nms.v1_20R5.converter.v1_20R5NbtConverter;
import io.github.fajzu.nms.v1_20R5.data.v1_20R5Data;
import io.github.fajzu.nms.v1_20R5.server.v1_20R5MinecraftServer;
import org.bukkit.entity.Player;

public class v1_20R5NmsService implements NmsService {

    @Override
    public Actionbar actionBar() {
        return new v1_20R5Actionbar();
    }

    @Override
    public NbtConverter<Object> nbtConverter() {
        return new v1_20R5NbtConverter();
    }

    @Override
    public Data<Player, Object> data() {
        return new v1_20R5Data();
    }

    @Override
    public MinecraftServer minecraftServer() {
        return new v1_20R5MinecraftServer();
    }

    @Override
    public Border border() {
        return new v1_20R5Border();
    }
}
