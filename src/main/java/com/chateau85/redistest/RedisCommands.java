package com.chateau85.redistest;

interface RedisCommands {
    void set(String key, String value);
    String get(String key);
    void addToSet(String key, String... values);
    boolean isSetMember(String key, String value);
    void appendToList(String key, String... values);
    String listElement(String key, long index);
    void setHashValue(String key, String field, String value);
    void incrementHashValue(String key, String field, long amount);
    String hashValue(String key, String field);
}
