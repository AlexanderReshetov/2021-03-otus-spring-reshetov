package ru.otus.main.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.main.repository.AuctionRepository;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис получения трендов должен")
public class TrendServiceImplTest {
    @Mock
    private AuctionRepository auctionRepository;
    @InjectMocks
    private TrendServiceImpl trendService;

    @Test
    @DisplayName("получить тренд предмета по ИД Blizzard")
    void shouldGetTrendByItemBlizzardId() {
        trendService.getTrendByItemBlizzardId(1L, 2L);

        verify(auctionRepository).findTrendByBlizzardItemId(1L, 2L);
    }

    @Test
    @DisplayName("получить тренд предмета по внутреннему ИД")
    void shouldGetTrendByItemId() {
        trendService.getTrendByItemId(1L, 2L);

        verify(auctionRepository).findTrendByItemId(1L, 2L);
    }
}
