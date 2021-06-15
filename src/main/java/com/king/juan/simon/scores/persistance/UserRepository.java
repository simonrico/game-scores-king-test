package com.king.juan.simon.scores.persistance;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.king.juan.simon.scores.model.User;

public class UserRepository {

    private Map<Integer, User> users;

    public UserRepository() {
        users = new ConcurrentHashMap<>();
    }

    public User addUser(User user) {
        return users.put(user.getUserID(), user);
    }

    public User getUser(Integer userId) {
        return users.get(userId);
    }


}
