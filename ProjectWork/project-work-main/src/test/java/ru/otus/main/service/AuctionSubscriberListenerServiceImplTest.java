package ru.otus.main.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.main.dto.ResponseAuctionDto;

import java.time.LocalDateTime;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис обработки сообщений кафки с лотами аукционов должен")
public class AuctionSubscriberListenerServiceImplTest {
    @Mock
    private AuctionService auctionService;
    @InjectMocks
    private AuctionSubscriberListenerServiceImpl auctionSubscriberListenerService;

    private final static Long BLIZZARD_ID = 1L;
    private final static Long REALM_ID = 2L;
    private final static Long BLIZZARD_ITEM_ID = 3L;
    private final static Long PRICE = 4L;
    private final static Long QUANTITY = 5L;
    private final static LocalDateTime LOCAL_DATE_TIME = LocalDateTime.now();

    @Test
    @DisplayName("сохранить полученное сообщение в базу")
    void shouldSaveMessage() {
        ResponseAuctionDto responseAuctionDto = new ResponseAuctionDto(BLIZZARD_ID, REALM_ID, BLIZZARD_ITEM_ID, PRICE, QUANTITY, LOCAL_DATE_TIME);
        auctionSubscriberListenerService.receiveAuction(responseAuctionDto);

        verify(auctionService).save(responseAuctionDto);
    }
}