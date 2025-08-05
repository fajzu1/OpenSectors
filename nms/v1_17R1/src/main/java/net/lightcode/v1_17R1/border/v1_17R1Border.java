package net.lightcode.v1_17R1.border;

import net.lightcode.border.Border;
import net.minecraft.network.protocol.game.ClientboundInitializeBorderPacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderWarningDelayPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.border.WorldBorder;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class v1_17R1Border implements Border {
    @Override
    public void sendWorldBorder(Player player, double size, double centerX, double centerZ) {
        ServerPlayer serverPlayer = ((CraftPlayer) player).getHandle();

        WorldBorder border = new WorldBorder();
        border.setCenter(centerX, centerZ);
        border.setSize(size);
        border.setWarningBlocks(0);
        border.setWarningTime(0);
        border.setDamagePerBlock(0);

        ClientboundInitializeBorderPacket packet = new ClientboundInitializeBorderPacket(border);

        serverPlayer.connection.send(packet);
    }
}