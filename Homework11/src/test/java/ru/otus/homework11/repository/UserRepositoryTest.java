package ru.otus.homework11.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.otus.homework11.domain.Role;
import ru.otus.homework11.domain.User;
import ru.otus.homework11.service.exception.UserNotExistsException;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@DisplayName("Dao пользователей должен")
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("добавить пользователя и получить его по ИД")
    void shouldAddUserAndGetHimById() {
        final String newLogin = "New Login";
        final String newPassword = "New Password";
        final List<Role> roles = Collections.singletonList(new Role(1L, "admin"));
        User user = new User(newLogin, newPassword, roles);
        user = userRepository.save(user);
        Long newId = user.getId();
        user = userRepository.findById(user.getId()).orElseThrow(() -> new UserNotExistsException("There is no user with id = " + newId));

        assertEquals(newLogin, user.getLogin());
        assertEquals(newPassword, user.getPassword());
        assertEquals(roles.size(), user.getRoles().size());
        assertEquals(roles.get(0).getId(), user.getRoles().get(0).getId());
        assertEquals(roles.get(0).getName(), user.getRoles().get(0).getName());
    }
}
