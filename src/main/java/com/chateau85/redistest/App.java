package com.chateau85.redistest;

public final class App {
    private App() {
    }

    public static void main(String[] args) {
        RedisSettings settings = RedisSettings.fromEnvironment();
        try (JedisRedisCommands commands = JedisRedisCommands.connect(settings)) {
            new RedisExampleService(commands).run().forEach(System.out::println);
        }
    }
}
