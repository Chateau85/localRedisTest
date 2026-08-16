package com.chateau85.redistest;

import java.util.List;
import java.util.Objects;

final class RedisExampleService {
    private final RedisCommands commands;

    RedisExampleService(RedisCommands commands) {
        this.commands = Objects.requireNonNull(commands, "commands");
    }

    List<String> run() {
        commands.set("NAME", "Cheolwoo Nam");
        commands.addToSet("myset", "element1", "element2", "element3");
        commands.appendToList("mylist", "element1", "element2", "element3");
        commands.setHashValue("myhash", "word1", Integer.toString(2));
        commands.incrementHashValue("myhash", "word2", 1);

        return List.of(
                commands.get("NAME"),
                "element2 is a member : " + commands.isSetMember("myset", "element2"),
                "element at index 1 : " + commands.listElement("mylist", 1),
                "frequency of word1 : " + commands.hashValue("myhash", "word1"),
                "frequency of word2 : " + commands.hashValue("myhash", "word2"));
    }
}
