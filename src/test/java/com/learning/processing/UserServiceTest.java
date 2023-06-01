package com.learning.processing;

import com.learning.processing.DB.InDB;
import com.learning.processing.DB.User;
import com.learning.processing.implement.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private UserServiceImpl userService;
    private InDB inDB;

    @BeforeEach
    void setUp() {
        inDB = new InDB();
        userService = new UserServiceImpl();
    }

    @Test
    void isUserRegisterCorrectly() {
        // Register a new user
        userService.reg(inDB, "Alyaska", "123", 1);

        // Check if the user is registered correctly in the in-memory database
        assertTrue(inDB.getUsers().containsKey(1));
        User user = inDB.getUsers().get(1);
        assertEquals("Alyaska", user.getLogin());
        assertEquals("123", user.getPassword());
    }

    @Test
    void isUserAuthCorrectly() {
        // Register a new user
        userService.reg(inDB, "Alyaska", "123", 1);

        // Authenticate the user
        int userId = userService.auth("Alyaska", "123", inDB);

        // Check if the authentication is successful
        assertEquals(1, userId);
    }

    @Test
    void userIsAbleToCreateAccount() {
        // Register a new user
        userService.reg(inDB, "Alyaska", "123", 1);

        // Authenticate the user
        int authId = userService.auth("Alyaska", "123", inDB);

        // Create an account for the user
        userService.createAccount(inDB.getUsers().get(authId), authId, 100);

        // Check if the account is created successfully
        assertTrue(inDB.getUsers().get(authId).getAccounts().size() > 0);
    }
}
