package ru.otus.main.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.main.repository.ItemRepository;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис получения данных предметов для отображения должен")
public class ItemForViewServiceImplTest {
    @Mock
    private ItemRepository itemRepository;
    @InjectMocks
    private ItemForViewServiceImpl itemForViewService;

    @Test
    @DisplayName("получить предмет из репозитория по русскому наименованию")
    void shouldGetItemByRuName() {
        itemForViewService.getByRuName("предмет");

        verify(itemRepository).findAllByRuName("предмет");
    }

    @Test
    @DisplayName("получить предмет из репозитория по английскому наименованию")
    void shouldGetItemByEnName() {
        itemForViewService.getByEnName("item");

        verify(itemRepository).findAllByEnName("item");
    }
}
