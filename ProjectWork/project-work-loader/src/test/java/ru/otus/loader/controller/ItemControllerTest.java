package ru.otus.loader.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import ru.otus.loader.dto.BlizzardItemDto;
import ru.otus.loader.dto.BlizzardNameDto;
import ru.otus.loader.dto.ResponseItemDto;
import ru.otus.loader.service.LoadItemService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Контроллер предметов должен")
public class ItemControllerTest {
    @Mock
    private LoadItemService loadItemService;
    @InjectMocks
    private ItemController itemController;

    @Test
    @DisplayName("запросить данные предмета и вернуть их")
    void shouldAskItemAndReturnData() {
        when(loadItemService.getItemById("token", 1L))
                .thenReturn(new BlizzardItemDto(1L, new BlizzardNameDto("item", "предмет")));

        ResponseEntity<ResponseItemDto> responseEntity = itemController.getItemById("token",1L);

        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        assertEquals(1L, responseEntity.getBody().getId());
        assertEquals("item", responseEntity.getBody().getEnName());
        assertEquals("предмет", responseEntity.getBody().getRuName());
    }
}