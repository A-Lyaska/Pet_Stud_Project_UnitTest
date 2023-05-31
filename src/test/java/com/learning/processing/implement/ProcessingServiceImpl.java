package com.learning.processing.implement;

import com.learning.processing.DB.Account;
import com.learning.processing.interfaces.ProcessingService;

public class ProcessingServiceImpl implements ProcessingService {
    public void exchangeMoney(Account accountFrom, Account accountTo, int money, int userId) {
            if (accountFrom.getUserId() == userId && accountFrom.getMoney() - money >= 0) {
                    accountFrom.setMoney(accountFrom.getMoney() - money);
                    accountTo.setMoney(accountTo.getMoney() + money);
            } else {
                    System.out.println("Нищеброд. Иди работай");
            }
    }
}
