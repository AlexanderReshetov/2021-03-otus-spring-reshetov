package ru.otus.main.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.otus.main.domain.Item;
import ru.otus.main.service.exception.ItemNotExistsException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@DisplayName("Dao предметов должен")
public class ItemRepositoryTest {
    @Autowired
    private ItemRepository itemRepository;

    @Test
    @DisplayName("добавить предмет и получить его по ИД")
    void shouldAddItemAndGetHimById() {
        final Long blizzardId = 1L;
        final String enName = "item";
        final String ruName = "предмет";
        Item item = new Item(null, blizzardId, enName, ruName);
        item = itemRepository.save(item);
        final Long newId = item.getId();
        item = itemRepository.findById(item.getId()).orElseThrow(() -> new ItemNotExistsException("There is no item with id = " + newId));

        assertEquals(blizzardId, item.getBlizzardId());
        assertEquals(enName, item.getEnName());
        assertEquals(ruName, item.getRuName());
    }
}