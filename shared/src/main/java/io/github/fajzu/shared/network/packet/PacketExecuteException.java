package io.github.fajzu.shared.network.packet;

import org.jetbrains.annotations.NotNull;

public final class PacketExecuteException extends RuntimeException {

    public PacketExecuteException(final @NotNull String message) {
        super(message);
    }

    public PacketExecuteException(final @NotNull String message,
                                  final @NotNull Throwable cause) {
        super(message, cause);
    }
}
