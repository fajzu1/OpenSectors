package io.github.fajzu.shared.network.packet;

import io.github.fajzu.shared.network.codec.PacketCodec;
import io.nats.client.Connection;
import io.nats.client.Message;
import io.nats.client.MessageHandler;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public final class PacketListener implements MessageHandler {
    private final Map<String, Method> methodsByName;

    private final Object messageHandler;
    private final PacketCodec packetCodec;
    private final Connection connection;

    public PacketListener(@NotNull final Map<String, Method> methodsByName,
                          @NotNull final Object messageHandler,
                          @NotNull final PacketCodec packetCodec,
                          @NotNull final Connection connection) {
        this.methodsByName = methodsByName;
        this.messageHandler = messageHandler;
        this.packetCodec = packetCodec;
        this.connection = connection;
    }

    @Override
    public void onMessage(@NotNull final Message message) {
        try {
            final Packet packet = this.packetCodec.decode(message.getData());
            final Method method = this.methodsByName.get(packet.getClass().getName());

            if (method == null) {
                return;
            }

            final Object response = method.invoke(this.messageHandler, packet);

            if (response instanceof Packet responsePacket && message.getReplyTo() != null && !message.getReplyTo().isBlank()) {
                this.connection.publish(message.getReplyTo(), this.packetCodec.encode(responsePacket));
            }
        } catch (InvocationTargetException exception) {
            final Throwable cause = exception.getCause() == null ? exception : exception.getCause();
            throw new PacketExecuteException(
                "Failed to execute packet handler for " + message.getSubject() + ": " + cause.getClass().getSimpleName() + " - " + cause.getMessage(),
                cause
            );
        } catch (IllegalAccessException exception) {
            throw new PacketExecuteException(
                "Failed to execute packet handler for " + message.getSubject() + ": " + exception.getClass().getSimpleName() + " - " + exception.getMessage(),
                exception
            );
        }
    }
}