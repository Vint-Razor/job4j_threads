package ru.job4j.ref;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class UserCache {
    private final ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<>();
    private final AtomicInteger id = new AtomicInteger();

    private User userClone(User user) {
        User clone = User.of(user.getName());
        clone.setId(user.getId());
        return clone;
    }

    public void add(User user) {
        user.setId(id.incrementAndGet());
        users.put(user.getId(), user);
    }

    private void validateId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Значения индекса должно быть больше ноля");
        }
    }

    public User findById(int id) {
        validateId(id);
        User user = null;
        if (users.containsKey(id)) {
            user = userClone(users.get(id));
        }
        return user;
    }

    public List<User> findAll() {
        ArrayList<User> cloneList = new ArrayList<>();
        for (User user : users.values()) {
            cloneList.add(userClone(user));
        }
        return cloneList;
    }
}
