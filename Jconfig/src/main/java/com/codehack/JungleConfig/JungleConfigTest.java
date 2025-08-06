package com.codehack.JungleConfig;

import com.codehack.JungleConfig.JungleConfig;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.File;
import java.util.*;

public class JungleConfigTest {

    static class User {
        public String name;
        public int age;

        public User() {}
        public User(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String toString() {
            return name + " (" + age + ")";
        }
    }
private static long timest = System.currentTimeMillis();
    public static void main(String[] args) {
        System.out.println("TimeStamp: "+(System.currentTimeMillis()-timest));
        File configFile = new File("test_config.json");
        JungleConfig config = new JungleConfig(configFile);

        // Primitive sets
        config.Set("user.name", "Himansa");
        config.Set("user.age", 19);
        config.Set("is.coder", true);

        // Get directly
        String name = config.get("user.name", String.class);
        int age = config.get("user.age", Integer.class);
        boolean isCoder = config.get("is.coder", Boolean.class);
        System.out.println("Name: " + name + ", Age: " + age + ", Coder: " + isCoder);
        System.out.println("TimeStamp: "+(System.currentTimeMillis()-timest));

        // Safe get
        config.Get("user.name", String.class).ifPresent(val -> System.out.println("Safe name: " + val));
        System.out.println("TimeStamp: "+(System.currentTimeMillis()-timest));

        // POJO
        config.SetPOJO("user.profile", new User("Himansa", 19));
        User u = config.get("user.profile", User.class);
        System.out.println("User Profile: " + u);
        System.out.println("TimeStamp: "+(System.currentTimeMillis()-timest));

        // Collection
        List<String> skills = Arrays.asList("Java", "C++", "Assembly");
        config.Set("skills", skills);
        List<String> loadedSkills = config.getCollection("skills", new TypeReference<List<String>>() {});
        System.out.println("Skills: " + loadedSkills);
        System.out.println("TimeStamp: "+(System.currentTimeMillis()-timest));

        // Transaction test
        config.BeginTransaction();
        config.Set("temp.key1", "value1");
        config.Set("temp.key2", "value2");
        config.Rollback(); // Should discard above
        System.out.println("TimeStamp: "+(System.currentTimeMillis()-timest));

        boolean hasKey = config.Get("temp.key1", String.class).isPresent();
        System.out.println("temp.key1 exists after rollback? " + hasKey);
        System.out.println("TimeStamp: "+(System.currentTimeMillis()-timest));

        // Remove key test
        config.Set("to.remove", "bye");
        System.out.println("Before remove: " + config.get("to.remove", String.class));
        config.Remove("to.remove");
        System.out.println("After remove: " + config.Get("to.remove", String.class).isPresent());
        System.out.println("TimeStamp: "+(System.currentTimeMillis()-timest));
        config.EndTransaction();
                System.out.println("TimeStamp: "+(System.currentTimeMillis()-timest));

    }
}
