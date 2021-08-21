package ru.otus.homework9.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.homework9.dto.ResponseGenreDto;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
@DisplayName("Контроллер жанров должен")
public class GenreControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("вернуть жанр по ИД")
    void shouldReturnGenreById() throws Exception {
        final ResponseGenreDto responseGenreDto = new ResponseGenreDto(1L, "TestGenre1");

        mockMvc.perform(get("/genres/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(responseGenreDto)));
    }

    @Test
    @DisplayName("вернуть список жанров")
    void shouldReturnGenreList() throws Exception {
        final List<ResponseGenreDto> responseGenreDtoList = Arrays.asList(
                new ResponseGenreDto(1L, "TestGenre1"),
                new ResponseGenreDto(2L, "TestGenre2"),
                new ResponseGenreDto(3L, "TestGenre3"));

        mockMvc.perform(get("/genres/"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(responseGenreDtoList)));
    }
}
