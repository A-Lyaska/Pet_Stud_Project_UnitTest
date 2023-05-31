package com.learning.processing.interfaces;

import com.learning.processing.DB.Account;
import com.learning.processing.DB.User;

public interface  AccountService {
    Account createAccount(User user, int money, int accountId);
    void deleteAccount(User user, int accountId);
    boolean hasAccess(User user, int accountId, int userId);
}
