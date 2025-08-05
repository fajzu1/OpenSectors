package net.lightcode.v1_19R3.data;

import net.lightcode.data.Data;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class v1_19R3Data implements Data<Player, Object> {
    @Override
    public String saveData(Player player) {
        ServerPlayer nmsPlayer = ((CraftPlayer) player).getHandle();

        CompoundTag tag = new CompoundTag();
        nmsPlayer.saveWithoutId(tag);

        return tag.toString();
    }

    @Override
    public void loadData(Player player, Object nbtTagCompound) {
        ServerPlayer nmsPlayer = ((CraftPlayer) player).getHandle();
        nmsPlayer.load((CompoundTag) nbtTagCompound);
    }
}