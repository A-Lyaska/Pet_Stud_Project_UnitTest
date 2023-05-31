package com.learning.processing.interfaces;

import com.learning.processing.DB.Account;

public interface ProcessingService {
    public void exchangeMoney (Account accountFrom, Account accountTo, int money, int userId);
}
