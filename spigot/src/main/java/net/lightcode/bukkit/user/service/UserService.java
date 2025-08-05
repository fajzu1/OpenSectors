package net.lightcode.bukkit.user.service;

import net.lightcode.bukkit.user.User;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class UserService {

    private final Map<UUID, User> users = new ConcurrentHashMap<>();

    public void create(UUID uuid,String name) {
        this.users.put(uuid,new User(name, uuid));
    }

    public void remove(UUID uuid) {
        this.users.remove(uuid);
    }

    public Optional<User> find(UUID uuid) {
        return Optional.ofNullable(this.users.get(uuid));
    }

    public Map<UUID, User> users() {
        return this.users;
    }
}