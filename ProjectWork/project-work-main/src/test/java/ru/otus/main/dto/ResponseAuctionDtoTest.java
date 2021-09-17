package ru.otus.main.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.main.domain.Auction;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Dto аукционного лота должен")
public class ResponseAuctionDtoTest {
    private final static Long BLIZZARD_ID = 1L;
    private final static Long REALM_ID = 2L;
    private final static Long BLIZZARD_ITEM_ID = 3L;
    private final static Long PRICE = 4L;
    private final static Long QUANTITY = 5L;
    private final static LocalDateTime LOCAL_DATE_TIME = LocalDateTime.now();

    @Test
    @DisplayName("корректно создаться конструктором")
    void shouldHaveCorrectConstructor() {
        final ResponseAuctionDto responseAuctionDto = new ResponseAuctionDto(BLIZZARD_ID, REALM_ID, BLIZZARD_ITEM_ID, PRICE, QUANTITY, LOCAL_DATE_TIME);

        assertEquals(BLIZZARD_ID, responseAuctionDto.getId());
        assertEquals(REALM_ID, responseAuctionDto.getRealmId());
        assertEquals(BLIZZARD_ITEM_ID, responseAuctionDto.getItemId());
        assertEquals(PRICE, responseAuctionDto.getItemPrice());
        assertEquals(QUANTITY, responseAuctionDto.getCount());
        assertEquals(LOCAL_DATE_TIME, responseAuctionDto.getLocalDateTime());
    }

    @Test
    @DisplayName("корректно создать доменную сущность")
    void shouldHaveCorrectToDomainMethod() {
        final Auction auction = ResponseAuctionDto.toDomain(new ResponseAuctionDto(BLIZZARD_ID, REALM_ID, BLIZZARD_ITEM_ID, PRICE, QUANTITY, LOCAL_DATE_TIME));

        assertEquals(BLIZZARD_ID, auction.getBlizzardId());
        assertEquals(REALM_ID, auction.getRealmId());
        assertEquals(BLIZZARD_ITEM_ID, auction.getItemBlizzardId());
        assertEquals(PRICE, auction.getPrice());
        assertEquals(QUANTITY, auction.getQuantity());
        assertEquals(LOCAL_DATE_TIME, auction.getLocalDateTime());
    }
}
