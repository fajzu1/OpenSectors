package io.github.fajzu.shared.network.codec;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.github.fajzu.shared.network.packet.Packet;
import org.jetbrains.annotations.NotNull;
import org.msgpack.jackson.dataformat.MessagePackFactory;

import java.io.IOException;

public final class PacketCodec {
    private final ObjectMapper objectMapper;

    public PacketCodec() {
        this.objectMapper = new ObjectMapper(new MessagePackFactory())
                .setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL)
                .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .enable(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN)
                .enable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY);

        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.registerSubtypes(Packet.class);
    }

    public byte @NotNull [] encode(@NotNull final Packet packet) {
        try {
            return this.objectMapper.writeValueAsBytes(packet);
        } catch (final JsonProcessingException exception) {
            throw new CodecSerializationException("Encoding packet failed: " + exception.getMessage());
        }
    }

    @NotNull
    public <T> T decode(final byte @NotNull [] data,
                        @NotNull final Class<T> type) {
        try {
            return this.objectMapper.readValue(data, type);
        } catch (final IOException exception) {
            throw new CodecDeserializationException("Decoding packet failed: " + exception.getMessage());
        }
    }

    @NotNull
    public Packet decode(final byte @NotNull [] data) {
        return this.decode(data, Packet.class);
    }
}
