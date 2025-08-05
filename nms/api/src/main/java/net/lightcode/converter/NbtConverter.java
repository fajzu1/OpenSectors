package net.lightcode.converter;

public interface NbtConverter<T> {

    T convertStringToNBTCompound(String nbtString);
}
