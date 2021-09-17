package ru.otus.main.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.otus.main.domain.Item;
import ru.otus.main.service.exception.ItemNotExistsException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@DisplayName("Dao предметов должен")
public class ItemRepositoryTest {
    @Autowired
    private ItemRepository itemRepository;

    private static Long BLIZZARD_ID = 1L;
    private static String EN_NAME = "item";
    private static String RU_NAME = "предмет";
    private static String EN_NAME_FOR_SEARCH = "te";
    private static String RU_NAME_FOR_SEARCH = "предмет";

    @Test
    @DisplayName("добавить предмет и получить его по ИД")
    void shouldAddItemAndGetHimById() {
        Item item = item();

        item = itemRepository.save(item);
        final Long newId = item.getId();
        item = itemRepository.findById(item.getId()).orElseThrow(() -> new ItemNotExistsException("There is no item with id = " + newId));

        assertEquals(BLIZZARD_ID, item.getBlizzardId());
        assertEquals(EN_NAME, item.getEnName());
        assertEquals(RU_NAME, item.getRuName());
    }

    @Test
    @DisplayName("найти предмет по английскому наименованию")
    void shouldFindItemByEnName() {
        Item item = item();

        itemRepository.save(item);
        final List<Item> itemList = itemRepository.findAllByEnName(EN_NAME_FOR_SEARCH);

        assertEquals(1, itemList.size());
        assertEquals(item.getId(), itemList.get(0).getId());
        assertEquals(item.getEnName(), itemList.get(0).getEnName());
        assertEquals(item.getRuName(), itemList.get(0).getRuName());
    }

    @Test
    @DisplayName("найти предмет по русскому наименованию")
    void shouldFindItemByRuName() {
        Item item = item();

        itemRepository.save(item);
        final List<Item> itemList = itemRepository.findAllByRuName(RU_NAME_FOR_SEARCH);

        assertEquals(1, itemList.size());
        assertEquals(item.getId(), itemList.get(0).getId());
        assertEquals(item.getEnName(), itemList.get(0).getEnName());
        assertEquals(item.getRuName(), itemList.get(0).getRuName());
    }

    private Item item() {
        return new Item(null, BLIZZARD_ID, EN_NAME, RU_NAME);
    }
}