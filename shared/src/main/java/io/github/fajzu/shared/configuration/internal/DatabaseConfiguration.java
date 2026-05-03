package io.github.fajzu.shared.configuration.internal;

import io.github.fajzu.shared.configuration.Configuration;

public class DatabaseConfiguration implements Configuration {

    private final String natsHost;

    public DatabaseConfiguration() {
        this.natsHost = "nats://localhost:4222";
    }

    public String natsHost() {
        return this.natsHost;
    }

    @Override
    public String fileName() {
        return "database.json";
    }
}
