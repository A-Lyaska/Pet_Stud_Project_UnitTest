package com.learning.processing.DB;

import java.util.HashMap;

public class InDB {
    private final HashMap<Integer, User> users;

    public InDB(){
        this.users = new HashMap<>();
    }

    public HashMap<Integer, User> getUsers() {
        return users;
    }

    public void addUser(User user){
        users.put(user.getUserId(), user);
    }
}
