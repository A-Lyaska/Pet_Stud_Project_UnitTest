package com.learning.processing;

import com.learning.processing.DB.Account;
import com.learning.processing.DB.InDB;
import com.learning.processing.DB.User;
import com.learning.processing.implement.AccountServiceImpl;
import com.learning.processing.implement.UserServiceImpl;
import com.learning.processing.interfaces.AccountService;
import com.learning.processing.interfaces.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class AccountServiceTest {

    private UserService userService;
    private AccountService accountService;
    private InDB inDB;

    @BeforeEach
    void setUp() {
        inDB = new InDB();
        userService = new UserServiceImpl();
        accountService = new AccountServiceImpl();
    }

    @Test
    void createdAccountIsBelongToUser() {
        // Создаем пользователя
        User user = new User("ALyaska", "123", 1);
        userService.reg(inDB, user.getLogin(), user.getPassword(), user.getUserId());

        // Создаем счет для пользователя
        Account account = accountService.createAccount(user, 100, 1);

        // Проверяем, что созданный счет принадлежит указанному пользователю
        assertEquals(user.getUserId(), account.getUserId());
    }

    @Test
    void nonAuthorizedUserHasNoAccessToAccount() {
        // Создаем пользователей
        User user1 = new User("ALyaska", "123", 1);
        User user2 = new User("Tundra", "456", 2);
        userService.reg(inDB, user1.getLogin(), user1.getPassword(), user1.getUserId());
        userService.reg(inDB, user2.getLogin(), user2.getPassword(), user2.getUserId());

        // Создаем счет для первого пользователя
        Account account = accountService.createAccount(user1, 100, 1);

        // Проверяем, что второй пользователь не имеет доступа к счету первого пользователя
        assertFalse(accountService.hasAccess(user2, account.getAccountId(), user2.getUserId()));
    }

    @Test
    void accountIsBelongToUser() {
        // Создаем пользователя
        User user = new User("ALyaska", "123", 1);
        userService.reg(inDB, user.getLogin(), user.getPassword(), user.getUserId());

        // Создаем счет для пользователя
        Account account = accountService.createAccount(user, 100, 1);

        // Проверяем, что поле userId у счета не пустое
        assertNotNull(account.getUserId());
    }
}
