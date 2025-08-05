package net.lightcode.v1_15R1.converter;

import net.lightcode.converter.NbtConverter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class v1_15R1NbtConverter implements NbtConverter<Object> {

    @Override
    public Object convertStringToNBTCompound(String nbtString) {
        try {
            Class<?> mojangsonParserClass = Class.forName("net.minecraft.server.v1_15_R1.MojangsonParser");
            Method parseMethod = mojangsonParserClass.getMethod("parse", String.class);
            return parseMethod.invoke(null, nbtString);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            throw new RuntimeException("Failed to parse NBT string: " + nbtString, cause);
        } catch (Exception e) {
            throw new RuntimeException("Reflection error during NBT parsing", e);
        }
    }
}
