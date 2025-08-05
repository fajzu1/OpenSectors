package net.lightcode.v1_20R5.converter;

import net.lightcode.converter.NbtConverter;
import net.minecraft.nbt.TagParser;

public class v1_20R5NbtConverter implements NbtConverter<Object> {

    @Override
    public Object convertStringToNBTCompound(String nbtString) {
        try {
            return TagParser.parseTag(nbtString);
        } catch (Exception exception) {
            throw new RuntimeException("NBT parsing failed", exception);
        }
    }
}