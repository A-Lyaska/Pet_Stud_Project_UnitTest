package com.learning.processing.interfaces;

import com.learning.processing.DB.InDB;
import com.learning.processing.DB.User;

public interface UserService {
    public void reg(InDB dataBase, String login, String password, int userId);

     public int auth(String login, String password, InDB dataBase);
     
     public void createAccount(User user, int authId, int money);
}
