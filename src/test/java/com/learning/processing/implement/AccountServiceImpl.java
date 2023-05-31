package com.learning.processing.implement;

import com.learning.processing.DB.Account;
import com.learning.processing.DB.User;
import com.learning.processing.interfaces.AccountService;

public class AccountServiceImpl implements AccountService {

    public Account createAccount(User user, int money, int accountId) {
        return new Account(user, money, accountId);
    }

    public void deleteAccount(User user, int accountId) {
        user.getAccounts().remove(accountId);
    }

    public boolean hasAccess(User user,int accountId, int userId) {
        return user.getAccounts().values().stream().anyMatch(account -> account.getAccountId() == accountId && account.getUserId() == userId);
    }

}
