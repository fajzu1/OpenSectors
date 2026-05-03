package io.github.fajzu.sectors.bukkit.helper;

import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Base64;

public final class SerializeHelper {

    public static byte[] serialize(final Object object) {
        try (final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             final BukkitObjectOutputStream bukkitObjectOutputStream = new BukkitObjectOutputStream(outputStream)) {
            bukkitObjectOutputStream.writeObject(object);
            return Base64.getEncoder().encode(outputStream.toByteArray());
        } catch (final Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public static Object deserialize(final byte[] base64) {
        try (final BukkitObjectInputStream objectInputStream = new BukkitObjectInputStream(
                new ByteArrayInputStream(Base64.getDecoder().decode(base64)))) {
            return objectInputStream.readObject();
        } catch (final Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
