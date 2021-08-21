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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@WithMockUser(
        username = "admin",
        authorities = {"admin"}
)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
@DisplayName("Контроллер главной страницы должен")
public class MainControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("отобразить главную страницу по базовому адресу")
    void shouldShowMainPageByBaseAddress() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(view().name("index"));
    }

    @Test
    @DisplayName("отобразить главную страницу по адресу index")
    void shouldShowMainPageBIndexAddress() throws Exception {
        mockMvc.perform(get("/index"))
                .andExpect(view().name("index"));
    }

    @Test
    @DisplayName("отобразить главную страницу после аутентификации")
    void shouldShowMainPageAfterAuthentication() throws Exception {
        mockMvc.perform(post("/"))
                .andExpect(view().name("index"));
    }
}
