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
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.homework11.domain.Book;
import ru.otus.homework11.dto.*;
import ru.otus.homework11.repository.BookRepository;
import ru.otus.homework11.service.exception.BookNotExistsException;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.otus.homework11.security.SecurityConstants.*;
import static ru.otus.homework11.security.SecurityConstants.TOKEN_PREFIX;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
@DisplayName("Контроллер книг должен")
public class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private BookRepository bookRepository;

    @DirtiesContext
    @Test
    @DisplayName("добавить книгу под авторизованным пользователем")
    void shouldInsertBookForAuthorizedUser() throws Exception {
        final RequestBookDto requestBookDto = new RequestBookDto("NewBook", 2L, 3L);
        final ResponseBookDto responseBookDto = new ResponseBookDto(
                3L,
                "NewBook",
                new ResponseAuthorDto(2L, "TestAuthor2"),
                new ResponseGenreDto(3L, "TestGenre3"),
                null);

        mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBookDto))
                .header(HEADER_STRING, TOKEN_PREFIX + createAuthorizedToken()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(responseBookDto)));

        Book book = bookRepository.findById(3L).orElseThrow(() -> new BookNotExistsException("There is no book with id = 3"));

        assertEquals("NewBook", book.getName());
    }

    @DirtiesContext
    @Test
    @DisplayName("не добавить книгу под неавторизованным пользователем")
    void shouldNotInsertBookForUnauthorizedUser() throws Exception {
        final RequestBookDto requestBookDto = new RequestBookDto("NewBook", 2L, 3L);

        mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBookDto))
                .header(HEADER_STRING, TOKEN_PREFIX + createUnauthorizedToken()))
                .andExpect(status().isForbidden());
    }

    @DirtiesContext
    @Test
    @DisplayName("обновить данные по книге под авторизованным пользователем")
    void shouldUpdateBookForAuthorizedUser() throws Exception {
        final RequestBookDto requestBookDto = new RequestBookDto("NewBook", 2L, 3L);
        final ResponseBookDto responseBookDto = new ResponseBookDto(
                1L,
                "NewBook",
                new ResponseAuthorDto(2L, "TestAuthor2"),
                new ResponseGenreDto(3L, "TestGenre3"),
                null);

        mockMvc.perform(put("/books/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBookDto))
                .header(HEADER_STRING, TOKEN_PREFIX + createAuthorizedToken()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(responseBookDto)));

        Book book = bookRepository.findById(1L).orElseThrow(() -> new BookNotExistsException("There is no book with id = 1"));

        assertEquals("NewBook", book.getName());
    }

    @DirtiesContext
    @Test
    @DisplayName("не обновить данные по книге под неавторизованным пользователем")
    void shouldNotUpdateBookForUnauthorizedUser() throws Exception {
        final RequestBookDto requestBookDto = new RequestBookDto("NewBook", 2L, 3L);

        mockMvc.perform(put("/books/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBookDto))
                .header(HEADER_STRING, TOKEN_PREFIX + createUnauthorizedToken()))
                .andExpect(status().isForbidden());
    }

    @DirtiesContext
    @Test
    @DisplayName("удалить книгу под авторизованным пользователем")
    void shouldDeleteBookForAuthorizedUser() throws Exception {
        mockMvc.perform(delete("/books/1")
                .header(HEADER_STRING, TOKEN_PREFIX + createAuthorizedToken()))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));

        assertFalse(bookRepository.findById(1L).isPresent());
    }

    @DirtiesContext
    @Test
    @DisplayName("не удалить книгу под неавторизованным пользователем")
    void shouldNotDeleteBookForUnauthorizedUser() throws Exception {
        mockMvc.perform(delete("/books/1")
                .header(HEADER_STRING, TOKEN_PREFIX + createUnauthorizedToken()))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("вернуть книгу по ИД под авторизованным пользователем")
    void shouldReturnBookByIdForAuthorizedUser() throws Exception {
        final ResponseBookDto responseBookDto = new ResponseBookDto(
                1L,
                "TestBook1",
                new ResponseAuthorDto(1L, "TestAuthor1"),
                new ResponseGenreDto(1L, "TestGenre1"),
                Collections.singletonList(new ResponseCommentDto(1L, 1L, "TestBook1", "TestComment1ForBook1")));

        mockMvc.perform(get("/books/1")
                .header(HEADER_STRING, TOKEN_PREFIX + createAuthorizedToken()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(responseBookDto)));
    }

    @Test
    @DisplayName("не вернуть книгу по ИД под неавторизованным пользователем")
    void shouldNotReturnBookByIdForUnauthorizedUser() throws Exception {
        mockMvc.perform(get("/books/1")
                .header(HEADER_STRING, TOKEN_PREFIX + createUnauthorizedToken()))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("вернуть список книг под авторизованным пользователем")
    void shouldReturnBookListForAuthorizedUser() throws Exception {
        final ResponseBookDto responseBookDto1 = new ResponseBookDto(
                1L,
                "TestBook1",
                new ResponseAuthorDto(1L, "TestAuthor1"),
                new ResponseGenreDto(1L, "TestGenre1"),
                Collections.singletonList(new ResponseCommentDto(1L, 1L, "TestBook1", "TestComment1ForBook1")));
        final ResponseBookDto responseBookDto2 = new ResponseBookDto(
                2L,
                "TestBook2",
                new ResponseAuthorDto(1L, "TestAuthor1"),
                new ResponseGenreDto(2L, "TestGenre2"),
                Arrays.asList(
                        new ResponseCommentDto(2L, 2L, "TestBook2", "TestComment1ForBook2"),
                        new ResponseCommentDto(3L, 2L, "TestBook2", "TestComment2ForBook2")));
        final List<ResponseBookDto> responseBookDtoList = Arrays.asList(responseBookDto1, responseBookDto2);

        mockMvc.perform(get("/books/")
                .header(HEADER_STRING, TOKEN_PREFIX + createAuthorizedToken()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(responseBookDtoList)));
    }

    @Test
    @DisplayName("не вернуть список книг под неавторизованным пользователем")
    void shouldNotReturnBookListForUnauthorizedUser() throws Exception {
        mockMvc.perform(get("/books/")
                .header(HEADER_STRING, TOKEN_PREFIX + createUnauthorizedToken()))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("вернуть список комментариев к книге по ее ИД под авторизованным пользователем")
    void shouldReturnCommentsForBookByIdForAuthorizedUser() throws Exception {
        List<ResponseCommentDto> responseCommentDtoList = Arrays.asList(
                new ResponseCommentDto(2L, 2L, "TestBook2", "TestComment1ForBook2"),
                new ResponseCommentDto(3L, 2L, "TestBook2", "TestComment2ForBook2"));

        mockMvc.perform(get("/books/2/comments/")
                .header(HEADER_STRING, TOKEN_PREFIX + createAuthorizedToken()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(responseCommentDtoList)));
    }

    @Test
    @DisplayName("не вернуть список комментариев к книге по ее ИД под неавторизованным пользователем")
    void shouldNotReturnCommentsForBookByIdForUnauthorizedUser() throws Exception {
        mockMvc.perform(get("/books/2/comments/")
                .header(HEADER_STRING, TOKEN_PREFIX + createUnauthorizedToken()))
                .andExpect(status().isForbidden());
    }

    private String createAuthorizedToken() {
        return JWT.create()
                .withSubject("reader")
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SECRET.getBytes()));
    }

    private String createUnauthorizedToken() {
        return JWT.create()
                .withSubject("commentator")
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SECRET.getBytes()));
    }
}
