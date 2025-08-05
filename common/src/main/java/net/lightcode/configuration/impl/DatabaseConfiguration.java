package net.lightcode.configuration.impl;

import net.lightcode.configuration.Configuration;

public class DatabaseConfiguration implements Configuration {

    private final String redisHost, redisPassword;

    private final int redisPort;

    public DatabaseConfiguration() {
        this.redisHost = "localhost";
        this.redisPort = 6379;
        this.redisPassword = "root";

    }

    public String redisHost() {
        return this.redisHost;
    }

    public String redisPassword() {
        return this.redisPassword;
    }

    public int redisPort() {
        return this.redisPort;
    }

    @Override
    public String fileName() {
        return "database.json";
    }
}
