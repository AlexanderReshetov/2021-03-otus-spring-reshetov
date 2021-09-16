package ru.otus.main.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.otus.main.domain.Auction;
import ru.otus.main.service.exception.AuctionNotExistsException;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@DisplayName("Dao аукционных лотов должен")
public class AuctionRepositoryTest {
    @Autowired
    private AuctionRepository auctionRepository;

    @Test
    @DisplayName("добавить лот и получить его по ИД")
    void shouldAddAuctionAndGetHimById() {
        final Long blizzardId = 1L;
        final Long realmId = 2L;
        final Long itemBlizzardId = 3L;
        final Long price = 4L;
        final Long quantity = 5L;
        final LocalDateTime localDateTime = LocalDateTime.now();
        Auction auction = new Auction(null, blizzardId, realmId, itemBlizzardId, price, quantity, localDateTime);
        auction = auctionRepository.save(auction);
        final Long newId = auction.getId();
        auction = auctionRepository.findById(auction.getId()).orElseThrow(() -> new AuctionNotExistsException("There is no auction with id = " + newId));

        assertEquals(blizzardId, auction.getBlizzardId());
        assertEquals(realmId, auction.getRealmId());
        assertEquals(itemBlizzardId, auction.getItemBlizzardId());
        assertEquals(price, auction.getPrice());
        assertEquals(quantity, auction.getQuantity());
        assertEquals(localDateTime, auction.getLocalDateTime());
    }
}