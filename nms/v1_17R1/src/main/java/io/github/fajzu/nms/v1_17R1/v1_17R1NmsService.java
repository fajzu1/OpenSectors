package io.github.fajzu.nms.v1_17R1;

import io.github.fajzu.nms.api.NmsService;
import io.github.fajzu.nms.api.actionbar.Actionbar;
import io.github.fajzu.nms.api.border.Border;
import io.github.fajzu.nms.api.converter.NbtConverter;
import io.github.fajzu.nms.api.data.Data;
import io.github.fajzu.nms.api.server.MinecraftServer;
import io.github.fajzu.nms.v1_17R1.actionbar.v1_17R1Actionbar;
import io.github.fajzu.nms.v1_17R1.border.v1_17R1Border;
import io.github.fajzu.nms.v1_17R1.converter.v1_17R1NbtConverter;
import io.github.fajzu.nms.v1_17R1.data.v1_17R1Data;
import io.github.fajzu.nms.v1_17R1.server.v1_17R1MinecraftServer;

public class v1_17R1NmsService implements NmsService {

    @Override
    public Actionbar actionBar() {
        return new v1_17R1Actionbar();
    }

    @Override
    public NbtConverter nbtConverter() {
        return new v1_17R1NbtConverter();
    }

    @Override
    public Data data() {
        return new v1_17R1Data();
    }

    @Override
    public MinecraftServer minecraftServer() {
        return new v1_17R1MinecraftServer();
    }

    @Override
    public Border border() {
        return new v1_17R1Border();
    }
}
