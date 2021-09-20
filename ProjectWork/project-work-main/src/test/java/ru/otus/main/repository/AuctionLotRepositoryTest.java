package ru.otus.main.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.otus.main.domain.AuctionLot;
import ru.otus.main.dto.TrendItemDto;
import ru.otus.main.service.exception.AuctionNotExistsException;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@DisplayName("Dao аукционных лотов должен")
public class AuctionLotRepositoryTest {
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
        AuctionLot auctionLot = auction();

        auctionLot = auctionRepository.save(auctionLot);
        final Long newId = auctionLot.getId();
        auctionLot = auctionRepository.findById(auctionLot.getId()).orElseThrow(() -> new AuctionNotExistsException("There is no auctionLot with id = " + newId));

        assertEquals(BLIZZARD_ID, auctionLot.getBlizzardId());
        assertEquals(REALM_ID, auctionLot.getRealmId());
        assertEquals(BLIZZARD_ITEM_ID, auctionLot.getItemBlizzardId());
        assertEquals(PRICE, auctionLot.getPrice());
        assertEquals(QUANTITY, auctionLot.getQuantity());
    }

    @Test
    @DisplayName("отобрать лоты с отсутствующими в базе предметами")
    void shouldGetLotsWithNonExistingItems() {
        AuctionLot auctionLot = auction();

        auctionLot = auctionRepository.save(auctionLot);
        List<AuctionLot> auctionLotList = auctionRepository.findAllByLocalDateTimeWhereItemIsNotExists(LOCAL_DATE_TIME);

        assertEquals(1, auctionLotList.size());
        assertEquals(auctionLot.getId(), auctionLotList.get(0).getId());
        assertEquals(auctionLot.getBlizzardId(), auctionLotList.get(0).getBlizzardId());
        assertEquals(auctionLot.getItemBlizzardId(), auctionLotList.get(0).getItemBlizzardId());
        assertEquals(auctionLot.getPrice(), auctionLotList.get(0).getPrice());
        assertEquals(auctionLot.getQuantity(), auctionLotList.get(0).getQuantity());
        assertEquals(auctionLot.getLocalDateTime(), auctionLotList.get(0).getLocalDateTime());
    }

    @Test
    @DisplayName("получить тренд по Blizzard ID предмета")
    void shouldGetTrendByItemBlizzardId() {
        AuctionLot auctionLot = auction();

        auctionRepository.save(auctionLot);
        List<TrendItemDto> trendItemDtoList = auctionRepository.findTrendByBlizzardItemId(REALM_ID, BLIZZARD_ITEM_ID);

        assertEquals(1, trendItemDtoList.size());
        assertEquals(PRICE, trendItemDtoList.get(0).getPrice());
    }

    @Test
    @DisplayName("получить тренд по ID предмета")
    void shouldGetTrendByItemId() {
        AuctionLot auctionLot = auction();

        auctionRepository.save(auctionLot);
        List<TrendItemDto> trendItemDtoList = auctionRepository.findTrendByItemId(REALM_ID, null);

        assertEquals(0, trendItemDtoList.size());
    }

    private AuctionLot auction() {
        return new AuctionLot(null, BLIZZARD_ID, REALM_ID, BLIZZARD_ITEM_ID, PRICE, QUANTITY, LOCAL_DATE_TIME);
    }
}