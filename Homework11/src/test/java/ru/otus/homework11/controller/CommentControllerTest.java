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
import ru.otus.homework11.domain.Comment;
import ru.otus.homework11.dto.*;
import ru.otus.homework11.repository.CommentRepository;
import ru.otus.homework11.service.exception.BookNotExistsException;
import ru.otus.homework11.service.exception.CommentNotExistsException;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.otus.homework11.security.SecurityConstants.*;
import static ru.otus.homework11.security.SecurityConstants.TOKEN_PREFIX;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
@DisplayName("Контроллер комментариев должен")
public class CommentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CommentRepository commentRepository;

    @DirtiesContext
    @Test
    @DisplayName("добавить комментарий под авторизованным пользователем")
    void shouldAddCommentForAuthorizedUser() throws Exception {
        final RequestCommentDto requestCommentDto = new RequestCommentDto(2L, "NewComment");
        final ResponseCommentDto responseCommentDto = new ResponseCommentDto(
                4L,
                2L,
                "TestBook2",
                "NewComment");

        mockMvc.perform(post("/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestCommentDto))
                .header(HEADER_STRING, TOKEN_PREFIX + createAuthorizedToken()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(responseCommentDto)));

        Comment comment = commentRepository.findById(4L).orElseThrow(() -> new CommentNotExistsException("There is no comment with id = 4"));

        assertEquals("NewComment", comment.getText());
    }

    @DirtiesContext
    @Test
    @DisplayName("не добавить комментарий под неавторизованным пользователем")
    void shouldNotAddCommentForUnauthorizedUser() throws Exception {
        final RequestCommentDto requestCommentDto = new RequestCommentDto(2L, "NewComment");

        mockMvc.perform(post("/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestCommentDto))
                .header(HEADER_STRING, TOKEN_PREFIX + createUnauthorizedToken()))
                .andExpect(status().isForbidden());
    }

    @DirtiesContext
    @Test
    @DisplayName("редактировать комментарий под авторизованным пользователем")
    void shouldEditCommentForAuthorizedUser() throws Exception {
        final RequestCommentDto requestCommentDto = new RequestCommentDto(2L, "NewComment");
        final ResponseCommentDto responseCommentDto = new ResponseCommentDto(
                2L,
                2L,
                "TestBook2",
                "NewComment");

        mockMvc.perform(put("/comments/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestCommentDto))
                .header(HEADER_STRING, TOKEN_PREFIX + createAuthorizedToken()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(responseCommentDto)));

        Comment comment = commentRepository.findById(2L).orElseThrow(() -> new BookNotExistsException("There is no comment with id = 2"));

        assertEquals("NewComment", comment.getText());
    }

    @DirtiesContext
    @Test
    @DisplayName("не редактировать комментарий под неавторизованным пользователем")
    void shouldNotEditCommentForUnauthorizedUser() throws Exception {
        final RequestCommentDto requestCommentDto = new RequestCommentDto(2L, "NewComment");

        mockMvc.perform(put("/comments/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestCommentDto))
                .header(HEADER_STRING, TOKEN_PREFIX + createUnauthorizedToken()))
                .andExpect(status().isForbidden());
    }

    @DirtiesContext
    @Test
    @DisplayName("удалить комментарий под авторизованным пользователем")
    void shouldDeleteCommentForAuthorizedUser() throws Exception {
        mockMvc.perform(delete("/comments/1")
                .header(HEADER_STRING, TOKEN_PREFIX + createAuthorizedToken()))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));

        assertFalse(commentRepository.findById(1L).isPresent());
    }

    @DirtiesContext
    @Test
    @DisplayName("не удалить комментарий под неавторизованным пользователем")
    void shouldNotDeleteCommentForUnauthorizedUser() throws Exception {
        mockMvc.perform(delete("/comments/1")
                .header(HEADER_STRING, TOKEN_PREFIX + createUnauthorizedToken()))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("вернуть комментарий по ИД под авторизованным пользователем")
    void shouldReturnCommentByIdForAuthorizedUser() throws Exception {
        final ResponseCommentDto responseCommentDto = new ResponseCommentDto(
                3L,
                2L,
                "TestBook2",
                "TestComment2ForBook2");

        mockMvc.perform(get("/comments/3")
                .header(HEADER_STRING, TOKEN_PREFIX + createAuthorizedToken()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(responseCommentDto)));
    }

    @Test
    @DisplayName("не вернуть комментарий по ИД под неавторизованным пользователем")
    void shouldNotReturnCommentByIdForUnauthorizedUser() throws Exception {
        mockMvc.perform(get("/comments/3")
                .header(HEADER_STRING, TOKEN_PREFIX + createUnauthorizedToken()))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("вернуть список комментариев под авторизованным пользователем")
    void shouldReturnCommentListForAuthorizedUser() throws Exception {
        final ResponseCommentDto responseCommentDto1 = new ResponseCommentDto(
                1L,
                1L,
                "TestBook1",
                "TestComment1ForBook1");
        final ResponseCommentDto responseCommentDto2 = new ResponseCommentDto(
                2L,
                2L,
                "TestBook2",
                "TestComment1ForBook2");
        final ResponseCommentDto responseCommentDto3 = new ResponseCommentDto(
                3L,
                2L,
                "TestBook2",
                "TestComment2ForBook2");
        final List<ResponseCommentDto> responseCommentDtoList = Arrays.asList(responseCommentDto1, responseCommentDto2, responseCommentDto3);

        mockMvc.perform(get("/comments/")
                .header(HEADER_STRING, TOKEN_PREFIX + createAuthorizedToken()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(responseCommentDtoList)));
    }

    @Test
    @DisplayName("не вернуть список комментариев под неавторизованным пользователем")
    void shouldNotReturnCommentListForUnauthorizedUser() throws Exception {
        mockMvc.perform(get("/comments/")
                .header(HEADER_STRING, TOKEN_PREFIX + createUnauthorizedToken()))
                .andExpect(status().isForbidden());
    }

    private String createAuthorizedToken() {
        return JWT.create()
                .withSubject("commentator")
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .withClaim(AUTHORITY_CLAIM, "ROLE_COMMENTATOR")
                .sign(Algorithm.HMAC512(SECRET.getBytes()));
    }

    private String createUnauthorizedToken() {
        return JWT.create()
                .withSubject("reader")
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .withClaim(AUTHORITY_CLAIM, "ROLE_READER")
                .sign(Algorithm.HMAC512(SECRET.getBytes()));
    }
}