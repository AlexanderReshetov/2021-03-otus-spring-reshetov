package ru.otus.main.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import ru.otus.main.domain.AuctionLot;
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
public class AuctionLotServiceImplTest {
    @Mock
    private TokenService tokenService;
    @Mock
    private RequestEntityService requestEntityService;
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
        when(requestEntityService.getResponseAuctionDtoList("token", REALM_ID))
                .thenReturn(ResponseEntity.ok(Collections.singletonList(new ResponseAuctionDto(BLIZZARD_ID, REALM_ID, BLIZZARD_ITEM_ID, PRICE, QUANTITY, LOCAL_DATE_TIME))));

        List<AuctionLot> auctionLotList = auctionService.loadAllAuctionsByRealmId(REALM_ID);

        verify(tokenService).getToken();
        assertEquals(1, auctionLotList.size());
        assertEquals(BLIZZARD_ID, auctionLotList.get(0).getBlizzardId());
        assertEquals(REALM_ID, auctionLotList.get(0).getRealmId());
        assertEquals(BLIZZARD_ITEM_ID, auctionLotList.get(0).getItemBlizzardId());
        assertEquals(PRICE, auctionLotList.get(0).getPrice());
        assertEquals(QUANTITY, auctionLotList.get(0).getQuantity());
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
                auctionRepository,
                itemLoaderService,
                requestEntityService);
    }
}
