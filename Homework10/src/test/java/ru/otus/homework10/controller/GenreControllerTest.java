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
import ru.otus.homework10.dto.ResponseGenreDto;
import ru.otus.homework10.service.GenreService;

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
@DisplayName("Контроллер жанров должен")
public class GenreControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private GenreService genreService;

    @Test
    @DisplayName("запросить ид жанра")
    void shouldAskGenreId() throws Exception {
        mockMvc.perform(get("/genres/show"))
                .andExpect(view().name("show-genre-id"));
    }

    @Test
    @DisplayName("отобразить жанр по ИД")
    void shouldShowGenreById() throws Exception {
        final Long genreId = 2L;
        ResponseGenreDto responseGenreDto = genreService.getById(genreId);

        mockMvc.perform(get("/genres/show?id=" + genreId))
                .andExpect(view().name("show-genre"))
                .andExpect(model().attribute("genre", hasProperty("id", is(responseGenreDto.getId()))))
                .andExpect(model().attribute("genre", hasProperty("name", is(responseGenreDto.getName()))));
    }

    @Test
    @DisplayName("отобразить все жанры")
    void shouldShowAllGenres() throws Exception {
        List<ResponseGenreDto> responseGenreDtoList = genreService.getAll();

        mockMvc.perform(get("/genres/show-all"))
                .andExpect(view().name("show-all-genres"))
                .andExpect(model().attribute("genres", hasSize(3)))
                .andExpect(model().attribute("genres", hasItem(
                        allOf(
                                hasProperty("id", is(responseGenreDtoList.get(0).getId())),
                                hasProperty("name", is(responseGenreDtoList.get(0).getName()))
                        )
                )))
                .andExpect(model().attribute("genres", hasItem(
                        allOf(
                                hasProperty("id", is(responseGenreDtoList.get(1).getId())),
                                hasProperty("name", is(responseGenreDtoList.get(1).getName()))
                        )
                )))
                .andExpect(model().attribute("genres", hasItem(
                        allOf(
                                hasProperty("id", is(responseGenreDtoList.get(2).getId())),
                                hasProperty("name", is(responseGenreDtoList.get(2).getName()))
                        )
                )));
    }
}
