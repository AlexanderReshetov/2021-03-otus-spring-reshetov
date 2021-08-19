package ru.otus.homework9.controller;

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
import ru.otus.homework9.domain.Comment;
import ru.otus.homework9.dto.*;
import ru.otus.homework9.repository.CommentRepository;
import ru.otus.homework9.service.exception.BookNotExistsException;
import ru.otus.homework9.service.exception.CommentNotExistsException;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
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

    @Test
    @DisplayName("добавить комментарий")
    void shouldAddComment() throws Exception {
        final RequestCommentDto requestCommentDto = new RequestCommentDto(2L, "NewComment");
        final ResponseCommentDto responseCommentDto = new ResponseCommentDto(
                4L,
                2L,
                "TestBook2",
                "NewComment");

        mockMvc.perform(post("/comments").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(requestCommentDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(responseCommentDto)));

        Comment comment = commentRepository.findById(4L).orElseThrow(() -> new CommentNotExistsException("There is no comment with id = 4"));

        assertEquals("NewComment", comment.getText());
    }

    @Test
    @DisplayName("редактировать комментарий")
    void shouldEditComment() throws Exception {
        final RequestCommentDto requestCommentDto = new RequestCommentDto(2L, "NewComment");
        final ResponseCommentDto responseCommentDto = new ResponseCommentDto(
                2L,
                2L,
                "TestBook2",
                "NewComment");

        mockMvc.perform(put("/comments/2").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(requestCommentDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(responseCommentDto)));

        Comment comment = commentRepository.findById(2L).orElseThrow(() -> new BookNotExistsException("There is no comment with id = 2"));

        assertEquals("NewComment", comment.getText());
    }

    @Test
    @DisplayName("удалить комментарий")
    void shouldDeleteComment() throws Exception {
        mockMvc.perform(delete("/comments/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));

        assertFalse(commentRepository.findById(1L).isPresent());
    }

    @Test
    @DisplayName("вернуть комментарий по ИД")
    void shouldReturnCommentById() throws Exception {
        final ResponseCommentDto responseCommentDto = new ResponseCommentDto(
                3L,
                2L,
                "TestBook2",
                "TestComment2ForBook2");

        mockMvc.perform(get("/comments/3"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(responseCommentDto)));
    }

    @Test
    @DisplayName("вернуть список комментариев")
    void shouldReturnCommentList() throws Exception {
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

        mockMvc.perform(get("/comments/"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(responseCommentDtoList)));
    }
}