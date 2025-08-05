package net.lightcode.v1_14R1;

import net.lightcode.NmsService;
import net.lightcode.actionbar.Actionbar;
import net.lightcode.border.Border;
import net.lightcode.converter.NbtConverter;
import net.lightcode.data.Data;
import net.lightcode.server.MinecraftServer;
import net.lightcode.v1_14R1.actionbar.v1_14R1Actionbar;
import net.lightcode.v1_14R1.border.v1_14R1Border;
import net.lightcode.v1_14R1.converter.v1_14R1NbtConverter;
import net.lightcode.v1_14R1.data.v1_14R1Data;
import net.lightcode.v1_14R1.server.v1_14R1MinecraftServer;

public class v1_14R1NmsService implements NmsService {

    @Override
    public Actionbar actionBar() {
        return new v1_14R1Actionbar();
    }

    @Override
    public NbtConverter nbtConverter() {
        return new v1_14R1NbtConverter();
    }

    @Override
    public Data data() {
        return new v1_14R1Data();
    }

    @Override
    public MinecraftServer minecraftServer() {
        return new v1_14R1MinecraftServer();
    }

    @Override
    public Border border() {
        return new v1_14R1Border();
    }


}
