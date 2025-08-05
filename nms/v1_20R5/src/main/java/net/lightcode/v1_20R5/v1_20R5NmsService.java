package net.lightcode.v1_20R5;

import net.lightcode.NmsService;
import net.lightcode.actionbar.Actionbar;
import net.lightcode.border.Border;
import net.lightcode.converter.NbtConverter;
import net.lightcode.data.Data;
import net.lightcode.server.MinecraftServer;
import net.lightcode.v1_20R5.actionbar.v1_20R5Actionbar;
import net.lightcode.v1_20R5.border.v1_20R5Border;
import net.lightcode.v1_20R5.converter.v1_20R5NbtConverter;
import net.lightcode.v1_20R5.data.v1_20R5Data;
import net.lightcode.v1_20R5.server.v1_20R5MinecraftServer;
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
    public Data<Player,Object> data() {
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
