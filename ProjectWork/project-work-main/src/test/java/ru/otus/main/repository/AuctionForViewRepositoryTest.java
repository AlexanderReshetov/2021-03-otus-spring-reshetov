package ru.otus.main.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.otus.main.domain.AuctionForView;
import ru.otus.main.domain.Item;
import ru.otus.main.service.exception.AuctionNotExistsException;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@DisplayName("Dao аукционных лотов для отображения должен")
public class AuctionForViewRepositoryTest {
    @Autowired
    private AuctionForViewRepository auctionForViewRepository;

    @Test
    @DisplayName("добавить лот и получить его по ИД")
    void shouldAddAuctionAndGetHimById() {
        final Long blizzardId = 1L;
        final Long realmId = 2L;
        final Long price = 4L;
        final Long quantity = 5L;
        final LocalDateTime localDateTime = LocalDateTime.now();
        final Item item = new Item(6L, 7L, "item", "предмет");
        AuctionForView auctionForView = new AuctionForView(null, blizzardId, realmId, item, price, quantity, localDateTime);

        auctionForView = auctionForViewRepository.save(auctionForView);
        final Long newId = auctionForView.getId();
        auctionForView = auctionForViewRepository.findById(auctionForView.getId()).orElseThrow(() -> new AuctionNotExistsException("There is no auction with id = " + newId));

        assertEquals(blizzardId, auctionForView.getBlizzardId());
        assertEquals(realmId, auctionForView.getRealmId());
        assertEquals(item.getId(), auctionForView.getItem().getId());
        assertEquals(item.getBlizzardId(), auctionForView.getItem().getBlizzardId());
        assertEquals(item.getEnName(), auctionForView.getItem().getEnName());
        assertEquals(item.getRuName(), auctionForView.getItem().getRuName());
        assertEquals(price, auctionForView.getPrice());
        assertEquals(quantity, auctionForView.getQuantity());
        assertEquals(localDateTime, auctionForView.getLocalDateTime());
    }
}