package com.ylab;

import com.ylab.entity.Role;
import com.ylab.entity.User;
import com.ylab.service.AccessService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class AccessServiceTest {

    private final AccessService accessService = new AccessService();

//    @Test
//    public void testSameRole() {
//
//        assertThat(accessService.hasSuitableRole(new User("John", "password", Role.MANAGER),
//                Role.MANAGER)).isTrue();
//    }

}
