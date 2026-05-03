package io.github.fajzu.nms.v1_18R2;

import io.github.fajzu.nms.api.NmsService;
import io.github.fajzu.nms.api.actionbar.Actionbar;
import io.github.fajzu.nms.api.border.Border;
import io.github.fajzu.nms.api.converter.NbtConverter;
import io.github.fajzu.nms.api.data.Data;
import io.github.fajzu.nms.api.server.MinecraftServer;
import io.github.fajzu.nms.v1_18R2.actionbar.v1_18R2Actionbar;
import io.github.fajzu.nms.v1_18R2.border.v1_18R2Border;
import io.github.fajzu.nms.v1_18R2.converter.v1_18R2NbtConverter;
import io.github.fajzu.nms.v1_18R2.data.v1_18R2Data;
import io.github.fajzu.nms.v1_18R2.server.v1_18R2MinecraftServer;

public class v1_18R2NmsService implements NmsService {

    @Override
    public Actionbar actionBar() {
        return new v1_18R2Actionbar();
    }

    @Override
    public NbtConverter nbtConverter() {
        return new v1_18R2NbtConverter();
    }

    @Override
    public Data data() {
        return new v1_18R2Data();
    }

    @Override
    public MinecraftServer minecraftServer() {
        return new v1_18R2MinecraftServer();
    }

    @Override
    public Border border() {
        return new v1_18R2Border();
    }
}
