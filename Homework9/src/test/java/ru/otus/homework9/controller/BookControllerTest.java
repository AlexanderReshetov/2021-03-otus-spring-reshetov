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
import ru.otus.homework9.domain.Book;
import ru.otus.homework9.dto.*;
import ru.otus.homework9.repository.BookRepository;
import ru.otus.homework9.service.exception.BookNotExistsException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
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

    @Test
    @DisplayName("добавить книгу")
    void shouldInsertBook() throws Exception {
        final RequestBookDto requestBookDto = new RequestBookDto("NewBook", 2L, 3L);
        final ResponseBookDto responseBookDto = new ResponseBookDto(
                3L,
                "NewBook",
                new ResponseAuthorDto(2L, "TestAuthor2"),
                new ResponseGenreDto(3L, "TestGenre3"),
                null);

        mockMvc.perform(post("/books").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(requestBookDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(responseBookDto)));

        Book book = bookRepository.findById(3L).orElseThrow(() -> new BookNotExistsException("There is no book with id = 3"));

        assertEquals("NewBook", book.getName());
    }

    @Test
    @DisplayName("обновить данные по книге")
    void shouldUpdateBook() throws Exception {
        final RequestBookDto requestBookDto = new RequestBookDto("NewBook", 2L, 3L);
        final ResponseBookDto responseBookDto = new ResponseBookDto(
                1L,
                "NewBook",
                new ResponseAuthorDto(2L, "TestAuthor2"),
                new ResponseGenreDto(3L, "TestGenre3"),
                null);

        mockMvc.perform(put("/books/1").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(requestBookDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(responseBookDto)));

        Book book = bookRepository.findById(1L).orElseThrow(() -> new BookNotExistsException("There is no book with id = 1"));

        assertEquals("NewBook", book.getName());
    }

    @Test
    @DisplayName("удалить книгу")
    void shouldDeleteBook() throws Exception {
        mockMvc.perform(delete("/books/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));

        assertFalse(bookRepository.findById(1L).isPresent());
    }

    @Test
    @DisplayName("вернуть книгу по ИД")
    void shouldReturnBookById() throws Exception {
        final ResponseBookDto responseBookDto = new ResponseBookDto(
                1L,
                "TestBook1",
                new ResponseAuthorDto(1L, "TestAuthor1"),
                new ResponseGenreDto(1L, "TestGenre1"),
                Collections.singletonList(new ResponseCommentDto(1L, 1L, "TestBook1", "TestComment1ForBook1")));

        mockMvc.perform(get("/books/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(responseBookDto)));
    }

    @Test
    @DisplayName("вернуть список книг")
    void shouldReturnBookList() throws Exception {
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

        mockMvc.perform(get("/books/"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(responseBookDtoList)));
    }

    @Test
    @DisplayName("вернуть список комментариев к книге по ее ИД")
    void shouldReturnCommentsForBookById() throws Exception {
        List<ResponseCommentDto> responseCommentDtoList = Arrays.asList(
                new ResponseCommentDto(2L, 2L, "TestBook2", "TestComment1ForBook2"),
                new ResponseCommentDto(3L, 2L, "TestBook2", "TestComment2ForBook2"));

        mockMvc.perform(get("/books/2/comments/"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(responseCommentDtoList)));
    }
}
