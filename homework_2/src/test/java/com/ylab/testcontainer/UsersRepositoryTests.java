package com.ylab.testcontainer;

import com.ylab.entity.Role;
import com.ylab.entity.User;
import com.ylab.repository.UserRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
@ExtendWith(MockitoExtension.class)
public class UsersRepositoryTests {

    private static final UserRepository userRepository = new UserRepository();

    @Container
    static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("test_db_users")
            .withUsername("daniilmolchanov")
            .withPassword("microcuts1928")
            .withInitScript("script_for_tests_users.sql");


    @BeforeAll
    @DisplayName(value = "Открытие подключения к БД и задание подключения репозиторию")
    public static void setUp() throws SQLException {
        postgresContainer.start();

        var connection = DriverManager.getConnection(
                postgresContainer.getJdbcUrl(),
                postgresContainer.getUsername(),
                postgresContainer.getPassword()
        );

        userRepository.setNewConnection(connection);
        userRepository.add(new User("maria", "5678", Role.MANAGER));
    }

    @Test
    @DisplayName(value = "Тест на проверку добавления нового пользователя")
    public void testAddNewUserInTable() throws SQLException {
        assertTrue(userRepository.add(new User("anton", "5678", Role.CLIENT)));
    }

    @Test
    @DisplayName(value = "Тест на проверку отображения всех пользователей")
    public void testGetAllUsers() throws SQLException {
        assertNotNull(userRepository.getAll());
    }

    @Test
    @DisplayName(value = "Тест удаления пользователя")
    public void testRemoveUser() throws SQLException {
        int countOfUsersBeforeRemove = userRepository.getAll().size();

        userRepository.remove(new User(1, "daniil", "1234", Role.ADMIN));

        int countOfUsersAfterRemove = userRepository.getAll().size();

       assertEquals(countOfUsersBeforeRemove - countOfUsersAfterRemove, 1);
    }

    @Test
    @DisplayName(value = "Тест на обновление информации о пользователе")
    public void testUpdateUser() throws SQLException {
        var user = new User(1, "dan", "4321", Role.MANAGER);

        userRepository.edit(user);

        assertEquals(userRepository.getAll().get(userRepository.getAll().size() - 1).getUsername(),
                "dan");
        assertEquals(userRepository.getAll().get(userRepository.getAll().size() - 1).getPassword(),
                "4321");
        assertEquals(userRepository.getAll().get(userRepository.getAll().size() - 1).getRole(),
                Role.MANAGER);
    }

    @AfterAll
    @DisplayName(value = "Закрытие подключения к БД после прохождения всех тестов")
    public static void closeConnection() {
        postgresContainer.stop();
        postgresContainer.close();
    }
}