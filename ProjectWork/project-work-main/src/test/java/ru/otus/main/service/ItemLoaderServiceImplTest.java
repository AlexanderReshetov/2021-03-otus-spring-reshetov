package ru.otus.main.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.main.domain.AuctionLot;
import ru.otus.main.domain.Item;
import ru.otus.main.domain.Token;
import ru.otus.main.dto.ItemAndTokenDto;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис загрузки предметов должен")
public class ItemLoaderServiceImplTest {
    @Mock
    private ItemService itemService;
    @InjectMocks
    private ItemLoaderServiceImpl itemLoaderService;

    @Test
    @DisplayName("запросить предметы у загрузчика")
    void shouldLoadItems() {
        when(itemService.loadItemWithTokenById(3L)).thenReturn(new ItemAndTokenDto(
                new Item(1L, 2L, "item", "предмет"),
                new Token(1L, "token", LocalDateTime.now())));
        itemLoaderService.loadItemsByAuctions(Collections.singletonList(new AuctionLot(null, 1L, 2L, 3L, 4L, 5L, LocalDateTime.now())));

        verify(itemService).loadItemWithTokenById(3L);
    }

}