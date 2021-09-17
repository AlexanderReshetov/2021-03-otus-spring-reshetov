package ru.otus.main.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestOperations;
import ru.otus.main.domain.Item;
import ru.otus.main.domain.Token;
import ru.otus.main.dto.ItemAndTokenDto;
import ru.otus.main.dto.ResponseItemDto;
import ru.otus.main.repository.ItemRepository;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис получения предметов должен")
public class ItemServiceImplTest {
    @Mock
    private TokenService tokenService;
    @Mock
    private RestOperations restOperations;
    @Mock
    private ItemRepository itemRepository;

    private final static Long BLIZZARD_ITEM_ID = 1L;
    private final static String EN_NAME = "item";
    private final static String RU_NAME = "предмет";
    private final static LocalDateTime LOCAL_DATE_TIME = LocalDateTime.now();
    private final static String TOKEN = "token";

    @Test
    @DisplayName("запросить предмет из загрузчика по Blizzard ИД")
    void shouldGetItemFromLoaderByBlizzardId() {
        final ItemService itemService = itemService();
        when(tokenService.getToken()).thenReturn(new Token(null, TOKEN, LOCAL_DATE_TIME));
        when(restOperations.exchange(any(), eq(ResponseItemDto.class)))
                .thenReturn(ResponseEntity.ok(new ResponseItemDto(BLIZZARD_ITEM_ID, EN_NAME, RU_NAME)));

        Item item = itemService.loadItemById(BLIZZARD_ITEM_ID);

        verify(tokenService).getToken();
        verify(restOperations).exchange(any(), eq(ResponseItemDto.class));
        assertEquals(BLIZZARD_ITEM_ID, item.getBlizzardId());
        assertEquals(EN_NAME, item.getEnName());
        assertEquals(RU_NAME, item.getRuName());
    }

    @Test
    @DisplayName("запросить предмет из загрузчика по Blizzard ИД и токену")
    void shouldGetItemFromLoaderByBlizzardIdAndToken() {
        final ItemService itemService = itemService();
        when(restOperations.exchange(any(), eq(ResponseItemDto.class)))
                .thenReturn(ResponseEntity.ok(new ResponseItemDto(BLIZZARD_ITEM_ID, EN_NAME, RU_NAME)));

        Item item = itemService.loadItemById(TOKEN, BLIZZARD_ITEM_ID);

        verify(restOperations).exchange(any(), eq(ResponseItemDto.class));
        assertEquals(BLIZZARD_ITEM_ID, item.getBlizzardId());
        assertEquals(EN_NAME, item.getEnName());
        assertEquals(RU_NAME, item.getRuName());
    }

    @Test
    @DisplayName("запросить предмет и токен из загрузчика по Blizzard ИД")
    void shouldGetItemAndTokenFromLoaderByBlizzardId() {
        final ItemService itemService = itemService();
        when(tokenService.getToken()).thenReturn(new Token(null, TOKEN, LOCAL_DATE_TIME));
        when(restOperations.exchange(any(), eq(ResponseItemDto.class)))
                .thenReturn(ResponseEntity.ok(new ResponseItemDto(BLIZZARD_ITEM_ID, EN_NAME, RU_NAME)));

        ItemAndTokenDto itemAndTokenDto = itemService.loadItemWithTokenById(BLIZZARD_ITEM_ID);

        verify(tokenService).getToken();
        verify(restOperations).exchange(any(), eq(ResponseItemDto.class));
        assertEquals(TOKEN, itemAndTokenDto.getToken().getToken());
        assertEquals(LOCAL_DATE_TIME, itemAndTokenDto.getToken().getExpirationDt());
        assertEquals(BLIZZARD_ITEM_ID, itemAndTokenDto.getItem().getBlizzardId());
        assertEquals(EN_NAME, itemAndTokenDto.getItem().getEnName());
        assertEquals(RU_NAME, itemAndTokenDto.getItem().getRuName());
    }

    private ItemService itemService() {
        return new ItemServiceImpl(
                tokenService,
                restOperations,
                "host",
                "port",
                itemRepository);
    }
}
