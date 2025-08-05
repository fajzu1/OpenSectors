package net.lightcode;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import net.lightcode.packet.Packet;
import net.lightcode.redis.PacketListener;
import net.lightcode.redis.codec.MessagePackCodec;

import java.util.ArrayList;
import java.util.List;

public class NetworkService {

    private final List<String> subscribedChannels;

    private final StatefulRedisPubSubConnection<String, Packet> pubSubConnection;
    private final StatefulRedisConnection<String, Packet> connection;

    private String packetSender;

    public NetworkService(String address, int port, String password) {
        this.subscribedChannels = new ArrayList<>();

        RedisClient redisClient = RedisClient.create(RedisURI.builder()
                .withHost(address)
                .withPort(port)
                .withPassword(password)
                .build());

        MessagePackCodec messagePackCodec = new MessagePackCodec();

        this.pubSubConnection = redisClient.connectPubSub(messagePackCodec);
        this.connection = redisClient.connect(messagePackCodec);

    }

    public void shutdown() {
        this.pubSubConnection.sync().unsubscribe(this.subscribedChannels.toArray(new String[0]));
        this.pubSubConnection.close();
        this.connection.close();
    }

    public void publish(String channel, Packet packet) {
        packet.sender(this.packetSender);

        this.connection.sync().publish(channel, packet);
    }

    public void subscribe(String channel, PacketListener<? extends Packet> listener) {
        this.pubSubConnection.addListener(listener);

        if (this.subscribedChannels.contains(channel)) return;

        this.pubSubConnection.sync().subscribe(channel);
        this.subscribedChannels.add(channel);
    }

    public List<String> subscribedChannels() {
        return this.subscribedChannels;
    }

    public void setPacketSender(String packetSender) {
        this.packetSender = packetSender;
    }
}