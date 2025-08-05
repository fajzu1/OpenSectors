package net.lightcode.bridge;

import org.slf4j.Logger;

import java.util.List;

public class BridgeLogger {

    private final Logger logger;

    public BridgeLogger(Logger logger) {
        this.logger = logger;
    }

    public void log(String message) {
        this.logger.info(message);
    }

    public void log(String message, Object... args) {
        message = format(message, args);
        this.logger.info(message);
    }

    public void log(List<String> messages) {
        for (String message : messages) {
            this.logger.info(message);
        }
    }

    public void warning(String message) {
        this.logger.warn(message);
    }

    public void warning(String message, Object... args) {
        message = format(message, args);
        this.logger.warn(message);
    }

    public void warning(List<String> messages) {
        for (String message : messages) {
            this.logger.warn(message);
        }
    }

    public void severe(String message) {
        this.logger.error(message);
    }

    public void severe(String message, Object... args) {
        message = format(message, args);
        this.logger.error(message);
    }

    private String format(String message, Object... args) {
        for (Object arg : args) {
            message = message.replaceFirst("\\{}", arg == null ? "null" : arg.toString());
        }
        return message;
    }

}