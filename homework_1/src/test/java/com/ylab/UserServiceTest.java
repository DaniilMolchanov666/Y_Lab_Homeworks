package com.ylab;

import com.ylab.entity.Role;
import com.ylab.entity.User;
import com.ylab.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {
    private UserService userManager;

    @BeforeEach
    public void setUp() {
        userManager = new UserService();
    }

    @Test
    public void testAddUser() {
        User user = new User("testUser", "password", Role.CLIENT);
        userManager.addUser(user);
        assertEquals(user, userManager.getUserByUsername("testUser"));
    }

    @Test
    public void testGetUserByUsername() {
        User user = new User("testUser", "password", Role.CLIENT);
        userManager.addUser(user);
        assertEquals(user, userManager.getUserByUsername("testUser"));
        assertNull(userManager.getUserByUsername("nonExistentUser"));
    }
}