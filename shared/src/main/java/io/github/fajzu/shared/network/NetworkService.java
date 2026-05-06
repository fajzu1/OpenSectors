package io.github.fajzu.shared.network;

import com.google.inject.Singleton;
import io.github.fajzu.shared.network.codec.PacketCodec;
import io.github.fajzu.shared.network.packet.Packet;
import io.github.fajzu.shared.network.packet.PacketHandler;
import io.github.fajzu.shared.network.packet.PacketListener;
import io.nats.client.Connection;
import io.nats.client.Nats;
import io.nats.client.Options;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public final class NetworkService {

    private final PacketCodec packetCodec;
    private final String packetSender;
    private final Connection connection;

    public NetworkService(@NotNull final String url,
                          @NotNull final String packetSender) {
        this.packetCodec = new PacketCodec();
        this.packetSender = packetSender;

        try {
            final Options options = new Options.Builder()
                .server(url)
                .maxReconnects(-1)
                .connectionTimeout(Duration.ofSeconds(2L))
                .build();

            this.connection = Nats.connect(options);
        } catch (final Exception exception) {
            throw new RuntimeException("Could not connect to NATS", exception);
        }
    }

    @NotNull
    public CompletableFuture<Void> publish(@NotNull final String topic,
                                           @NotNull final Packet packet) {
        packet.sender(this.packetSender);

        try {
            final byte[] data = this.packetCodec.encode(packet);
            this.connection.publish(topic, data);
            return CompletableFuture.completedFuture(null);
        } catch (final Exception exception) {
            return CompletableFuture.failedFuture(exception);
        }
    }

    @NotNull
    public <T extends Packet> CompletableFuture<T> request(@NotNull final String topic,
                                                           @NotNull final Packet packet,
                                                           @NotNull final Class<T> responseType) {
        return this.request(topic, packet, responseType, Duration.ofSeconds(5L));
    }

    @NotNull
    public <T extends Packet> CompletableFuture<T> request(@NotNull final String topic,
                                                           @NotNull final Packet packet,
                                                           @NotNull final Class<T> responseType,
                                                           @NotNull final Duration timeout) {
        packet.sender(this.packetSender);
        try {
            final byte[] data = this.packetCodec.encode(packet);
            return this.connection.requestWithTimeout(topic, data, timeout)
                .thenApply(message -> {
                    try {
                        final Packet responsePacket = this.packetCodec.decode(message.getData());
                        return responseType.cast(responsePacket);
                    } catch (final Exception exception) {
                        throw new RuntimeException("Could not decode response packet for topic " + topic, exception);
                    }
                });
        } catch (final Exception exception) {
            return CompletableFuture.failedFuture(exception);
        }
    }

    public void subscribe(@NotNull final String topic,
                          @NotNull final Object listener) {
        final Map<String, Method> methodByType = new ConcurrentHashMap<>();
        Arrays.stream(listener.getClass().getDeclaredMethods())
            .filter(method -> method.getParameters().length == 1 && method.isAnnotationPresent(PacketHandler.class))
            .forEach(method -> {
                method.setAccessible(true);
                methodByType.put(method.getParameters()[0].getType().getName(), method);
            });

        this.connection.createDispatcher(new PacketListener(methodByType, listener, this.packetCodec, this.connection)).subscribe(topic);
    }

    public void shutdown() {
        try {
            if (this.connection != null) {
                this.connection.close();
            }
        } catch (final Exception exception) {
            throw new RuntimeException("Could not close NATS connection", exception);
        }
    }
}