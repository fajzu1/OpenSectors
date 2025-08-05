package net.lightcode.updater;

import net.lightcode.updater.fetcher.VersionDataFetcher;

import java.util.function.Consumer;
import java.util.logging.Logger;

public class UpdaterService {

    private final String currentVersion;

    private final String newestVersion;

    public UpdaterService(String currentVersion, Logger logger) {
        this.currentVersion = currentVersion;

        this.newestVersion = VersionDataFetcher.fetch(logger,"https://raw.githubusercontent.com/fajzu1/OpenSectors/main/version.txt");
    }

    public void check(Consumer<String> consumer) {
        int result = this.currentVersion.compareTo(this.newestVersion);

        if(result >= 0) {
            return;
        }

        consumer.accept(this.newestVersion);
    }
}
