package net.lightcode.v1_13R2;

import net.lightcode.NmsService;
import net.lightcode.actionbar.Actionbar;
import net.lightcode.border.Border;
import net.lightcode.converter.NbtConverter;
import net.lightcode.data.Data;
import net.lightcode.server.MinecraftServer;
import net.lightcode.v1_13R2.actionbar.v1_13R2Actionbar;
import net.lightcode.v1_13R2.border.v1_13R2Border;
import net.lightcode.v1_13R2.converter.v1_13R2NbtConverter;
import net.lightcode.v1_13R2.data.v1_13R2Data;
import net.lightcode.v1_13R2.server.v1_13R2MinecraftServer;

public class v1_13R2NmsService implements NmsService {

    @Override
    public Actionbar actionBar() {
        return new v1_13R2Actionbar();
    }

    @Override
    public NbtConverter nbtConverter() {
        return new v1_13R2NbtConverter();
    }

    @Override
    public Data data() {
        return new v1_13R2Data();
    }

    @Override
    public MinecraftServer minecraftServer() {
        return new v1_13R2MinecraftServer();
    }

    @Override
    public Border border() {
        return new v1_13R2Border();
    }


}
