package net.lightcode.bukkit;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BukkitLogger {

    private final Logger logger;

    public BukkitLogger(Logger logger) {
        this.logger = logger;

        for (Handler handler : logger.getHandlers()) {
            handler.setLevel(Level.ALL);

            try {
                handler.setEncoding("UTF-8");
            } catch (UnsupportedEncodingException exception) {
                throw new RuntimeException(exception);
            }
        }
    }

    public void log(String message) {
        this.logger.log(Level.INFO, message);
    }

    public void log(Level level, List<String> messages) {
        for (String message : messages) {
            this.logger.log(level, message);
        }
    }

    public void log(Level level, String message) {
        this.logger.log(level, message);
    }

    public void log(Level level, String message, Throwable throwable) {
        this.logger.log(level, message, throwable);
    }

    public void warning(String message) {
        this.logger.log(Level.WARNING, message);
    }

    public void severe(String message) {
        this.logger.log(Level.SEVERE, message);
    }

    public void fine(String message) {
        this.logger.log(Level.FINE, message);
    }

}