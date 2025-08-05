package net.lightcode.bukkit.helper;

import net.lightcode.NmsService;
import org.bukkit.plugin.Plugin;

public class NmsHelper {

    public static NmsService findNmsService(Plugin plugin) {
        String nmsVersion = findBukkitVersion(plugin);

        String className = String.format("net.lightcode.%s.%sNmsService", nmsVersion, nmsVersion);

        try {
            return (NmsService) Class.forName(className).newInstance();
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    protected static String findBukkitVersion(Plugin plugin) {
        StringBuilder nmsVersion = new StringBuilder(plugin.getServer().getClass().getPackage().getName().split("\\.")[3]);

        int revPosition = nmsVersion.lastIndexOf("_");
        nmsVersion.deleteCharAt(revPosition);

        return nmsVersion.toString();
    }

}
