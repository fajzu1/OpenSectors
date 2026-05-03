package io.github.fajzu.nms.v1_19R3;

import io.github.fajzu.nms.api.NmsService;
import io.github.fajzu.nms.api.actionbar.Actionbar;
import io.github.fajzu.nms.api.border.Border;
import io.github.fajzu.nms.api.converter.NbtConverter;
import io.github.fajzu.nms.api.data.Data;
import io.github.fajzu.nms.api.server.MinecraftServer;
import io.github.fajzu.nms.v1_19R3.actionbar.v1_19R3Actionbar;
import io.github.fajzu.nms.v1_19R3.border.v1_19R3Border;
import io.github.fajzu.nms.v1_19R3.converter.v1_19R3NbtConverter;
import io.github.fajzu.nms.v1_19R3.data.v1_19R3Data;
import io.github.fajzu.nms.v1_19R3.server.v1_19R3MinecraftServer;

public class v1_19R3NmsService implements NmsService {

    @Override
    public Actionbar actionBar() {
        return new v1_19R3Actionbar();
    }

    @Override
    public NbtConverter nbtConverter() {
        return new v1_19R3NbtConverter();
    }

    @Override
    public Data data() {
        return new v1_19R3Data();
    }

    @Override
    public MinecraftServer minecraftServer() {
        return new v1_19R3MinecraftServer();
    }

    @Override
    public Border border() {
        return new v1_19R3Border();
    }
}
