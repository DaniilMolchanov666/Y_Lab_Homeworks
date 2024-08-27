package com.ylab;

import com.ylab.entity.Role;
import com.ylab.entity.User;
import com.ylab.service.AuthenticationService;
import com.ylab.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    public void testAuthenticateUser() {

        var user = new User("John", "password", Role.ADMIN);

        when(userService.getUserByUsername("John")).thenReturn(user);

        User findedUser = userService.getUserByUsername(user.getUsername());

        assertThat(findedUser).isNotNull();
    }
}
