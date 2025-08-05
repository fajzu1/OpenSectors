package net.lightcode.bukkit.helper;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public final class SerializeHelper {

    public static String serializeLocation(final Location location) {
        try {
            return location.getWorld().getName() + ":" + location.getX() + ":" + location.getY() + ":"
                    + location.getZ() + ":" + location.getYaw() + ":" + location.getPitch();
        } catch (final Exception exception) {
            throw new RuntimeException("Location serialization failed", exception);
        }
    }

    public static Location deserializeLocation(final String serializedData) {
        try {
            final String[] split = serializedData.split(":");
            return new Location(Bukkit.getWorld(split[0]), Double.parseDouble(split[1]),
                    Double.parseDouble(split[2]), Double.parseDouble(split[3]), Float.parseFloat(split[4]),
                    Float.parseFloat(split[5]));
        } catch (final Exception exception) {
            throw new RuntimeException("Location deserialization failed", exception);
        }
    }

}