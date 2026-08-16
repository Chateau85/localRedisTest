package com.chateau85.redistest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;

import org.junit.jupiter.api.Test;

class RedisSettingsTest {
    @Test
    void usesSafeLocalDefaults() {
        RedisSettings settings = RedisSettings.from(Map.of());

        assertEquals("127.0.0.1", settings.host());
        assertEquals(6379, settings.port());
        assertEquals(3000, settings.timeoutMillis());
        assertEquals("", settings.password());
    }

    @Test
    void readsEnvironmentOverrides() {
        RedisSettings settings = RedisSettings.from(Map.of(
                "REDIS_HOST", "redis.example.test",
                "REDIS_PORT", "6380",
                "REDIS_TIMEOUT_MS", "1500",
                "REDIS_PASSWORD", "not-a-real-secret"));

        assertEquals("redis.example.test", settings.host());
        assertEquals(6380, settings.port());
        assertEquals(1500, settings.timeoutMillis());
        assertEquals("not-a-real-secret", settings.password());
    }

    @Test
    void rejectsInvalidPort() {
        assertThrows(IllegalArgumentException.class,
                () -> RedisSettings.from(Map.of("REDIS_PORT", "invalid")));
        assertThrows(IllegalArgumentException.class,
                () -> RedisSettings.from(Map.of("REDIS_PORT", "70000")));
    }
}
