package ru.otus.homework11.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.homework11.dto.ResponseGenreDto;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.otus.homework11.security.SecurityConstants.*;
import static ru.otus.homework11.security.SecurityConstants.TOKEN_PREFIX;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
@DisplayName("Контроллер жанров должен")
public class GenreControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("вернуть жанр по ИД под авторизованным пользователем")
    void shouldReturnGenreByIdForAuthorizedUser() throws Exception {
        final ResponseGenreDto responseGenreDto = new ResponseGenreDto(1L, "TestGenre1");

        mockMvc.perform(get("/genres/1").header(HEADER_STRING, TOKEN_PREFIX + createAuthorizedToken()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(responseGenreDto)));
    }

    @Test
    @DisplayName("не вернуть жанр по ИД под неавторизованным пользователем")
    void shouldNotReturnGenreByIdForUnauthorizedUser() throws Exception {
        mockMvc.perform(get("/genres/1").header(HEADER_STRING, TOKEN_PREFIX + createUnauthorizedToken()))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("вернуть список жанров под авторизованным пользователем")
    void shouldReturnGenreListForAuthorizedUser() throws Exception {
        final List<ResponseGenreDto> responseGenreDtoList = Arrays.asList(
                new ResponseGenreDto(1L, "TestGenre1"),
                new ResponseGenreDto(2L, "TestGenre2"),
                new ResponseGenreDto(3L, "TestGenre3"));

        mockMvc.perform(get("/genres/").header(HEADER_STRING, TOKEN_PREFIX + createAuthorizedToken()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(responseGenreDtoList)));
    }

    @Test
    @DisplayName("не вернуть список жанров под неавторизованным пользователем")
    void shouldNotReturnGenreListForUnauthorizedUser() throws Exception {
        mockMvc.perform(get("/genres/").header(HEADER_STRING, TOKEN_PREFIX + createUnauthorizedToken()))
                .andExpect(status().isForbidden());
    }

    private String createAuthorizedToken() {
        return JWT.create()
                .withSubject("reader")
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .withClaim(AUTHORITY_CLAIM, "ROLE_READER")
                .sign(Algorithm.HMAC512(SECRET.getBytes()));
    }

    private String createUnauthorizedToken() {
        return JWT.create()
                .withSubject("commentator")
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .withClaim(AUTHORITY_CLAIM, "ROLE_COMMENTATOR")
                .sign(Algorithm.HMAC512(SECRET.getBytes()));
    }
}
