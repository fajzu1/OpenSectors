package net.lightcode.v1_8R3;

import net.lightcode.NmsService;
import net.lightcode.actionbar.Actionbar;
import net.lightcode.border.Border;
import net.lightcode.converter.NbtConverter;
import net.lightcode.data.Data;
import net.lightcode.server.MinecraftServer;
import net.lightcode.v1_8R3.actionbar.v1_8R3Actionbar;
import net.lightcode.v1_8R3.border.v1_8R3Border;
import net.lightcode.v1_8R3.converter.v1_8R3NbtConverter;
import net.lightcode.v1_8R3.data.v1_8R3Data;
import net.lightcode.v1_8R3.server.v1_8R3MinecraftServer;

public class v1_8R3NmsService implements NmsService {

    @Override
    public Actionbar actionBar() {
        return new v1_8R3Actionbar();
    }

    @Override
    public NbtConverter nbtConverter() {
        return new v1_8R3NbtConverter();
    }

    @Override
    public Data data() {
        return new v1_8R3Data();
    }

    @Override
    public MinecraftServer minecraftServer() {
        return new v1_8R3MinecraftServer();
    }

    @Override
    public Border border() {
        return new v1_8R3Border();
    }


}
