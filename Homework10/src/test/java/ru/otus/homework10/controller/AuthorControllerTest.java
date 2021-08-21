package ru.otus.homework10.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.homework10.dto.ResponseAuthorDto;
import ru.otus.homework10.service.AuthorService;

import java.util.List;

import static org.hamcrest.Matchers.*;
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
@DisplayName("Контроллер авторов должен")
public class AuthorControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AuthorService authorService;

    @Test
    @DisplayName("запросить ид автора")
    void shouldAskAuthorId() throws Exception {
        mockMvc.perform(get("/authors/show"))
                .andExpect(view().name("show-author-id"));
    }

    @Test
    @DisplayName("отобразить автора по ИД")
    void shouldShowAuthorById() throws Exception {
        final Long authorId = 1L;
        ResponseAuthorDto responseAuthorDto = authorService.getById(authorId);

        mockMvc.perform(get("/authors/show?id=" + authorId))
                .andExpect(view().name("show-author"))
                .andExpect(model().attribute("author", hasProperty("id", is(responseAuthorDto.getId()))))
                .andExpect(model().attribute("author", hasProperty("name", is(responseAuthorDto.getName()))));
    }

    @Test
    @DisplayName("отобразить всех авторов")
    void shouldShowAllAuthors() throws Exception {
        List<ResponseAuthorDto> responseAuthorDtoList = authorService.getAll();

        mockMvc.perform(get("/authors/show-all"))
                .andExpect(view().name("show-all-authors"))
                .andExpect(model().attribute("authors", hasSize(2)))
                .andExpect(model().attribute("authors", hasItem(
                        allOf(
                                hasProperty("id", is(responseAuthorDtoList.get(0).getId())),
                                hasProperty("name", is(responseAuthorDtoList.get(0).getName()))
                        )
                )))
                .andExpect(model().attribute("authors", hasItem(
                        allOf(
                                hasProperty("id", is(responseAuthorDtoList.get(1).getId())),
                                hasProperty("name", is(responseAuthorDtoList.get(1).getName()))
                        )
                )));
    }
}
