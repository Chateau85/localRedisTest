package com.chateau85.redistest;

import java.util.Map;

record RedisSettings(String host, int port, int timeoutMillis, String password) {
    private static final int DEFAULT_PORT = 6379;
    private static final int DEFAULT_TIMEOUT_MILLIS = 3000;

    RedisSettings {
        if (host == null || host.isBlank()) throw new IllegalArgumentException("Redis host must not be blank");
        if (port < 1 || port > 65535) throw new IllegalArgumentException("Redis port must be between 1 and 65535");
        if (timeoutMillis < 1) throw new IllegalArgumentException("Redis timeout must be positive");
        password = password == null ? "" : password;
    }

    static RedisSettings fromEnvironment() {
        return from(System.getenv());
    }

    static RedisSettings from(Map<String, String> environment) {
        String host = environment.getOrDefault("REDIS_HOST", "127.0.0.1");
        int port = parseInteger(environment, "REDIS_PORT", DEFAULT_PORT);
        int timeout = parseInteger(environment, "REDIS_TIMEOUT_MS", DEFAULT_TIMEOUT_MILLIS);
        return new RedisSettings(host, port, timeout, environment.getOrDefault("REDIS_PASSWORD", ""));
    }

    private static int parseInteger(Map<String, String> environment, String name, int defaultValue) {
        String value = environment.get(name);
        if (value == null || value.isBlank()) return defaultValue;
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException(name + " must be an integer", exception);
        }
    }
}
