package net.lightcode.data;

public interface Data<K, T> {

    String saveData(K entityPlayer);

    void loadData(K entityPlayer, T nbtTagCompound);
}
