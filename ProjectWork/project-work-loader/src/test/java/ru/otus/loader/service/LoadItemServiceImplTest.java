package ru.otus.loader.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestOperations;
import ru.otus.loader.dto.*;
import ru.otus.loader.service.exception.ItemException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис загрузки данных предмета должен")
public class LoadItemServiceImplTest {
    @Mock
    private RestOperations restOperations;

    @Test
    @DisplayName("вернуть данные предмета по идентификатору")
    void shouldReturnItemById() {
        when(restOperations.exchange(any(), eq(BlizzardItemDto.class)))
                .thenReturn(ResponseEntity.ok(new BlizzardItemDto(1L, new BlizzardNameDto("item", "предмет"))));
        final LoadItemService loadItemService = loadItemService();

        final BlizzardItemDto blizzardItemDto = loadItemService.getItemById("token", 1L);

        assertEquals(1L, blizzardItemDto.getId());
        assertEquals("item", blizzardItemDto.getBlizzardNameDto().getEn_US());
        assertEquals("предмет", blizzardItemDto.getBlizzardNameDto().getRu_RU());
    }

    @Test
    @DisplayName("вызвать исключение, если данные предмета не получены")
    void shouldThrowExceptionIfCannotLoadItem() {
        when(restOperations.exchange(any(), eq(BlizzardItemDto.class)))
                .thenReturn(ResponseEntity.badRequest().body(null));
        final LoadItemService loadItemService = loadItemService();

        assertThrows(ItemException.class, () -> loadItemService.getItemById("token", 1L));
    }

    private LoadItemService loadItemService() {
        return new LoadItemServiceImpl(restOperations, "");
    }
}