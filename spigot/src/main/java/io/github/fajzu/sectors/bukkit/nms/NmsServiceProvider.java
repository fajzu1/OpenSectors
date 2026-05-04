package io.github.fajzu.sectors.bukkit.nms;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.github.fajzu.nms.api.NmsService;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

@Singleton
public final class NmsServiceProvider {

    private final NmsVersionDetector versionDetector;
    private final NmsPlatformValidator platformValidator;
    private final NmsServiceFactory serviceFactory;
    private NmsService nmsService;

    @Inject
    public NmsServiceProvider(final @NotNull NmsVersionDetector versionDetector,
                              final @NotNull NmsPlatformValidator platformValidator,
                              final @NotNull NmsServiceFactory serviceFactory) {
        this.versionDetector = versionDetector;
        this.platformValidator = platformValidator;
        this.serviceFactory = serviceFactory;
    }

    public NmsService provide(final @NotNull Plugin plugin) {
        if (this.nmsService == null) {
            final String nmsVersion = this.versionDetector.detect(plugin);
            this.platformValidator.validate(plugin, nmsVersion);
            this.nmsService = this.serviceFactory.create(nmsVersion);
        }

        return this.nmsService;
    }
}
