package ru.otus.main.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestOperations;
import ru.otus.main.domain.Auction;
import ru.otus.main.domain.Token;
import ru.otus.main.dto.ResponseAuctionDto;
import ru.otus.main.repository.AuctionRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис получения данных аукционов должен")
public class AuctionServiceImplTest {
    @Mock
    private TokenService tokenService;
    @Mock
    private RestOperations restOperations;
    @Mock
    private AuctionRepository auctionRepository;
    @Mock
    private ItemLoaderService itemLoaderService;

    private final static Long BLIZZARD_ID = 1L;
    private final static Long REALM_ID = 2L;
    private final static Long BLIZZARD_ITEM_ID = 3L;
    private final static Long PRICE = 4L;
    private final static Long QUANTITY = 5L;
    private static LocalDateTime LOCAL_DATE_TIME = LocalDateTime.now();

    @Test
    @DisplayName("запросить данные из загрузчика")
    void shouldGetDataFromLoader() {
        final AuctionService auctionService = auctionService();
        when(tokenService.getToken()).thenReturn(new Token(null, "token", LOCAL_DATE_TIME));
        when(restOperations.exchange(any(), Matchers.<ParameterizedTypeReference<List<ResponseAuctionDto>>>any()))
                .thenReturn(ResponseEntity.ok(Collections.singletonList(new ResponseAuctionDto(BLIZZARD_ID, REALM_ID, BLIZZARD_ITEM_ID, PRICE, QUANTITY, LOCAL_DATE_TIME))));

        List<Auction> auctionList = auctionService.loadAllAuctionsByRealmId(1L);

        verify(tokenService).getToken();
        verify(restOperations).exchange(any(), Matchers.<ParameterizedTypeReference<List<ResponseAuctionDto>>>any());
        assertEquals(1, auctionList.size());
        assertEquals(BLIZZARD_ID, auctionList.get(0).getBlizzardId());
        assertEquals(REALM_ID, auctionList.get(0).getRealmId());
        assertEquals(BLIZZARD_ITEM_ID, auctionList.get(0).getItemBlizzardId());
        assertEquals(PRICE, auctionList.get(0).getPrice());
        assertEquals(QUANTITY, auctionList.get(0).getQuantity());
        assertEquals(LOCAL_DATE_TIME, auctionList.get(0).getLocalDateTime());
    }

    @Test
    @DisplayName("сохранить сообщение в базу")
    void shouldSaveMessage() {
        final AuctionService auctionService = auctionService();

        auctionService.save(new ResponseAuctionDto(BLIZZARD_ID, REALM_ID, BLIZZARD_ITEM_ID, PRICE, QUANTITY, LOCAL_DATE_TIME));

        verify(auctionRepository).saveAll(any());
    }

    private AuctionService auctionService() {
        return new AuctionServiceImpl(
                tokenService,
                restOperations,
                "host",
                "port",
                auctionRepository,
                itemLoaderService);
    }
}
