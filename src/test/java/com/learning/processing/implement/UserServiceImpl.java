package com.learning.processing.implement;

import java.util.Objects;
import java.util.Random;

import com.learning.processing.DB.Account;
import com.learning.processing.DB.InDB;
import com.learning.processing.DB.User;
import com.learning.processing.interfaces.UserService;

public class UserServiceImpl implements UserService {
    public void reg(InDB dataBase, String login, String password, int userId) {
        if(dataBase.getUsers() == null || dataBase.getUsers().entrySet().stream().noneMatch(user ->Objects.equals(user.getValue().getLogin(), login))){
            User user = new User(login, password, userId);
            dataBase.addUser(user);
        } else {
            System.out.println("Иди своей дорогой сталкер. Такой уже на базе");
        }
     }

     public int auth(String login, String password, InDB dataBase) {
        return dataBase.getUsers().entrySet().stream().filter(user -> user.getValue().getLogin() == login && user.getValue().getPassword() == password).findAny().get().getValue().getUserId();
     }
    
     public void createAccount(User user, int authId, int money){ 
        Random random = new Random();
        if (user.getUserId() == authId) {
            user.getAccounts().put(12, new Account(user, money, random.nextInt(10000)));
        } else {
            System.out.println("Доступа нет. Невозможно создать счёт.");
        }
    }
}
