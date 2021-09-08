package ru.otus.library.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestOperations;
import ru.otus.library.dto.ResponseAuthorDto;
import ru.otus.library.security.SecurityConstants;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
@DisplayName("Контроллер авторов должен")
public class AuthorControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private RestOperations restOperations;

    @Test
    @DisplayName("вернуть автора по ИД под авторизованным пользователем")
    void shouldReturnAuthorByIdForAuthorizedUser() throws Exception {
        final ResponseAuthorDto responseAuthorDto = new ResponseAuthorDto(1L, "LibraryAuthor1");

        mockMvc.perform(get("/authors/1").header(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + createAuthorizedToken()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(responseAuthorDto)));
    }

    @Test
    @DisplayName("не вернуть автора по ИД под неавторизованным пользователем")
    void shouldNotReturnAuthorByIdForUnauthorizedUser() throws Exception {
        mockMvc.perform(get("/authors/1").header(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + createUnauthorizedToken()))
                .andExpect(status().isForbidden());
    }

    @DirtiesContext
    @Test
    @DisplayName("вернуть список авторов под авторизованным пользователем")
    void shouldReturnAuthorListForAuthorizedUser() throws Exception {
        final List<ResponseAuthorDto> responseAuthorDtoList = Arrays.asList(
                new ResponseAuthorDto(1L, "TestAuthor1"),
                new ResponseAuthorDto(2L, "TestAuthor2"));
        when(restOperations.exchange(any(), eq(String.class))).thenReturn(ResponseEntity.ok("user "));
        when(restOperations.exchange(any(), Matchers.<ParameterizedTypeReference<List<ResponseAuthorDto>>>any()))
                .thenReturn(ResponseEntity.ok(Arrays.asList(new ResponseAuthorDto(1L, "TestAuthor1"), new ResponseAuthorDto(2L, "TestAuthor2"))));

        mockMvc.perform(get("/authors/").header(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + createAuthorizedToken()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(responseAuthorDtoList)));
    }

    @Test
    @DisplayName("не вернуть список авторов под неавторизованным пользователем")
    void shouldNotReturnAuthorListForUnauthorizedUser() throws Exception {
        mockMvc.perform(get("/authors/").header(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + createUnauthorizedToken()))
                .andExpect(status().isForbidden());
    }

    private String createAuthorizedToken() {
        return JWT.create()
                .withSubject("reader")
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .withClaim(SecurityConstants.AUTHORITY_CLAIM, "ROLE_READER")
                .sign(Algorithm.HMAC512(SecurityConstants.SECRET.getBytes()));
    }

    private String createUnauthorizedToken() {
        return JWT.create()
                .withSubject("commentator")
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .withClaim(SecurityConstants.AUTHORITY_CLAIM, "ROLE_COMMENTATOR")
                .sign(Algorithm.HMAC512(SecurityConstants.SECRET.getBytes()));
    }
}
