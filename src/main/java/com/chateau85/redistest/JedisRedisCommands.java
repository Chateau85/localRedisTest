package com.chateau85.redistest;

import redis.clients.jedis.DefaultJedisClientConfig;
import redis.clients.jedis.RedisClient;

final class JedisRedisCommands implements RedisCommands, AutoCloseable {
    private final RedisClient client;

    private JedisRedisCommands(RedisClient client) {
        this.client = client;
    }

    static JedisRedisCommands connect(RedisSettings settings) {
        DefaultJedisClientConfig.Builder config = DefaultJedisClientConfig.builder()
                .connectionTimeoutMillis(settings.timeoutMillis())
                .socketTimeoutMillis(settings.timeoutMillis());
        if (!settings.password().isBlank()) config.password(settings.password());
        RedisClient client = RedisClient.builder()
                .hostAndPort(settings.host(), settings.port())
                .clientConfig(config.build())
                .build();
        return new JedisRedisCommands(client);
    }

    @Override public void set(String key, String value) { client.set(key, value); }
    @Override public String get(String key) { return client.get(key); }
    @Override public void addToSet(String key, String... values) { client.sadd(key, values); }
    @Override public boolean isSetMember(String key, String value) { return client.sismember(key, value); }
    @Override public void appendToList(String key, String... values) { client.rpush(key, values); }
    @Override public String listElement(String key, long index) { return client.lindex(key, index); }
    @Override public void setHashValue(String key, String field, String value) { client.hset(key, field, value); }
    @Override public void incrementHashValue(String key, String field, long amount) { client.hincrBy(key, field, amount); }
    @Override public String hashValue(String key, String field) { return client.hget(key, field); }
    @Override public void close() { client.close(); }
}
