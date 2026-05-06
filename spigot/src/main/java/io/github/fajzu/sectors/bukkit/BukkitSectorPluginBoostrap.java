package io.github.fajzu.sectors.bukkit;

import com.google.inject.CreationException;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.bukkit.plugin.java.JavaPlugin;

public final class BukkitSectorPluginBoostrap extends JavaPlugin {

    private BukkitSectorPluginController controller;

    public void onEnable() {
        final Injector injector = Guice.createInjector(new BukkitInjectorModule(this));

        try {
            this.controller = injector.getInstance(BukkitSectorPluginController.class);
            this.controller.initialize();
        } catch (CreationException exception) {
            this.getServer().shutdown();
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void onDisable() {
        this.controller.shutdown();
    }
}
