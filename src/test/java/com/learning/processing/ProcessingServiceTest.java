package com.learning.processing;

import com.learning.processing.DB.Account;
import com.learning.processing.DB.InDB;
import com.learning.processing.DB.User;
import com.learning.processing.implement.AccountServiceImpl;
import com.learning.processing.implement.ProcessingServiceImpl;
import com.learning.processing.implement.UserServiceImpl;
import com.learning.processing.interfaces.AccountService;
import com.learning.processing.interfaces.ProcessingService;
import com.learning.processing.interfaces.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ProcessingServiceTest {
    private UserService userService;
    private AccountService accountService;
    private ProcessingService processingService;
    private InDB inDB;

    @BeforeEach
    void setUp() {
        inDB = new InDB();
        userService = new UserServiceImpl();
        accountService = new AccountServiceImpl();
        processingService = new ProcessingServiceImpl();
    }

    @Test
    void transactionsBetweenAccountsIsValid() {
        // Создаем пользователей
        userService.reg(inDB, "Alyaska", "123", 1);
        userService.reg(inDB, "Tundra", "456", 2);

        // Создаем счета для пользователей
        int accountId1 = accountService.createAccount(inDB.getUsers().get(1), 100, 1).getAccountId();
        int accountId2 = accountService.createAccount(inDB.getUsers().get(2), 50, 2).getAccountId();

        // Проверяем балансы до транзакции
        assertEquals(100, inDB.getUsers().get(1).getAccounts().get(accountId1).getMoney());
        assertEquals(50, inDB.getUsers().get(2).getAccounts().get(accountId2).getMoney());

        // Выполняем транзакцию между счетами разных пользователей
        processingService.exchangeMoney(inDB.getUsers().get(1).getAccounts().get(accountId1),
                inDB.getUsers().get(2).getAccounts().get(accountId2), 50, 1);

        // Проверяем балансы после транзакции
        assertEquals(50, inDB.getUsers().get(1).getAccounts().get(accountId1).getMoney());
        assertEquals(100, inDB.getUsers().get(2).getAccounts().get(accountId2).getMoney());
    }

    @Test
    void transactionsBetweenAccountsOfOneUserIsValid() {
        // Создаем пользователя
        userService.reg(inDB, "Alyaska", "123", 1);

        // Создаем счета для пользователя
        int accountId1 = accountService.createAccount(inDB.getUsers().get(1), 100, 1).getAccountId();
        int accountId2 = accountService.createAccount(inDB.getUsers().get(1), 50, 2).getAccountId();

        // Проверяем балансы до транзакции
        assertEquals(100, inDB.getUsers().get(1).getAccounts().get(accountId1).getMoney());
        assertEquals(50, inDB.getUsers().get(1).getAccounts().get(accountId2).getMoney());

        // Выполняем транзакцию между счетами одного пользователя
        processingService.exchangeMoney(inDB.getUsers().get(1).getAccounts().get(accountId1),
                inDB.getUsers().get(1).getAccounts().get(accountId2), 50, 1);

        // Проверяем балансы после транзакции
        assertEquals(50, inDB.getUsers().get(1).getAccounts().get(accountId1).getMoney());
        assertEquals(100, inDB.getUsers().get(1).getAccounts().get(accountId2).getMoney());
    }

    @Test
    void transactionRollbackByError() {
        // Создаем пользователей
        userService.reg(inDB, "Alyaska", "123", 1);
        userService.reg(inDB, "Tundra", "456", 2);

        // Создаем счета для пользователей
        int accountId1 = accountService.createAccount(inDB.getUsers().get(1), 100, 1).getAccountId();
        int accountId2 = accountService.createAccount(inDB.getUsers().get(2), 50, 2).getAccountId();

        // Проверяем балансы до транзакции
        assertEquals(100, inDB.getUsers().get(1).getAccounts().get(accountId1).getMoney());
        assertEquals(50, inDB.getUsers().get(2).getAccounts().get(accountId2).getMoney());

        // Выполняем транзакцию, которая вызывает ошибку (недостаточно средств)
        processingService.exchangeMoney(inDB.getUsers().get(1).getAccounts().get(accountId1),
                inDB.getUsers().get(2).getAccounts().get(accountId2), 200, 1);

        // Проверяем, что балансы остались неизменными
        assertEquals(100, inDB.getUsers().get(1).getAccounts().get(accountId1).getMoney());
        assertEquals(50, inDB.getUsers().get(2).getAccounts().get(accountId2).getMoney());
    }

    @Test
    void transactionNotPermitIfNegativeDebt() {
        // Создаем пользователей
        userService.reg(inDB, "Alyaska", "123", 1);
        userService.reg(inDB, "Tundra", "456", 2);

        // Создаем счета для пользователей
        int accountId1 = accountService.createAccount(inDB.getUsers().get(1), 100, 1).getAccountId();
        int accountId2 = accountService.createAccount(inDB.getUsers().get(2), 50, 2).getAccountId();

        // Проверяем балансы до транзакции
        assertEquals(100, inDB.getUsers().get(1).getAccounts().get(accountId1).getMoney());
        assertEquals(50, inDB.getUsers().get(2).getAccounts().get(accountId2).getMoney());

        // Выполняем транзакцию с отрицательной суммой (долгом)
        processingService.exchangeMoney(inDB.getUsers().get(1).getAccounts().get(accountId1),
                inDB.getUsers().get(2).getAccounts().get(accountId2), 150, 1);

        // Проверяем, что транзакция не была выполнена
        assertEquals(100, inDB.getUsers().get(1).getAccounts().get(accountId1).getMoney());
        assertEquals(50, inDB.getUsers().get(2).getAccounts().get(accountId2).getMoney());
    }
}
