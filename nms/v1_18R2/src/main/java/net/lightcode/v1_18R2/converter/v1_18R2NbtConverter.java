package net.lightcode.v1_18R2.converter;

import net.lightcode.converter.NbtConverter;
import net.minecraft.nbt.TagParser;

public class v1_18R2NbtConverter implements NbtConverter<Object> {

    @Override
    public Object convertStringToNBTCompound(String nbtString) {
        try {
            return TagParser.parseTag(nbtString);
        } catch (Exception exception) {
            throw new RuntimeException("NBT parsing failed", exception);
        }
    }
}