package io.github.fajzu.sectors.bukkit.nms;

import io.github.fajzu.nms.api.NmsService;
import org.jetbrains.annotations.NotNull;

public final class NmsServiceFactory {

    private static final String NMS_PACKAGE_FORMAT = "io.github.fajzu.nms.%s.%sNmsService";

    public NmsService create(final @NotNull String version) {
        final String className = String.format(NMS_PACKAGE_FORMAT, version, version);

        try {
            return (NmsService) Class.forName(className).getDeclaredConstructor().newInstance();
        } catch (final Exception exception) {
            throw new RuntimeException("Could not find NMS implementation for version: " + version, exception);
        }
    }
}
