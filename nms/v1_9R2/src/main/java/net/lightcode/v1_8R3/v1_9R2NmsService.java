package net.lightcode.v1_8R3;

import net.lightcode.NmsService;
import net.lightcode.actionbar.Actionbar;
import net.lightcode.border.Border;
import net.lightcode.converter.NbtConverter;
import net.lightcode.data.Data;
import net.lightcode.server.MinecraftServer;
import net.lightcode.v1_8R3.actionbar.v1_9R2Actionbar;
import net.lightcode.v1_8R3.border.v1_9R2Border;
import net.lightcode.v1_8R3.converter.v1_9R2NbtConverter;
import net.lightcode.v1_8R3.data.v1_9R2Data;
import net.lightcode.v1_8R3.server.v1_9R2MinecraftServer;

public class v1_9R2NmsService implements NmsService {

    @Override
    public Actionbar actionBar() {
        return new v1_9R2Actionbar();
    }

    @Override
    public NbtConverter nbtConverter() {
        return new v1_9R2NbtConverter();
    }

    @Override
    public Data data() {
        return new v1_9R2Data();
    }

    @Override
    public MinecraftServer minecraftServer() {
        return new v1_9R2MinecraftServer();
    }

    @Override
    public Border border() {
        return new v1_9R2Border();
    }


}
