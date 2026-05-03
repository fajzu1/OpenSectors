package io.github.fajzu.sectors.bukkit.profile;

import com.google.inject.Singleton;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class ProfileService {

    private final Map<UUID, Profile> profiles = new ConcurrentHashMap<>();

    public void create(final UUID uuid,
                       final String name) {
        this.profiles.put(uuid, new Profile(name, uuid));
    }

    public void remove(final UUID uuid) {
        this.profiles.remove(uuid);
    }

    public Profile find(final String name) {
        return this.profiles.values().stream()
                .filter(profile -> profile.name().equals(name))
                .findFirst()
                .orElse(null);
    }

    public Profile find(final UUID uuid) {
        return this.profiles.get(uuid);
    }

    public Map<UUID, Profile> profiles() {
        return this.profiles;
    }
}
