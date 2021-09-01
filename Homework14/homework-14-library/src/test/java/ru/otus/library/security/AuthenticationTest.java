package ru.otus.library.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.otus.library.security.SecurityConstants.*;

@AutoConfigureMockMvc
@SpringBootTest
@DisplayName("Фильтр аутентификации должен")
public class AuthenticationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("вернуть токен для правильных логина и пароля")
    void shouldReturnTokenForRightCredentials() throws Exception {
        final String token = mockMvc.perform(get("/login").contentType(MediaType.APPLICATION_JSON)
                .content("{\"login\": \"admin\",\"password\": \"password\"}"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString()
                .replace("admin ", TOKEN_PREFIX);
        final DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                .build()
                .verify(token.replace(TOKEN_PREFIX, ""));
        final String userName = decodedJWT.getSubject();
        final String authorities = decodedJWT.getClaim(AUTHORITY_CLAIM).asString();

        assertEquals("admin", userName);
        assertEquals("ROLE_ADMIN", authorities);
    }

    @Test
    @DisplayName("не вернуть токен для неправильных логина и пароля")
    void shouldNotReturnTokenForWrongCredentials() throws Exception {
        mockMvc.perform(get("/login").contentType(MediaType.APPLICATION_JSON)
                .content("{\"login\": \"admin\",\"password\": \"pwd\"}"))
                .andExpect(status().isUnauthorized());
    }
}
