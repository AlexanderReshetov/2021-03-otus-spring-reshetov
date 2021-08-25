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
import ru.otus.homework10.dto.ResponseCommentDto;
import ru.otus.homework10.service.CommentService;
import ru.otus.homework10.service.exception.CommentNotExistsException;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WithMockUser(
        username = "admin",
        authorities = {"admin"}
)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
@DisplayName("Контроллер комментариев должен")
public class CommentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CommentService commentService;

    @Test
    @DisplayName("запросить данные комментария для добавления")
    void shouldAskCommentForAdd() throws Exception {
        mockMvc.perform(get("/books/insert"))
                .andExpect(view().name("insert-book"));
    }

    @DirtiesContext
    @Test
    @DisplayName("добавить комментарий")
    void shouldAddComment() throws Exception {
        final long bookId = 2L;
        final String newCommentText = "NewComment";
        final Long newCommentId = 4L;

        mockMvc.perform(get("/comments/add?bookId=" + bookId + "&text=" + newCommentText))
                .andExpect(view().name("show-comment"))
                .andExpect(model().attribute("comment", hasProperty("id", is(newCommentId))))
                .andExpect(model().attribute("comment", hasProperty("bookId", is(bookId))))
                .andExpect(model().attribute("comment", hasProperty("text", is(newCommentText))));

        ResponseCommentDto responseCommentDto = commentService.getById(newCommentId);

        assertEquals(bookId, responseCommentDto.getBookId());
        assertEquals(newCommentText, responseCommentDto.getText());
    }

    @Test
    @DisplayName("запросить данные комментария для редактирования")
    void shouldAskCommentForEdit() throws Exception {
        mockMvc.perform(get("/comments/edit"))
                .andExpect(view().name("edit-comment"));
    }

    @DirtiesContext
    @Test
    @DisplayName("обновить комментарий")
    void shouldEditComment() throws Exception {
        final Long updCommentId = 3L;
        final long bookId = 2L;
        final String updCommentText = "UpdComment";

        mockMvc.perform(get("/comments/edit?id=" + updCommentId + "&bookId=" + bookId + "&text=" + updCommentText))
                .andExpect(view().name("show-comment"))
                .andExpect(model().attribute("comment", hasProperty("id", is(updCommentId))))
                .andExpect(model().attribute("comment", hasProperty("bookId", is(bookId))))
                .andExpect(model().attribute("comment", hasProperty("text", is(updCommentText))));

        ResponseCommentDto responseCommentDto = commentService.getById(updCommentId);

        assertEquals(bookId, responseCommentDto.getBookId());
        assertEquals(updCommentText, responseCommentDto.getText());
    }

    @Test
    @DisplayName("запросить ид комментария для удаления")
    void shouldAsCommentIdForRemove() throws Exception {
        mockMvc.perform(get("/comments/remove"))
                .andExpect(view().name("remove-comment-id"));
    }

    @DirtiesContext
    @Test
    @DisplayName("удалить комментарий")
    void shouldRemoveComment() throws Exception {
        final Long commentId = 2L;

        mockMvc.perform(get("/comments/remove?id=" + commentId))
                .andExpect(view().name("remove-comment"))
                .andExpect(model().attribute("id", is(commentId)));

        assertThrows(CommentNotExistsException.class, () -> commentService.getById(commentId));
    }

    @Test
    @DisplayName("запросить ид комментария для отображения")
    void shouldAskCommentIdForShow() throws Exception {
        mockMvc.perform(get("/comments/show"))
                .andExpect(view().name("show-comment-id"));
    }

    @Test
    @DisplayName("отобразить комментарий по ИД")
    void shouldShowCommentById() throws Exception {
        final Long commentId = 1L;
        ResponseCommentDto responseCommentDto = commentService.getById(commentId);

        mockMvc.perform(get("/comments/show?id=" + commentId))
                .andExpect(view().name("show-comment"))
                .andExpect(model().attribute("comment", hasProperty("id", is(responseCommentDto.getId()))))
                .andExpect(model().attribute("comment", hasProperty("bookId", is(responseCommentDto.getBookId()))))
                .andExpect(model().attribute("comment", hasProperty("bookName", is(responseCommentDto.getBookName()))))
                .andExpect(model().attribute("comment", hasProperty("text", is(responseCommentDto.getText()))));
    }

    @Test
    @DisplayName("отобразить все комментарии")
    void shouldShowAllComments() throws Exception {
        List<ResponseCommentDto> responseCommentDtoList = commentService.getAll();

        mockMvc.perform(get("/comments/show-all"))
                .andExpect(view().name("show-all-comments"))
                .andExpect(model().attribute("comments", hasSize(3)))
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
                )))
                .andExpect(model().attribute("comments", hasItem(
                        allOf(
                                hasProperty("id", is(responseCommentDtoList.get(2).getId())),
                                hasProperty("bookId", is(responseCommentDtoList.get(2).getBookId())),
                                hasProperty("bookName", is(responseCommentDtoList.get(2).getBookName())),
                                hasProperty("text", is(responseCommentDtoList.get(2).getText()))
                        )
                )));
    }
}
