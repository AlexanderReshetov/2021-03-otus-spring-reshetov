package ru.otus.genre.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import ru.otus.genre.repository.UserRepository;
import ru.otus.genre.domain.User;
import ru.otus.genre.service.exception.UserNotExistsException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис загрузки пользователей должен")
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("загрузить существующего пользователя")
    void shouldLoadExistingUser() {
        final String userLogin = "user";
        final String userPassword = "password";
        when(userRepository.findByLogin(userLogin)).thenReturn(new User(userLogin, userPassword, null));

        UserDetails userDetails = userService.loadUserByUsername(userLogin);

        assertEquals(userLogin, userDetails.getUsername());
        assertEquals(userPassword, userDetails.getPassword());
    }

    @Test
    @DisplayName("выдать сообщение об ошибке при попытке загрузить несуществующего пользователя")
    void shouldThrowExceptionIfTryToLoadWrongUser() {
        final String wrongUserLogin = "Wrong user";
        when(userRepository.findByLogin(wrongUserLogin)).thenThrow(UserNotExistsException.class);

        assertThrows(UserNotExistsException.class, () -> userRepository.findByLogin(wrongUserLogin));
    }
}
