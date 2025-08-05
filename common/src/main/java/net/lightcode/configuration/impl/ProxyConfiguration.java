package net.lightcode.configuration.impl;

import net.lightcode.configuration.Configuration;
import net.lightcode.configuration.wrapper.SectorWrapper;
import net.lightcode.sector.type.SectorType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ProxyConfiguration implements Configuration {

    private final Map<String, SectorWrapper> sectors;

    public ProxyConfiguration() {
        this.sectors = new ConcurrentHashMap<>();

        this.sectors.put("s1", new SectorWrapper("s1", SectorType.NORMAL, 250, 2000, -250, 2000));
        this.sectors.put("spawn_1", new SectorWrapper("spawn_1", SectorType.SPAWN, -250, 250, -250, 250));
    }

    public Map<String, SectorWrapper> sectors() {
        return this.sectors;
    }

    public String fileName() {
        return "sectors.json";
    }
}
