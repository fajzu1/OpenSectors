package net.lightcode.configuration.service;

import net.lightcode.configuration.Configuration;
import net.lightcode.helper.GsonHelper;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConfigurationService {

    private final String directory;

    private final Map<Class<?>, Configuration> configurations;

    public ConfigurationService(String directory) {
        this.directory = directory;

        this.configurations = new ConcurrentHashMap<>();
    }

    public synchronized <T> T loadConfiguration(Class<? extends Configuration> configurationClass) {
        try {
            Configuration configuration = configurationClass.getDeclaredConstructor().newInstance();

            String fileName = configuration.fileName();

            File file = new File(this.directory, fileName);

            if (file.exists()) {
                T loadedConfiguration = (T) GsonHelper.fromJson(Files.readString(file.toPath()), configurationClass);

                this.configurations.put(configurationClass, (Configuration) loadedConfiguration);
                return loadedConfiguration;
            }

            this.configurations.put(configurationClass, configuration);
            return this.saveConfiguration(configuration);
        } catch (IOException | InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException exception) {
            throw new RuntimeException("Loading configuration failed", exception);
        }
    }

    public synchronized <T> T saveConfiguration(Configuration configuration) {
        try {

            String fileName = configuration.fileName();

            File fileDirectory = new File(this.directory);

            if (!fileDirectory.exists())
                fileDirectory.mkdirs();

            File file = new File(fileDirectory, fileName);
            String json = GsonHelper.toJson(configuration);

            try (PrintWriter writer = new PrintWriter(file, StandardCharsets.UTF_8)) {
                writer.println(json);
            }

            return (T) configuration;
        } catch (IOException exception) {
            throw new RuntimeException("Saving configuration failed", exception);
        }
    }

    public <T extends Configuration> T find(Class<T> configuration) {
        return configuration.cast(this.configurations.get(configuration));
    }
}
