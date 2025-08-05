package net.lightcode.bukkit.listener.redis;

import net.lightcode.NetworkService;
import net.lightcode.helper.GsonHelper;
import net.lightcode.packet.impl.PlayerConnectSectorPacket;
import net.lightcode.packet.impl.UserSynchronizeDataPacket;
import net.lightcode.redis.PacketListener;
import net.lightcode.bukkit.user.User;
import net.lightcode.bukkit.user.service.UserService;

public class PacketUserSynchronizeDataListener extends PacketListener<UserSynchronizeDataPacket> {

    private final NetworkService networkService;

    private final UserService userService;

    public PacketUserSynchronizeDataListener(NetworkService networkService, UserService userService) {
        super(UserSynchronizeDataPacket.class);

        this.networkService = networkService;
        this.userService = userService;
    }

    @Override
    public void handle(UserSynchronizeDataPacket packet) {
        User user = GsonHelper.fromJson(packet.serializedUser(), User.class);

        this.userService.users().put(user.uuid(), user);
        this.networkService.publish("bridge", new PlayerConnectSectorPacket(user.name(), packet.sectorName()));
    }
}
