package ru.otus.homework10.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.homework10.dto.ResponseBookDto;
import ru.otus.homework10.dto.ResponseCommentDto;
import ru.otus.homework10.service.BookService;
import ru.otus.homework10.service.exception.BookNotExistsException;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WithMockUser(
        username = "admin",
        authorities = {"admin"}
)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
@DisplayName("Контроллер книг должен")
public class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private BookService bookService;

    @Test
    @DisplayName("запросить данные книги для добавления")
    void shouldAskBookForInsert() throws Exception {
        mockMvc.perform(get("/books/insert"))
                .andExpect(view().name("insert-book"));
    }

    @DirtiesContext
    @Test
    @DisplayName("добавить книгу")
    void shouldInsertBook() throws Exception {
        final String newBookName = "NewBook";
        final long authorId = 2L;
        final long genreId = 3L;
        final Long newBookId = 3L;

        mockMvc.perform(get("/books/insert?name=" + newBookName + "&authorId=" + authorId + "&genreId=" + genreId))
                .andExpect(view().name("show-book"))
                .andExpect(model().attribute("book", hasProperty("id", is(newBookId))))
                .andExpect(model().attribute("book", hasProperty("name", is(newBookName))));

        ResponseBookDto responseBookDto = bookService.getById(newBookId);

        assertEquals(newBookName, responseBookDto.getName());
    }

    @Test
    @DisplayName("запросить данные книги для обновления")
    void shouldAskBookForUpdate() throws Exception {
        mockMvc.perform(get("/books/update"))
                .andExpect(view().name("update-book"));
    }

    @DirtiesContext
    @Test
    @DisplayName("обновить книгу")
    void shouldUpdateBook() throws Exception {
        final Long bookId = 2L;
        final String updBookName = "UpdBook";
        final long authorId = 2L;
        final long genreId = 3L;

        mockMvc.perform(get("/books/update?id=" + bookId + "&name=" + updBookName + "&authorId=" + authorId + "&genreId=" + genreId))
                .andExpect(view().name("show-book"))
                .andExpect(model().attribute("book", hasProperty("id", is(bookId))))
                .andExpect(model().attribute("book", hasProperty("name", is(updBookName))));

        ResponseBookDto responseBookDto = bookService.getById(bookId);

        assertEquals(updBookName, responseBookDto.getName());
    }

    @Test
    @DisplayName("запросить ид книги для удаления")
    void shouldAskBookIdForDelete() throws Exception {
        mockMvc.perform(get("/books/delete"))
                .andExpect(view().name("delete-book-id"));
    }

    @DirtiesContext
    @Test
    @DisplayName("удалить книгу")
    void shouldDeleteBook() throws Exception {
        final Long bookId = 2L;

        mockMvc.perform(get("/books/delete?id=" + bookId))
                .andExpect(view().name("delete-book"))
                .andExpect(model().attribute("id", is(bookId)));

        assertThrows(BookNotExistsException.class, () -> bookService.getById(bookId));
    }

    @Test
    @DisplayName("запросить ид книги для отображения")
    void shouldAskBookIdForShow() throws Exception {
        mockMvc.perform(get("/books/show"))
                .andExpect(view().name("show-book-id"));
    }

    @Test
    @DisplayName("отобразить книгу по ИД")
    void shouldShowBookById() throws Exception {
        final Long bookId = 1L;
        ResponseBookDto responseBookDto = bookService.getById(bookId);

        mockMvc.perform(get("/books/show?id=" + bookId))
                .andExpect(view().name("show-book"))
                .andExpect(model().attribute("book", hasProperty("id", is(responseBookDto.getId()))))
                .andExpect(model().attribute("book", hasProperty("name", is(responseBookDto.getName()))));
    }

    @Test
    @DisplayName("отобразить все книги")
    void shouldShowAllBooks() throws Exception {
        List<ResponseBookDto> responseBookDtoList = bookService.getAll();

        mockMvc.perform(get("/books/show-all"))
                .andExpect(view().name("show-all-books"))
                .andExpect(model().attribute("books", hasSize(2)))
                .andExpect(model().attribute("books", hasItem(
                        allOf(
                                hasProperty("id", is(responseBookDtoList.get(0).getId())),
                                hasProperty("name", is(responseBookDtoList.get(0).getName()))
                        )
                )))
                .andExpect(model().attribute("books", hasItem(
                        allOf(
                                hasProperty("id", is(responseBookDtoList.get(1).getId())),
                                hasProperty("name", is(responseBookDtoList.get(1).getName()))
                        )
                )));
    }

    @Test
    @DisplayName("запросить ид книги для отображения комментариев")
    void shouldAskBookIdForComments() throws Exception {
        mockMvc.perform(get("/books/show-comments"))
                .andExpect(view().name("show-comments-for-book-id"));
    }

    @Test
    @DisplayName("отобразить все комментарии к книге")
    void shouldShowAllCommentsForBook() throws Exception {
        final Long bookId = 2L;

        List<ResponseCommentDto> responseCommentDtoList = bookService.getCommentsById(bookId);

        mockMvc.perform(get("/books/show-comments?id=" + bookId))
                .andExpect(view().name("show-comments-for-book"))
                .andExpect(model().attribute("comments", hasSize(2)))
                .andExpect(model().attribute("comments", hasItem(
                        allOf(
                                hasProperty("id", is(responseCommentDtoList.get(0).getId())),
                                hasProperty("bookId", is(responseCommentDtoList.get(0).getBookId())),
                                hasProperty("bookName", is(responseCommentDtoList.get(0).getBookName())),
                                hasProperty("text", is(responseCommentDtoList.get(0).getText()))
                        )
                )))
                .andExpect(model().attribute("comments", hasItem(
                        allOf(
                                hasProperty("id", is(responseCommentDtoList.get(1).getId())),
                                hasProperty("bookId", is(responseCommentDtoList.get(1).getBookId())),
                                hasProperty("bookName", is(responseCommentDtoList.get(1).getBookName())),
                                hasProperty("text", is(responseCommentDtoList.get(1).getText()))
                        )
                )));
    }
}
