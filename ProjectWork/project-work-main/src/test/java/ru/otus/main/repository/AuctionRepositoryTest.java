package ru.otus.main.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.otus.main.domain.Auction;
import ru.otus.main.dto.TrendItemDto;
import ru.otus.main.service.exception.AuctionNotExistsException;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@DisplayName("Dao аукционных лотов должен")
public class AuctionRepositoryTest {
    @Autowired
    private AuctionRepository auctionRepository;

    private final static Long BLIZZARD_ID = 1L;
    private final static Long REALM_ID = 2L;
    private final static Long BLIZZARD_ITEM_ID = 3L;
    private final static Long PRICE = 4L;
    private final static Long QUANTITY = 5L;
    private final static LocalDateTime LOCAL_DATE_TIME = LocalDateTime.now();

    @Test
    @DisplayName("добавить лот и получить его по ИД")
    void shouldAddAuctionAndGetHimById() {
        Auction auction = auction();

        auction = auctionRepository.save(auction);
        final Long newId = auction.getId();
        auction = auctionRepository.findById(auction.getId()).orElseThrow(() -> new AuctionNotExistsException("There is no auction with id = " + newId));

        assertEquals(BLIZZARD_ID, auction.getBlizzardId());
        assertEquals(REALM_ID, auction.getRealmId());
        assertEquals(BLIZZARD_ITEM_ID, auction.getItemBlizzardId());
        assertEquals(PRICE, auction.getPrice());
        assertEquals(QUANTITY, auction.getQuantity());
        assertEquals(LOCAL_DATE_TIME, auction.getLocalDateTime());
    }

    @Test
    @DisplayName("отобрать лоты с отсутствующими в базе предметами")
    void shouldGetLotsWithNonExistingItems() {
        Auction auction = auction();

        auction = auctionRepository.save(auction);
        List<Auction> auctionList = auctionRepository.findAllByLocalDateTimeWhereItemIsNotExists(LOCAL_DATE_TIME);

        assertEquals(1, auctionList.size());
        assertEquals(auction.getId(), auctionList.get(0).getId());
        assertEquals(auction.getBlizzardId(), auctionList.get(0).getBlizzardId());
        assertEquals(auction.getItemBlizzardId(), auctionList.get(0).getItemBlizzardId());
        assertEquals(auction.getPrice(), auctionList.get(0).getPrice());
        assertEquals(auction.getQuantity(), auctionList.get(0).getQuantity());
        assertEquals(auction.getLocalDateTime(), auctionList.get(0).getLocalDateTime());
    }

    @Test
    @DisplayName("получить тренд по Blizzard ID предмета")
    void shouldGetTrendByItemBlizzardId() {
        Auction auction = auction();

        auctionRepository.save(auction);
        List<TrendItemDto> trendItemDtoList = auctionRepository.findTrendByBlizzardItemId(REALM_ID, BLIZZARD_ITEM_ID);

        assertEquals(1, trendItemDtoList.size());
        assertEquals(PRICE, trendItemDtoList.get(0).getPrice());
        assertEquals(LOCAL_DATE_TIME, trendItemDtoList.get(0).getLocalDateTime());
    }

    @Test
    @DisplayName("получить тренд по ID предмета")
    void shouldGetTrendByItemId() {
        Auction auction = auction();

        auctionRepository.save(auction);
        List<TrendItemDto> trendItemDtoList = auctionRepository.findTrendByItemId(REALM_ID, null);

        assertEquals(0, trendItemDtoList.size());
    }

    private Auction auction() {
        return new Auction(null, BLIZZARD_ID, REALM_ID, BLIZZARD_ITEM_ID, PRICE, QUANTITY, LOCAL_DATE_TIME);
    }
}