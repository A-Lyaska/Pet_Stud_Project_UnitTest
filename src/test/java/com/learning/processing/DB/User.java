package com.learning.processing.DB;

import java.util.HashMap;

public class User {

    private int userId;
    private String login;
    private String password;
    private HashMap<Integer, Account> accounts;

    public User(String login, String password, int userId){
        this.login = login;
        this.password = password;
        this.userId = userId;
        accounts = new HashMap<>();
    }

    public HashMap<Integer, Account> getAccounts() {
        return accounts;
    }

    public String getPassword() {
        return password;
    }

    public int getUserId() {
        return userId;
    }

    public String getLogin() {
        return login;
    }
}
