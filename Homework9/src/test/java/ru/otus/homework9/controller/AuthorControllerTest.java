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
import ru.otus.homework9.dto.ResponseAuthorDto;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
@DisplayName("Контроллер авторов должен")
public class AuthorControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("вернуть автора по ИД")
    void shouldReturnAuthorById() throws Exception {
        final ResponseAuthorDto responseAuthorDto = new ResponseAuthorDto(1L, "TestAuthor1");

        mockMvc.perform(get("/authors/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(responseAuthorDto)));
    }

    @Test
    @DisplayName("вернуть список авторов")
    void shouldReturnAuthorList() throws Exception {
        final List<ResponseAuthorDto> responseAuthorDtoList = Arrays.asList(
                new ResponseAuthorDto(1L, "TestAuthor1"),
                new ResponseAuthorDto(2L, "TestAuthor2"));

        mockMvc.perform(get("/authors/"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(responseAuthorDtoList)));
    }
}
