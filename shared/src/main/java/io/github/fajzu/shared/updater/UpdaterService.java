package io.github.fajzu.shared.updater;

import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
import java.util.logging.Logger;

public class UpdaterService {

    private final String currentVersion;

    private final String newestVersion;

    public UpdaterService(final @NotNull String currentVersion,
                          final @NotNull Logger logger) {
        this.currentVersion = currentVersion;

        this.newestVersion = VersionDataFetcher.fetch(logger, "https://api.github.com/repos/fajzu1/OpenSectors/tags");
    }

    public void check(final @NotNull Consumer<String> consumer) {
        final int result = this.currentVersion.compareTo(this.newestVersion);
        if (result >= 0) {
            return;
        }

        consumer.accept(this.newestVersion);
    }
}
