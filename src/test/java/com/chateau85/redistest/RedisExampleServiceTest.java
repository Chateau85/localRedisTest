package com.chateau85.redistest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

class RedisExampleServiceTest {
    @Test
    void exercisesRedisDataTypesWithoutARealServer() {
        List<String> output = new RedisExampleService(new InMemoryRedisCommands()).run();

        assertEquals(List.of(
                "Cheolwoo Nam",
                "element2 is a member : true",
                "element at index 1 : element2",
                "frequency of word1 : 2",
                "frequency of word2 : 1"), output);
    }

    private static final class InMemoryRedisCommands implements RedisCommands {
        private final Map<String, String> strings = new HashMap<>();
        private final Map<String, Set<String>> sets = new HashMap<>();
        private final Map<String, List<String>> lists = new HashMap<>();
        private final Map<String, Map<String, Long>> hashes = new HashMap<>();

        @Override public void set(String key, String value) { strings.put(key, value); }
        @Override public String get(String key) { return strings.get(key); }
        @Override public void addToSet(String key, String... values) {
            sets.computeIfAbsent(key, ignored -> new HashSet<>()).addAll(Arrays.asList(values));
        }
        @Override public boolean isSetMember(String key, String value) {
            return sets.getOrDefault(key, Set.of()).contains(value);
        }
        @Override public void appendToList(String key, String... values) {
            lists.computeIfAbsent(key, ignored -> new ArrayList<>()).addAll(Arrays.asList(values));
        }
        @Override public String listElement(String key, long index) {
            return lists.get(key).get(Math.toIntExact(index));
        }
        @Override public void setHashValue(String key, String field, String value) {
            hashes.computeIfAbsent(key, ignored -> new HashMap<>()).put(field, Long.parseLong(value));
        }
        @Override public void incrementHashValue(String key, String field, long amount) {
            hashes.computeIfAbsent(key, ignored -> new HashMap<>()).merge(field, amount, Long::sum);
        }
        @Override public String hashValue(String key, String field) {
            Long value = hashes.getOrDefault(key, Map.of()).get(field);
            return value == null ? null : value.toString();
        }
    }
}
